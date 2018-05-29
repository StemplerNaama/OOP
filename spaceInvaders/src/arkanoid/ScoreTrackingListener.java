package arkanoid;
import core.Counter;
import core.HitListener;

/**
 * the ScoreTrackingListener class is in charge of summing the score game according to a block's value.
 */
public class ScoreTrackingListener implements HitListener {
   private Counter currentScore;

   /**
 * @param scoreCounter - it is the score counter of the game
 */
public ScoreTrackingListener(Counter scoreCounter) {
      this.currentScore = scoreCounter;
   }
/**
 * This method is called whenever the beingHit object is hit.
 * The hitter parameter is the Ball that's doing the hitting.
* @param beingHit -this is the block that being hit
* @param hitter -this is the ball that hits
*/
@Override
public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getHitPoints() == 0) {
           this.currentScore.increase(100);
           }
   }
}