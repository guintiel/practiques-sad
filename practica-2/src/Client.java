import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                System.out.println("Hey, vaig a llegir");
                while ((line = in.readLine()) != null) {
                    ms.println(line);
                    if (line.equals("close")) {
                        System.out.println("Tanco socket bro");
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
            String line = null;
            System.out.println("Hey, vull escriure");
            while ((line = ms.readLine()) != null && !ms.getSocket().isClosed()) {
                System.out.println("Hey, tinc algo per rebre!");
                System.out.println(line);
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
            new Thread(c.new SendMessages()).start();
            new Thread(c.new ReceiveMessages()).start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}