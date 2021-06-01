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
    
    public String getUsername(){
        return this.username;
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
            this.input.close();
            this.output.close();
            
            System.out.println("INPUT i OUTPUT tancats bruh");
            this.getSocket().close();
        } catch (IOException e) {
            System.out.println("Error al tancar");
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
        String st = null;
        try {
            st = this.input.readLine();
            return st;
        } catch (IOException e) {
            return st;
            /*System.out.println("Error al llegir pel socket");
            e.printStackTrace();
            System.out.println("Passa al socket: " + this.toString() + "\nPort desti: " + this.getSocket().getPort());
            System.out.println("Port font: " + this.getSocket().getLocalPort());*/
        }
        
    }
    
    public void sendUsername(){
        try {
            this.output.println(this.username);
        } catch (Exception e) {
            System.out.println("Falla a l'enviar el nom d'usuari.");
        }
    }

    public void writeLine(String st) {
        try {
            this.output.write(st);
        } catch (Exception e) {
            System.out.println("Error a l'escriure pel socket");
        }
    }

    public void println(String st) {
        this.output.println(st);
    }

}
