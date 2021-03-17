package src;

import java.util.*;

public class Line {

    private ArrayList<Integer> characters;
    // private Boolean editable;
    private int length;
    private int cursorPos;

    public Line(int len) {
        this.characters = new ArrayList<Integer>();
        // this.editable = true;
        this.length = len;
        this.cursorPos = 0;
    }

    public void addCar(int car) {
        if (characters.size() < this.length) {
            characters.add(car);
        }
    }

    public void setCursorPos(int cursorPos) {
        this.cursorPos = cursorPos;
    }

    public int getCursorPos() {
        return this.cursorPos;
    }

    public int getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        String cad = "";
        for (int i = 0; i < characters.size(); i++) {
            cad = cad + (char) characters.get(i).byteValue();
        }
        return cad;
    }

}
