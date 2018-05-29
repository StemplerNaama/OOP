package arkanoid;
import core.Counter;
import core.HitListener;

/**
 * a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
   private GameLevel gameLevel;
   private Counter remainingBlocks;

   /**
 * @param gameLevel - this is the game for a specific level
 * @param removedBlocks -this is the number of blocks in the game
 */
public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
       this.gameLevel = gameLevel;
       this.remainingBlocks = removedBlocks;
   }

/**
 * Blocks that are hit and reach 0 hit-points should be removed
 * from the gameLevel. and update the number of blocks.
* @param beingHit -this is the block that being hit
* @param hitter -this is the ball that hits
*/
@Override
public void hitEvent(Block beingHit, Ball hitter) {
       if (beingHit.getHitPoints() == 0) {
           beingHit.removeFromGame(this.gameLevel);
           beingHit.removeHitListener(this);
           this.remainingBlocks.decrease(1);
       }
   }
}