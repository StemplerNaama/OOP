package arkanoid;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import core.Sprite;

/**
 * SpriteCollection Class.
 */
public class SpriteCollection {
    //members
    private List<Sprite> collectionOfSprites;
    // constructors
    /**
     * build spriteCollection.
     */
    public SpriteCollection() {
        this.collectionOfSprites = new ArrayList<Sprite>();
    }
    /**
     * @param sprite - the Sprite object
     */
    public void addSprite(Sprite sprite) {
        this.collectionOfSprites.add(sprite);
    }
    /**
     * @param sprite -the Sprite object to remove from list
     */
    public void removeSprite(Sprite sprite) {
        this.collectionOfSprites.remove(sprite);
    }
    /**
     * call timePassed() on all sprites.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < collectionOfSprites.size(); i++) {
            collectionOfSprites.get(i).timePassed(dt);
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d - the surface to draw on it
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < collectionOfSprites.size(); i++) {
            (collectionOfSprites.get(i)).drawOn(d);
        }
    }
}
