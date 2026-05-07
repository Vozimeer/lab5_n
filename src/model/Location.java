package model;

/**
 * Локация (город) с координатами и названием.
 */
public class Location {
    private Integer x;
    private float y;
    private String name;

    /**
     * @param x    координата X, не null
     * @param y    координата Y
     * @param name название, не null и не пустое
     */
    public Location(Integer x, float y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Integer getX() { return x; }
    public float getY() { return y; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + name + ")";
    }
}