package project;

import java.util.Objects;

import static java.lang.Math.sqrt;

public class Coords{
    private int x;
    private int y;

    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x && y == coords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString(){
        return "("+x+", "+y+")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInRange(int reach, Coords position) {
        return x - reach <= position.getX() &&
                x + reach >= position.getX() &&
                y - reach <= position.getY() &&
                y + reach >= position.getY();
    }

    public Double getDistance(Coords position) {
        int a = x - position.getX();
        int b = y - position.getY();
        return sqrt(a^2 + b^2);
    }
}
