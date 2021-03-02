package practica-1;


import java.io.*;
import java.util.*;


public class EditableBufferedReader extends BufferedReader {
    
    public void setRaw(){
        //setRaw method passa la consola de mode cooked a mode raw.
    }

    public void unsetRaw(){
        //unsetRaw method passa la consola de mode raw a mode cooked.
    }

    public int read(){
        //read method llegeix el següent caràcter o la següent tecla de cursor.
    }

    public int readLine(){
        //readLine method llegeix la línia amb possibilitat d’editar-la.
    }

}
