package arkanoid;


import biuoop.DrawSurface;
import core.Sprite;
import geometry.Rectangle;

/**
 * this is the block drawer interface.
 */
public interface BlockDrawer extends Sprite {
    /**
     * @param ds - this is the surface to draw on it
     * @param rect - this is the rect to fill in
     */
    void fillBlock(DrawSurface ds, Rectangle rect);
  }