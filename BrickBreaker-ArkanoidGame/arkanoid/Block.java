package arkanoid;
import java.awt.Image;
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
    // אולי למחוק
    //private java.awt.Color color;
    private List<HitListener> hitListeners = new ArrayList<>();
    private BlockDrawer defaultFilling;
    private java.awt.Color strokeColor;
    //private Map<Integer, BlockDrawer> blockCreators;
    private Map<Integer, BlockDrawer> fillKMap;

    // constructors
    /**
     * @param rec - the rectangle that we want to implement as a block.
     */
    public Block(Rectangle rec) {
        this.rec = rec;
        this.numOfHits = 0;
        //this.color = null;
        this.defaultFilling = null;
        this.strokeColor = null;
        this.fillKMap = new HashMap<Integer, BlockDrawer>();
    }
    public Block(Rectangle rec, BlockDrawer defaultFilling, java.awt.Color strokeColor) {
        this.rec = rec;
        this.numOfHits = 0;
        //this.color = color;
        this.defaultFilling = defaultFilling;
        this.strokeColor = strokeColor;
        this.fillKMap = new HashMap<Integer, BlockDrawer>();
    }
    public Block(Rectangle rec, BlockDrawer defaultFilling, Map<Integer, BlockDrawer> fillKMap, java.awt.Color strokeColor, int hit_points) {
        this.rec = rec;
        this.numOfHits = hit_points;
        //this.color = color;
        this.defaultFilling = defaultFilling;
        this.strokeColor = strokeColor;
        //this.blockCreators = new HashMap<Integer, BlockDrawer>();
        this.fillKMap = fillKMap;
    }
    /*public void addFillToMap(int numOfHits, BlockDrawer filling) {
        this.fillKMap.put(numOfHits, filling);
    }*/
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
    public int getWidth() {
        return (int) this.rec.getWidth();
    }
    /**
     * drawing the outline of the blocks.
     * @param surface - the surface to draw the rectangle on
     */
    @Override
    public void drawOn(DrawSurface surface) {
     // if there is an image to draw
        if (fillKMap.containsKey(this.numOfHits)) {
            fillKMap.get(this.numOfHits).fillBlock(surface, this.rec);
        } else {
            this.defaultFilling.fillBlock(surface, this.rec);
        }
        // drawing the stroke if exists
        if (null != this.strokeColor) {
            surface.setColor(strokeColor);
            int x = (int) this.rec.getUpperLeft().getX();
            int y = (int) this.rec.getUpperLeft().getY();
            surface.drawRectangle(x, y, (int) this.rec.getWidth(), (int) this.rec.getHeight());
        }
    }
    /**
     * draw the background.
     * @param surface - the surface to draw the background on
     */
    /*public void drawBackground(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
    }
    /**
     * notify objects to move.
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