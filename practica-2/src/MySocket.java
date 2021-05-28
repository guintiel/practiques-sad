import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket extends Socket {
    private String username;
    private BufferedReader input;
    private PrintWriter output;

    public MySocket() throws IOException {
        super();
    }

    public MySocket(String host, int port) throws IOException {
        super();
    }

    public MySocket(String username, String host, int port) throws IOException {
        this(host, port);
        try {
            this.username = username;
            this.input = new BufferedReader(new InputStreamReader(this.getInputStream()));
            this.output = new PrintWriter(this.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    // public MySocket(InetAddress address, int port, InetAddress localAddr, int
    // localPort) throws IOException {
    // super();
    // }

    // public MySocket(String host, int port, InetAddress localAddr, int localPort)
    // throws IOException {
    // super();
    // }

    public Socket getSocket() {
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public BufferedReader myGetInputStream() {
        return this.input;
    }

    public PrintWriter myGetOutputStream() {
        return this.output;
    }

    //

    public void flush() {
        this.output.flush();
    }

    //

    public void close() {
        try {
            this.output.close();
            this.input.close();
            this.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean ready() {
        try {
            return this.input.ready();
        } catch (IOException e) {
            System.out.println("El socket no està llest");
        }
        return false;
    }

    public String readLine() {
        String s = null;
        try {
            s = this.input.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return s;
    }

    public void writeLine(String s) {
        try {
            this.output.write(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void println(String s) {
        this.output.println(s);
    }

    public void sendUsername() {
        this.output.println(this.username);
        System.out.println("Username enviat: " + this.username);
    }

    public String receiveUserName() {
        return this.readLine();
    }

    public String setUsername() {
        this.username = this.readLine();
        return this.username;
    }

}
