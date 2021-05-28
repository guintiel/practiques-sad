import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class App {

    public static void main(String[] args) throws Exception {
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter p = new PrintWriter(new OutputStreamWriter(System.out));
        // p.println("Hello shit");
        // System.out.println("m\n m");
        System.out.println("Digam el teu nom: ");
        String nom = b.readLine();
        System.out.println("Vale, el teu nom és " + nom);
        p.print("ff");
    }
}
