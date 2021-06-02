import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket(int port) throws IOException {
        super(port);
    }

    @Override
    public Socket accept() {
        Socket s = null;
        try {
            s = super.accept();
        } catch (IOException e) {
            System.out.println("Ha fallat accept");
        }
        return s;
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (IOException e) {
            System.out.println("Ha fallat tancar");
        }
    }
}
