package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * A Rectangle class.
 */
public class Rectangle {
    // members
    private Point upperLeft;
    private double width;
    private double height;
    private Limit limit;

    // constructors
    /**
     * Construct a rectangle with location and width/height.
     * @param upperLeft
     *            - the upper left point of rectangle, the location of rectangle
     * @param width
     *            - width of rectangle
     * @param height
     *            - height of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Construct a rectangle with location, width/height and its limits.
     * @param upperLeft
     *            - the upper left point of rectangle, the location
     * @param width
     *            - width of rectangle
     * @param height
     *            - height of rectangle
     * @param limit
     *            - the limits of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height, Limit limit) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.limit = limit;
    }

    /**
     * @return the upper left point of rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return the upper line of the rectangle
     */
    public Line getUpperLine() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY(), this.upperLeft.getX() + this.width,
                this.upperLeft.getY());
    }

    /**
     * @return the down line of the rectangle
     */
    public Line getDownLine() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY() + this.height, this.upperLeft.getX() + this.width,
                this.upperLeft.getY() + this.height);
    }

    /**
     * @return the left line of the rectangle
     */
    public Line getLeftLine() {
        return new Line(this.upperLeft.getX(), this.upperLeft.getY(), this.upperLeft.getX(),
                this.upperLeft.getY() + this.height);
    }

    /**
     * @return the right line of the rectangle
     */
    public Line getRightLine() {
        return new Line(this.upperLeft.getX() + this.width, this.upperLeft.getY(), this.upperLeft.getX() + this.width,
                this.upperLeft.getY() + this.height);
    }

    /**
     * @return all the four limits as an object
     */
    public Limit getLimit() {
        return this.limit;
    }

    /**
     * @param line
     *            - the line to find its intersection points with rectangle
     * @return listPoint- all collision points between the line and the
     *         rectangle
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> listPoint = new ArrayList<Point>();
        List<Line> listLines = new ArrayList<Line>();
        // adding the rectangle lines to a list
        listLines.add(this.getUpperLine());
        listLines.add(this.getDownLine());
        listLines.add(this.getLeftLine());
        listLines.add(this.getRightLine());
        // check intersection point for all the 4 lines
        for (int i = 0; i <= 3; i++) {
            Point intersectionPoint = line.intersectionWith((Line) listLines.get(i));
            // flag to show if a point is already exists.
            boolean isPointExists = false;
            if (intersectionPoint != null) {
                for (int j = 0; j < listPoint.size(); j++) {
                    // making sure there are no points that were added twice.
                    if (intersectionPoint.equals((Point) listPoint.get(j))) {
                        isPointExists = true;
                    }
                }
                if (!isPointExists) {
                    listPoint.add(intersectionPoint);
                }
            }
        }
        return listPoint;
    }

    /**
     * @return - the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return - the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }
}
