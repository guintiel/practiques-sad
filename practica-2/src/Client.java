import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    // private String username;
    // private String host;
    private MySocket ms;

    public Client(String username, String host) {
        // this.username = username;
        // this.host = host;
        try {
            this.ms = new MySocket(username, host, 8888);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public class ReadMessages implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line = null;
                while ((line = in.readLine()) != null) {
                    ms.writeLine(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    public class SendMessages implements Runnable {

        @Override
        public void run() {
            String line = null;
            while ((line = ms.readLine()) != null) {
                System.out.println(line);
            }

        }

    }

    public static void main(String[] args) {
        Client c = new Client("jordi", "127.0.0.1");
        new Thread(c.new ReadMessages()).start();
        new Thread(c.new SendMessages()).start();
    }
}
