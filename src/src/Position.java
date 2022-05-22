package src;

import java.awt.*;

public class Position {

    private int i;
    private int j;
    private Point coordinates;

    public Position(int i, int j, Point coordinates) {
        this.i = i;
        this.j = j;
        this.coordinates = coordinates;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
