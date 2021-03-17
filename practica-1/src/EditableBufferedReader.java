package src;

import java.io.*;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.lang.Integer;
import java.util.List;

public class EditableBufferedReader extends BufferedReader {

    private Line linia;
    public static final int RIGHT_ARROW = 67;
    public static final int LEFT_ARROW = 68;

    public static final int HOME = 72;
    public static final int END = 70;

    public static final int INS = 50;

    public static final int DEL = 51;
    public static final int BKSP = 127;

    public static final int CTRL_C = 3;

    public EditableBufferedReader(Reader in) {
        super(in);
        linia = new Line(getCols());
    }

    public void setRaw() {
        // ArrayList<String> com = new ArrayList<String>();
        // com.add(e)
        final List<String> command = new ArrayList<String>();
        command.add("/bin/sh");
        command.add("-c");
        // command.add("");
        command.add("stty -echo raw </dev/tty");
        ProcessBuilder p = new ProcessBuilder(command);
        try {
            p.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // setRaw method passa la consola de mode cooked a mode raw.
    }

    public void unsetRaw() {
        final List<String> command = new ArrayList<String>();
        command.add("/bin/sh");
        command.add("-c");
        // command.add("");
        command.add("stty -echo -raw </dev/tty");
        ProcessBuilder p = new ProcessBuilder(command);
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
        this.setRaw();
        int charac = this.read();
        // int maxCols = parseInt(this.getCols());
        while (charac != CTRL_C) {
            switch (charac) {
            case RIGHT_ARROW:
                this.linia.setCursorPos(this.linia.getCursorPos() + 1);
                break;
            case LEFT_ARROW:
                this.linia.setCursorPos(this.linia.getCursorPos() - 1);
                break;
            case HOME:
                this.linia.setCursorPos(0);
            case END:
                this.linia.setCursorPos(this.linia.getLength());
            case INS:

            case DEL:

            default:
                this.linia.addCar((char) charac);
                System.out.print((char) charac);
                break;
            }
            charac = this.read();
        }
        this.unsetRaw();
        return this.linia.toString();
    }

    public int getCols() {
        try {
            Process p = Runtime.getRuntime().exec("tput cols");
            InputStream input = p.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(input));
            int col = Integer.parseInt(r.readLine());
            return col;
        } catch (IOException e) {
            return 0;
        }

    }

}
