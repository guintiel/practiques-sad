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
                // System.out.println("Hey, vaig a llegir");
                while (!ms.getSocket().isClosed() && (line = in.readLine()) != null) {
                    ms.println(ms.getUsername() + "\n" + line);
                    if (line.equals("close")) {
                        System.out.println("Tanco socket");
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
            // System.out.println("Hey, vull escriure");
            while (!ms.getSocket().isClosed()) {
                if ((line = ms.readLine()) != null) {
                    System.out.println("Hey, tinc algo per rebre!");
                    System.out.println(line);
                }
            }

        }

    }

    public static void main(String[] args) {
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Quin es el teu nom d'usuari?");
            String user = b.readLine();
            System.out.println("Hola, " + user);
            Client c = new Client(user, "0.0.0.0", 55555);
            System.out.println("Estas enviant mitjançant el port: " + c.getSocket().getSocket().getLocalPort());
            new Thread(c.new SendMessages()).start();
            new Thread(c.new ReceiveMessages()).start();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}