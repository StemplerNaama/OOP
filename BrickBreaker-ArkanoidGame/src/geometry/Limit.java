package geometry;
/**
 * A Limit class.
 */
public class Limit {
    //members
    private int leftBorder;
    private int rightBorder;
    private int downBorder;
    private int upBorder;
    /**
     * Construct a limit.
     * @param leftBorder is the left Border
     * @param rightBorder is the right Border
     * @param downBorder is the down Border
     * @param upBorder is the up Border
     */
    public Limit(int leftBorder, int rightBorder, int downBorder, int upBorder) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.downBorder = downBorder;
        this.upBorder = upBorder;
    }
    /**
     * @return the leftBorder
     */
    public int getLeftBorder() {
        return this.leftBorder;
    }
    /**
     * @return the rightBorder
     */
    public int getRightBorder() {
        return this.rightBorder;
    }
    /**
     * @return the downBorder
     */
    public int getDownBorder() {
        return this.downBorder;
    }
    /**
     * @return the upBorder
     */
    public int getUpBorder() {
        return this.upBorder;
    }
}