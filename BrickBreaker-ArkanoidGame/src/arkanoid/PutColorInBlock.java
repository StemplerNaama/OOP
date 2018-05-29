package arkanoid;
import biuoop.DrawSurface;
import core.Sprite;
import geometry.Rectangle;

/**
 * this is a class that put color in block.
 */
public class PutColorInBlock implements BlockDrawer, Sprite {
    // members
    private java.awt.Color color;
    // constructor
    /**
     * @param color - the color to put in block
     */
    public PutColorInBlock(java.awt.Color color) {
        this.color = color;
    }
    /**
     * @param ds - this is the surface to draw on it
     * @param rect - this is the rect to fill in
     */
    @Override
    public void fillBlock(DrawSurface ds, Rectangle rect) {
        ds.setColor(this.color);
        ds.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
    }
    /**
     * draw the sprite to the screen.
     * @param d - the surface to draw on it.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(0, 0, 800, 600);
    }
    @Override
    /**
     * notify the sprite that time has passed
     * notify objects to move.
     * @param dt - specifies the amount of seconds passed since the last call
     */
    public void timePassed(double dt) {
    }
    @Override
    /**
     * @param g - add an object as a sprite
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
