import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * SpriteCllection Class.
 */
public class SpriteCollection {
    //members
    private List collectionOfSprites;
    // constructors
    /**
     * build spriteCollection.
     */
    public SpriteCollection() {
        this.collectionOfSprites = new ArrayList();
    }
    /**
     * @param sprite - the Sprite object
     */
    public void addSprite(Sprite sprite) {
        collectionOfSprites.add(sprite);
    }
    // call timePassed() on all sprites.
    /**
     *
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < collectionOfSprites.size(); i++) {
            ((Sprite) collectionOfSprites.get(i)).timePassed();
        }
    }
    // call drawOn(d) on all sprites.
    /**
     * @param d - the surface to draw on it
     */
    public void drawAllOn(DrawSurface d) {
        d.setColor(java.awt.Color.pink);

        for (int i = 0; i < collectionOfSprites.size(); i++) {
            ((Sprite) collectionOfSprites.get(i)).drawOn(d);
        }
    }
}



