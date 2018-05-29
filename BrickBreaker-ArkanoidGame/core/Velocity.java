package core;
import geometry.Point;

/**
 * A Velocity class.
 */
public class Velocity {
    //members
    private double dx;
    private double dy;
    // constructors
    /**
     * @param dx - the change in x coordinate.
     * @param dy - the change in y coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * @param angle - the incline of ball
     * @param speed - the speed of ball moving
     * @return new velocity in terms of angles and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * -1 * speed;
        return new Velocity(dx, dy);
    }
    /**
     * @return dx -the change in x coordinate.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * @return dy -the change in y coordinate.
     */
    public double getDy() {
        return this.dy;
    }
    /**
     * @param p - the point that need an update in location
     * @return newP - the point with the update location
     */
    public Point applyToPoint(Point p, double dt) {
        Point newP = new Point(p.getX() + this.dx * dt, p.getY() + this.dy * dt);
        return newP;
    }
}
