package levels;

import java.util.List;
import arkanoid.Block;
import core.Sprite;
import core.Velocity;
/**
 * this is the LevelInformation interface.
 */
public interface LevelInformation {
   /**
 * @return - the number of the level's balls.
 */
int numberOfBalls();
   // The initial velocity of each ball
   // Note that initialBallVelocities().size() == numberOfBalls()
/**
 * The initial velocity of each ball.
 * Note that initialBallVelocities().size() == numberOfBalls()
 * @return - the list of the velocities.
 */
List<Velocity> initialBallVelocities();
/**
 * @return - the paddleSpeed.
 */
int paddleSpeed();
/**
 * @return - the paddleWidth.
 */
int paddleWidth();
/**
 * the level name will be displayed at the top of the screen.
 * @return - the levelName.
 */
String levelName();
/**
 * Returns a sprite with the background of the level
 * the level name will be displayed at the top of the screen.
 * @return - the getBackground.
 */
Sprite getBackground();
/**
 * The Blocks that make up this level, each block contains
 * its size, color and location.
 * @return - the list of the blocks.
 */
List<Block> blocks();
/**
 * @return - Number of levels that should be removed before the level is considered to be "cleared".
 * This number should be <= blocks.size();
 */
int numberOfBlocksToRemove();
}
