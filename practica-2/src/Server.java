import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private Map<String, MySocket> sockets = new HashMap<String, MySocket>();

    // public static final int port = 8888;

    public class Intermediari implements Runnable {
        // Intermediari que agafa el missatge d'1 client i l'envia a la resta
        @Override
        public void run() {
            try {
                MyServerSocket ss = new MyServerSocket(8888);
                // ss.bind(endpoint);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    // Main que ha d'estar escoltant contínuament, quan hi ha un client que es vol
    // connectar amb un altre client li ha d'acceptar la connexió

    public static void main(String[] args) {
        try {
            MyServerSocket ss = new MyServerSocket(8888);

            while (true) {
                MySocket cli = ss.accept();
                System.out.println("Client accepted: " + cli.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
