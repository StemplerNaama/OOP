package arkanoid;
import java.awt.Image;
import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * this is a class that put image in block.
 */
public class PutImageInBlock implements BlockDrawer {
    //members
    private Image img;
    // constructor
    /**
     * @param img - the image to put in block
     */
    public PutImageInBlock(Image img) {
        this.img = img;
    }
 @Override
 /**
  * @param ds - this is the surface to draw on it
  * @param rect - this is the rect to fill in
  */
 public void fillBlock(DrawSurface ds, Rectangle rect) {
     ds.drawImage((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(), this.img);
     }
 /**
  * draw the sprite to the screen.
  * @param d - the surface to draw on it.
  */
@Override
public void drawOn(DrawSurface d) {
    d.drawImage(0, 0, this.img);
}

@Override
/**
 * notify the sprite that time has passed
 * notify objects to move.
 * @param dt - specifies the amount of seconds passed since the last call
 */
public void timePassed(double dt) {
}
/**
 * @param g - add an object as a sprite
 */
@Override
public void addToGame(GameLevel g) {
    g.addSprite(this);
}
 }