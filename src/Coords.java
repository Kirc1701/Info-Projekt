package src;

public record Coords(int x, int y) {

    public String toString() {
        return x + "_" + y;
    }

    public boolean isInRange(int reach, Coords position) {
        return x - reach <= position.x() &&
                x + reach >= position.x() &&
                y - reach <= position.y() &&
                y + reach >= position.y();
    }
}
