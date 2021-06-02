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

    public String getUsername() {
        return this.username;
    }

    public BufferedReader myGetInputStream() {
        return this.input;
    }

    public PrintWriter myGetOutputStream() {
        return this.output;
    }

    public void close() {
        try {
            this.input.close();
            this.output.close();
            this.getSocket().close();
        } catch (IOException e) {
            System.out.println("Error al tancar");
        }
    }

    public String readLine() {
        String st = null;
        try {
            st = this.input.readLine();
            return st;
        } catch (IOException e) {
            return st;
        }

    }

    public void println(String st) {
        this.output.println(st);
    }

    public void sendUsername() {
        this.output.println(this.username);
    }

    public void rcvAndSetUsername() {
        try {
            this.username = this.input.readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean verifyUsername(String nick) {
        // Obre un socket paral·lel (admin) amb el servidor i li demana si ja existeix
        // l'usuari
        // true si no existeix, false si ja existeix
        this.output.println(nick);
        try {
            return Boolean.parseBoolean(this.input.readLine());
        } catch (IOException ex) {
            ex.getMessage();
            return true;
        }
    }
}