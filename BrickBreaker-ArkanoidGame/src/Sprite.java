import biuoop.DrawSurface;

/**
 * the Sprite implement.
 * @author
 */
public interface Sprite {
    // draw the sprite to the screen
    /**
     * @param d - the surface to draw on it.
     */
    void drawOn(DrawSurface d);
    // notify the sprite that time has passed
    /**
     * notify objects to move.
     */
    void timePassed();
}
