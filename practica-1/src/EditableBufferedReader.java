package src;

import java.io.*;
//import java.util.ArrayList;
//import java.lang.Integer;

public class EditableBufferedReader extends BufferedReader {

    private Line linia;

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {
        ProcessBuilder p = new ProcessBuilder("stty -echo raw </dev/tty");
        try {
            p.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // setRaw method passa la consola de mode cooked a mode raw.
    }

    public void unsetRaw() {
        ProcessBuilder p = new ProcessBuilder("stty -echo cooked </dev/tty");
        try {
            p.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // unsetRaw method passa la consola de mode raw a mode cooked.
    }

    public int read() {
        // read method llegeix el següent caràcter o la següent tecla de cursor.
        int inchar = 0; // Per defecte retornarà 0 si no s'ha pogut llegir
        try {
            inchar = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inchar;
    }

    public String readLine() {
        // readLine method llegeix la línia amb possibilitat d’editar-la.
        int maxcols = 200;
        this.setRaw();
        int charac = this.read();
        // int maxCols = parseInt(this.getCols());
        while (charac != 3) {
            charac = this.read();
            switch (charac) {

            default:
                this.linia.addCar((char) charac);
            }
        }
        this.unsetRaw();
        return this.linia.toString();
    }

    public String getCols() throws IOException {
        Process p = Runtime.getRuntime().exec("tput cols");
        InputStream input = p.getInputStream();
        BufferedReader r = new BufferedReader(new InputStreamReader(input));
        String num = r.readLine();
        return num;
    }

}
