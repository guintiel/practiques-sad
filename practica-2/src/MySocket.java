import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MySocket extends Socket {

    private BufferedReader input;
    private PrintWriter output;

    public MySocket() throws IOException {
        super();
    }

    public MySocket(InetAddress address, int port) throws IOException {
        super();
    }

    public MySocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        super();
    }

    public MySocket(String host, int port) throws IOException {
        super();
    }

    public MySocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        super();
    }

}
