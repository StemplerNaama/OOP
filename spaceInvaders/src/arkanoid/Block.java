package arkanoid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import biuoop.DrawSurface;
import core.Collidable;
import core.HitListener;
import core.HitNotifier;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * a Block class.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // members
    private Rectangle rec;
    private int numOfHits;
    private List<HitListener> hitListeners = new ArrayList<>();
    private BlockDrawer defaultFilling;
    private java.awt.Color strokeColor;

    // constructors
    /**
     * @param rec - the rectangle that we want to implement as a block.
     */
    public Block(Rectangle rec) {
        this.rec = rec;
        this.numOfHits = 0;
        this.defaultFilling = null;
        this.strokeColor = null;
        //this.fillKMap = new HashMap<Integer, BlockDrawer>();
    }
    /**
     * @param rec - the rectangle that we want to implement as a block
     * @param defaultFilling - the default filling of the blocks
     * @param strokeColor - the color of the stroke of block
     */
    public Block(Rectangle rec, BlockDrawer defaultFilling, java.awt.Color strokeColor) {
        this.rec = rec;
        this.numOfHits = 0;
        this.defaultFilling = defaultFilling;
        this.strokeColor = strokeColor;
        //this.fillKMap = new HashMap<Integer, BlockDrawer>();
    }
    /**
     * @param rec - the rectangle that we want to implement as a block
     * @param defaultFilling - the default filling of the blocks
     * @param fillKMap - a map that make a match of specific filling to every hit
     * @param strokeColor - the color of the stroke of block
     * @param hitPoints - the num of hits until block is remove
     */
    public Block(Rectangle rec, BlockDrawer defaultFilling,
            java.awt.Color strokeColor, int hitPoints) {
        this.rec = rec;
        this.numOfHits = hitPoints;
        this.defaultFilling = defaultFilling;
        this.strokeColor = strokeColor;
    }
    /**
     * @param gameLevel - the game that has the sprites and collidables
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }
    /**
     * @param gameLevel - the game that has the sprites and collidables
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }
    /**
     * decrease num of hits in block.
     */
    public void decreaseNumOfHits() {
        this.numOfHits = this.numOfHits - 1;
    }
    /**
     * @return the num of hits in block.
     */
    public int getHitPoints() {
        return this.numOfHits;
    }
    /**
     * set the num of hits in block.
     * @param number - the update num of hits
     */
    public void setNumOfHits(int number) {
        this.numOfHits = number;
    }
    /**
     * @return the width of block.
     */
    public int getWidth() {
        return (int) this.rec.getWidth();
    }
    /**
     * drawing the outline of the blocks.
     * @param surface - the surface to draw the rectangle on
     */
    @Override
    public void drawOn(DrawSurface surface) {
            this.defaultFilling.fillBlock(surface, this.rec);
        // drawing the stroke if exists
        if (null != this.strokeColor) {
            surface.setColor(strokeColor);
            int x = (int) this.rec.getUpperLeft().getX();
            int y = (int) this.rec.getUpperLeft().getY();
            surface.drawRectangle(x, y, (int) this.rec.getWidth(), (int) this.rec.getHeight());
        }
    }
    /**
     * notify objects to move.
     * @param dt - specifies the amount of seconds passed since the last call
     */
    @Override
    public void timePassed(double dt) {
    }
    /**
     * @return the "collision shape" of the object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }
    /**
     *  Notify the object that we collided with it at collisionPoint with
     *  a given velocity.
     * @param hitter - the ball that hits
     * @param collisionPoint - the collision point with line and block
     * @param currentVelocity - the velocity before it changes
     * @return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        /*
         * if the collision point is equal to one of the limits of the rectangle- change the direction.
         */
        //if equal to the up border change the dY value.
        if (Math.abs(collisionPoint.getY() - this.getCollisionRectangle().getUpperLine().start().getY()) <= 0.0001) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -1 * Math.abs(currentVelocity.getDy()));
        }
        // if equal to the up border change the dY value.
        if (Math.abs(collisionPoint.getY() - this.getCollisionRectangle().getDownLine().start().getY()) <= 0.0001) {
            currentVelocity = new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));
        }
        // if equal to the up border change the dX value.
        if (Math.abs(collisionPoint.getX() - this.getCollisionRectangle().getRightLine().start().getX()) <= 0.0001) {
            currentVelocity = new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        // if equal to the up border change the dX value.
        if (Math.abs(collisionPoint.getX() - this.getCollisionRectangle().getLeftLine().start().getX()) <= 0.0001) {
            currentVelocity = new Velocity(-1 * Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        // decrease the num of hits
        this.decreaseNumOfHits();
        //notifyHItter
        this.notifyHit(hitter);
        // return the current velocity
        return currentVelocity;
    }
    /**
     * @param hitter - this is the ball that hits the block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }
    /**
     * @param hl -Add hl as a listener to hit events.
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }
    /**
     * @param hl -Remove hl from the list of listeners to hit events.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
        }

}