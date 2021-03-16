package src;

import java.util.*;

public class Line {

    private ArrayList<Integer> list;
    // private Boolean editable;
    private int length;

    public Line(int len) {
        this.list = new ArrayList<Integer>();
        // this.editable = true;
        this.length = len;
    }

    public void addCar(int car) {
        list.add(car);
    }

    // public String toString(){

    // }

}
