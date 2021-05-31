import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {
    private Socket s;
    private String username;
    private BufferedReader input;
    private PrintWriter output;

    public MySocket(String username, String host, int port) throws IOException {
        this.s = new Socket(host, port);
        this.username = username;
        this.input = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.output = new PrintWriter(this.s.getOutputStream(), true);
    }

    public MySocket(Socket s) throws IOException {
        this.s = s;
        this.input = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.output = new PrintWriter(this.s.getOutputStream(), true);
    }

    public Socket getSocket() {
        return this.s;
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
            System.out.println(e.getStackTrace());
        }
    }

    // public boolean ready() {
    // try {
    // return this.input.ready();
    // } catch (IOException e) {
    // System.out.println("El socket no està llest");
    // }
    // return false;
    // }

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

}
