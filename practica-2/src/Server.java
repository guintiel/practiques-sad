import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private Map<Integer, MySocket> sockets = new HashMap<Integer, MySocket>();
    private final ReentrantLock mon = new ReentrantLock();

    // public static final int port = 55555;
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
                sockets.put(this.socketServidor.getSocket().getPort(), socketServidor);
            } finally {
                mon.unlock();
            }
            while (true && !this.socketServidor.getSocket().isClosed()) {
                // Lectura des del servidor, llegeix un missatge del client associat a
                // l'intermediari
                String missatge = this.socketServidor.readLine();
                System.out.println("Missatge enviat: " + missatge);
                if (missatge.equals("close")) {
                    mon.lock();
                    try {
                        sockets.remove(this.socketServidor.getSocket().getPort());
                    } finally {
                        mon.unlock();
                    }
                    this.socketServidor.close();
                } else {
                    mon.lock();
                    try {
                        sockets.forEach((Integer k, MySocket socket) -> {
                            System.out.println("ah ok");
                            if (k != this.socketServidor.getSocket().getPort()) {
                                socket.println(missatge);
                            }

                        });
                    } finally {
                        mon.unlock();
                    }
                }
                // Escriptura des del servidor, escriu a la resta de sockets que no son ell.

            }
        }
    }

    // Main que ha d'estar escoltant contínuament, quan hi ha un client que es vol
    // connectar amb un altre client li ha d'acceptar la connexió
    public static void main(String[] args) {
        try {
            Server s = new Server();
            MyServerSocket ss = new MyServerSocket(55555);
            // ss.bind(new InetSocketAddress("127.0.0.1", 55555));
            System.out.println("Server escoltant en port: " + ss.getLocalPort());
            System.out.println("Adreça local: " + ss.getInetAddress());
            while (true) {
                Socket socketS = ss.accept();
                MySocket ms = new MySocket(socketS);
                System.out.println("Client accepted: " + socketS.toString());
                Thread intermediari = new Thread(s.new Intermediari(ms));
                intermediari.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

    }

}
