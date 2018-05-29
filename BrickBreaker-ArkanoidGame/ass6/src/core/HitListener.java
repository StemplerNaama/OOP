package core;
import arkanoid.Ball;
import arkanoid.Block;

/**
 * this is the HitListener interface.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
 * @param beingHit -this is the block that being hit
 * @param hitter -this is the ball that hits
 */
void hitEvent(Block beingHit, Ball hitter);
}