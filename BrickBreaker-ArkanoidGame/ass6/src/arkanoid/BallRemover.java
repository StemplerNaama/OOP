package arkanoid;
import core.Counter;
import core.HitListener;

/**
 *ball remover class is in charge of removing balls from the gameLevel, as well as keeping count
 *of the number of balls that remain.
 */
public class BallRemover implements HitListener {
   private GameLevel gameLevel;
   private Counter remainingBalls;

   /**
 * @param gameLevel - this is the game for a specific level
 * @param removedBalls -this is the number of balls in the game
 */
public BallRemover(GameLevel gameLevel, Counter removedBalls) {
       this.gameLevel = gameLevel;
       this.remainingBalls = removedBalls;
   }

/**
 * everytime that we hit the deathblock- the ball is removed from the game and we update the number of balls.
 * @param beingHit - this is the ball the being hit
 * @param hitter - this is the ball that hits
 */
@Override
public void hitEvent(Block beingHit, Ball hitter) {
           hitter.removeFromGame(this.gameLevel);
           this.remainingBalls.decrease(1);
   }
}