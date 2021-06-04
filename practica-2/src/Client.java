import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    private MySocket ms;

    public Client(String username, String host, int portToConnect) {
        try {
            this.ms = new MySocket(username, host, portToConnect);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public MySocket getSocket() {
        return this.ms;
    }

    public class SendMessages implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line = null;
                while (!ms.getSocket().isClosed() && (line = in.readLine()) != null) {
                    ms.println("@" + ms.getUsername() + ": " + line);
                    if (line.equals("close")) {
                        ms.close();
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public class ReceiveMessages implements Runnable {

        @Override
        public void run() {
            String line;
            while (!ms.getSocket().isClosed()) {
                if ((line = ms.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            final String hostToConnect = "0.0.0.0";
            final int portUsernames = 44444;
            final int portToConnect = 55555;
            System.out.println("Quin es el teu nom d'usuari?");
            String user = b.readLine();
            // Client demana si esta disponible un username mitjançant un socket
            // admin, que es connectara a una connexio paral·lela amb el servidor
            MySocket admin = new MySocket("admin", hostToConnect, portUsernames);
            while (!admin.verifyUsername(user)) {
                System.out.println("Nick en us, prova un altre:");
                user = b.readLine();
            }
            while (user.equals("admin")) {
                System.out.println("Nom reservat, tria'n un altre:");
                user = b.readLine();
            }
            // Usuari registrat
            System.out.println("-----------------------------------------");
            System.out.println("Hola, " + user + ".\nBenvinguda/t al xat. Envia un missatge:\nEnvia close per sortir.");
            System.out.println("-----------------------------------------");
            Client c = new Client(user, hostToConnect, portToConnect);
            c.getSocket().sendUsername();
            new Thread(c.new SendMessages()).start();
            new Thread(c.new ReceiveMessages()).start();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}