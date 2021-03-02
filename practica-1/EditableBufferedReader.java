import java.io.*;

public class EditableBufferedReader extends BufferedReader {
    
    public EditableBufferedReader(Reader in, int sz) {
        super(in, sz);
        // TODO Auto-generated constructor stub
    }

    public void setRaw() {
        //setRaw method passa la consola de mode cooked a mode raw.
    }

    public void unsetRaw(){
        //unsetRaw method passa la consola de mode raw a mode cooked.
    }

    public int read(){
        //read method llegeix el següent caràcter o la següent tecla de cursor.
        int i = 0;
        return 0;
    }

    public String readLine(){
        //readLine method llegeix la línia amb possibilitat d’editar-la.
        return null;
    }

}
