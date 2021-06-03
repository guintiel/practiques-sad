import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private final Map<String, MySocket> sockets = new HashMap<>();
    private final ReentrantLock mon = new ReentrantLock();
    private final String hostListening = "0.0.0.0";
    private final int portListening = 55555;
    private final int portListeningUsers = 44444;

    public class Intermediari implements Runnable {

        // Intermediari que agafa el missatge d'1 client i l'envia a la resta.
        // Aquest client queda associat a l'intermediari.
        private MySocket socketServidor;

        public Intermediari(MySocket s) {
            this.socketServidor = s;
        }

        @Override
        public void run() {
            mon.lock();
            try {
                // La clau de cada socket es el port que esta utilitzant el client
                sockets.put(this.socketServidor.getUsername(), socketServidor);
            } finally {
                mon.unlock();
            }
            while (!this.socketServidor.getSocket().isClosed()) {
                // Lectura des del servidor, llegeix un missatge del client associat a
                // l'intermediari
                String missatge = this.socketServidor.readLine();
                System.out.println("Missatge rebut: " + missatge);
                if (missatge.equals("@" + this.socketServidor.getUsername() + ": " + "close")) {
                    System.out.println(this.socketServidor.getUsername());
                    mon.lock();
                    System.out.println("Entra al monitor");
                    try {
                        sockets.remove(this.socketServidor.getUsername());
                        sockets.forEach((String u, MySocket socket) -> {
                            if (!u.equals(this.socketServidor.getUsername())) {
                                socket.println("@" + this.socketServidor.getUsername() + " ha abandonat el xat");
                            }

                        });
                    } finally {
                        mon.unlock();
                    }
                    System.out.println("Client que marxa: " + this.socketServidor.getUsername());
                    this.socketServidor.close();

                } else {
                    mon.lock();
                    try {
                        // Escriptura des del servidor, escriu a la resta de sockets que no son ell.
                        sockets.forEach((String u, MySocket socket) -> {
                            if (!u.equals(this.socketServidor.getUsername())) {
                                socket.println(missatge);
                            }

                        });
                    } finally {
                        mon.unlock();
                    }
                }

            }
        }
    }

    public class UsernameCheck implements Runnable {
        // Classe que conte un socket que escolta la demanda d'un nom d'usuari
        // per part d'un client, i aquest li contesta true si esta disponible i
        // false en cas contrari.

        private MyServerSocket checker;

        public UsernameCheck(String host, int port) throws IOException {
            checker = new MyServerSocket(host, port);
        }

        @Override
        public void run() {
            while (true) {
                Socket ch = checker.accept();
                try {
                    MySocket tal = new MySocket(ch);
                    String nick = tal.readLine();
                    mon.lock();
                    try {
                        while (sockets.containsKey(nick)) {
                            tal.println("false");
                            nick = tal.readLine();
                        }
                        tal.println("true");
                    } finally {
                        mon.unlock();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    public static void main(String[] args) {
        // Main que ha d'estar escoltant contínuament, quan hi ha un client que es vol
        // connectar amb un altre client li ha d'acceptar la connexió
        try {
            Server s = new Server();
            new Thread(s.new UsernameCheck(s.hostListening, s.portListeningUsers)).start();
            MyServerSocket ss = new MyServerSocket(s.hostListening, s.portListening);
            System.out.println("Server escoltant en port: " + ss.getLocalPort());
            System.out.println("Adreça local: " + ss.getInetAddress());
            while (true) {
                Socket socketS = ss.accept();
                MySocket ms = new MySocket(socketS);
                ms.rcvAndSetUsername();
                System.out.println("Client acceptat: " + ms.getUsername());
                s.mon.lock();
                try {
                    // Escriptura des del servidor, escriu a la resta de sockets que no son ell.
                    s.sockets.forEach((String u, MySocket socket) -> {
                        if (!u.equals(ms.getUsername())) {
                            socket.println("@" + ms.getUsername() + " ha entrat al xat");
                        }

                    });
                } finally {
                    s.mon.unlock();
                }

                Thread intermediari = new Thread(s.new Intermediari(ms));
                intermediari.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}