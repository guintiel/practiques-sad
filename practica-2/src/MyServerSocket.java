import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket() throws IOException {
        super();
    }

    public MyServerSocket(int port) throws IOException {
        super();
    }

    public MyServerSocket(int port, int backlog) throws IOException {
        super();
    }

    public MyServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        super();
    }

    // Methods

    public void bind(SocketAddress endpoint) {
        try {
            super.bind(endpoint);
        } catch (IOException e) {
            System.out.println("Ha fallat el bind a: " + endpoint);
        }

    }

    public MySocket accept() {
        Socket s = null;
        try {
            s = super.accept();
        } catch (IOException e) {
            System.out.println("Ha fallat accept");
        }
        return (MySocket) s;
    }

    public void close() {
        try {
            super.close();
        } catch (IOException e) {
            System.out.println("Ha fallat tancar");
        }
    }
}
