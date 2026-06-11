package common.model;

import java.io.Serializable;

/**
 * Координаты (x, y).
 */
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;
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