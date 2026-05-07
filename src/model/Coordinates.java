package model;

/**
 * Координаты (x, y).
 */
public class Coordinates {
    private long x;
    private Long y;

    /**
     * @param x координата X
     * @param y координата Y, не null
     */
    public Coordinates(long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() { return x; }
    public Long getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}