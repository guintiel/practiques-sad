package src;

import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    private Line linia;

    public EditableBufferedReader(Reader in) {
        super(in);
        this.linia = new Line();
    }

    public void setRaw() {
        // setRaw method passa la consola de mode cooked a mode raw.
    }

    public void unsetRaw() {
        // unsetRaw method passa la consola de mode raw a mode cooked.
    }

    public int read() {
        // read method llegeix el següent caràcter o la següent tecla de cursor.
        return 0;
    }

    public String readLine() {
        // readLine method llegeix la línia amb possibilitat d’editar-la.
        int character = 0;
        while (character != 'a') {
            character = this.read();

            this.linia.addChar(character);
        }

        return this.linia.toString();
    }

}
