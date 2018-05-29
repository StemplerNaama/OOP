import java.util.List;
import biuoop.DrawSurface;

/**
 * a Line class.
 */
public class Line {
    //members
    private Point start;
    private Point end;
    // constructors
    /**
     * Construct a line given a start point and an end point.
     * @param start - start point of line
     * @param end - end point of line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    /**
     * Construct a line.
     * @param x1 - the x1 coordinate
     * @param y1 - the y1 coordinate
     * @param x2 - the x2 coordinate
     * @param y2 - the y2 coordinate
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }
    /**
     * @return length of line.
     */
    public double length() {
        double lengthOfLine = this.start.distance(end);
        return lengthOfLine;
    }
    /**
     * @return the middle of the line.
     */
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2;
        double y = (this.start.getY() + this.end.getY()) / 2;
        Point middlePoint = new Point(x, y);
        return middlePoint;
    }
    /**
     * @return the start point.
     */
    public Point start() {
        return this.start;
    }
    /**
     * @return the end point.
     */
    public Point end() {
        return this.end;
    }
    /**
     * @param other line to check if they intersect.
     * @return p-the intersection point or null if there is not intersection
     */
    public Point intersectionWith(Line other) {
       //check if there is a collision between lines.
       Point localPoint = null;
       double num = (this.end().getY() - this.start().getY()) * (this.start().getX() - other.start().getX())
               - (this.end().getX() - this.start().getX()) * (this.start().getY() - other.start().getY());
       double denom = (this.end().getY() - this.start().getY()) * (other.end().getX() - other.start().getX())
       - (this.end().getX() - this.start().getX()) * (other.end().getY() - other.start().getY());
       double localPointX = other.start().getX() + (other.end().getX() - other.start().getX()) * num / denom;
       double localPointY = other.start().getY() + (other.end().getY() - other.start().getY()) * num / denom;
       // check if the collision point is between the x values and between the y values-if the lines segment collide.
       if (((localPointX >= Math.min(this.start().getX(), this.end().getX())
               && localPointX <= Math.max(this.start().getX(), this.end().getX()))
               && (localPointX >= Math.min(other.start().getX(), other.end().getX())
               && localPointX <= Math.max(other.start().getX(), other.end().getX())))
               && ((localPointY >= Math.min(this.start().getY(), this.end().getY())
               && localPointY <= Math.max(this.start().getY(), this.end().getY()))
                       && (localPointY >= Math.min(other.start().getY(), other.end().getY())
                       && localPointY <= Math.max(other.start().getY(), other.end().getY())))) {
           localPoint = new Point(localPointX, localPointY);
           }
       return localPoint;
       }
    /**
     * @param other line to check if intersect with
     * @return true if intersect or false if not intersect
     */
    public boolean isIntersecting(Line other) {
        boolean result;
        if (this.intersectionWith(other) == null) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }
    /**
     * draw line.
     * @param surface - the surface to draw the line on
     */
    public void drawLine(DrawSurface surface) {
        surface.drawLine((int) this.start.getX(), (int) this.start.getY(), (int) this.end.getX(),
                (int) this.end.getY());
    }
    /**
     * @param other line to check if it equals to
     * @return true if they equal, false- if not
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && (this.end.equals(other.end)));
    }
    /**
     * @param rect - a rectangle
     * @return the closest intersection to start of line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // listPoint- a list of all the intersection points.
        List listPoint = rect.intersectionPoints(this);
        Point intersectionPoint =  null;
        double distance = this.length();
        // check which point from the list is the closest to the start of line.
        for (int i = 0; i < listPoint.size(); i++) {
            if (distance >= this.start.distance((Point) listPoint.get(i))) {
                distance = this.start.distance((Point) listPoint.get(i));
                intersectionPoint = (Point) listPoint.get(i);
            }
        }
        // return the closest point
        return intersectionPoint;
    }
}