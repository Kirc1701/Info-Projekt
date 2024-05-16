package src.util;

import src.Direction;

public record CoordsDouble(double x, double y) {
    public String toString() {
        return x + "_" + y;
    }

    public static CoordsDouble getNormalized(Direction direction){
        return switch (direction){
            case EAST -> new CoordsDouble(1, 0);
            case NORTH -> new CoordsDouble(0, 1);
            case SOUTH -> new CoordsDouble(0, -1);
            case WEST -> new CoordsDouble(-1, 0);
        };
    }

    public CoordsDouble scale(float factor){
        return new CoordsDouble(x * factor, y * factor);
    }

    public CoordsDouble add(CoordsDouble other){
        return new CoordsDouble(x + other.x, y + other.y);
    }

    public CoordsDouble max(CoordsDouble other) {
        return new CoordsDouble(java.lang.Math.max(other.x(), this.x()), java.lang.Math.max(other.x(), this.y()));
    }
    public CoordsDouble min(CoordsDouble other) {
        return new CoordsDouble(java.lang.Math.min(other.x(), this.x()), java.lang.Math.min(other.x(), this.y()));
    }

    @SuppressWarnings("unused")
    public CoordsDouble abs(){
        return new CoordsDouble(java.lang.Math.abs(x), java.lang.Math.abs(x));
    }

    public CoordsInt toCoordsInt() {
        return new CoordsInt((int) x, (int) y);
    }
}
