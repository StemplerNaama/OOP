package geometry;
/**
 * A Point class.
 */
public class Point {
    //members
    private double x;
    private double y;
    /**
     * Construct a point given x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @return the x coordinate
     */
    public double getX() {
        return this.x;
    }
    /**
     * @return the y coordinate
     */
    public double getY() {
        return this.y;
    }
    /**
     * @param other point to measure the distance to
     * @return the distance to the other point
     */
    public double distance(Point other) {
        double distanceX = this.x - other.getX();
        double distanceY = this.y - other.getY();
        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
    }
    /**
     * @param other - point to check if it equals to
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return ((this.x == other.getX()) && (this.y == other.getY()));
    }
}
