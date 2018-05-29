package levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import arkanoid.BackgroundsOfLevels;
import arkanoid.Block;
import arkanoid.PutColorInBlock;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * @author - this is the fourth level class.
 */
public class LevelFour implements LevelInformation {
    // constructor
    /**
     * this is the constructor.
     */
    public LevelFour() {
    }
    /**
     * @return - the number of the level's balls.
     */
    @Override
    public int numberOfBalls() {
        return 3;
    }
    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - the list of the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocity = new ArrayList<Velocity>();
        int angle = 45;
        for (int i = 0; i < this.numberOfBalls(); i++) {
        Velocity ballVelocity = Velocity.fromAngleAndSpeed(angle, 350);
        ballsVelocity.add(ballVelocity);
        angle += 45;
        }
        return ballsVelocity;
    }
    /**
     * @return - the paddleSpeed.
     */
    @Override
    public int paddleSpeed() {
        return 10;
    }
    /**
     * @return - the paddleWidth.
     */
    @Override
    public int paddleWidth() {
        return 210;
    }
    /**
     * the level name will be displayed at the top of the screen.
     * @return - the levelName.
     */
    @Override
    public String levelName() {
        return "Final Four";
    }
    /**
     * Returns a sprite with the background of the level
     * the level name will be displayed at the top of the screen.
     * @return - the getBackground.
     */
    @Override
    public Sprite getBackground() {
        return new BackgroundsOfLevels(4);
    }
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return - the list of the blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocksInLevel = new ArrayList<Block>();
        // storing the colors of the levels
        List<java.awt.Color> levelsColorsList = new ArrayList<java.awt.Color>();
        levelsColorsList.add(java.awt.Color.gray);
        levelsColorsList.add(java.awt.Color.red);
        levelsColorsList.add(java.awt.Color.yellow);
        levelsColorsList.add(java.awt.Color.green);
        levelsColorsList.add(java.awt.Color.white);
        levelsColorsList.add(java.awt.Color.pink);
        levelsColorsList.add(java.awt.Color.cyan);
        // creating the block to hit
        int yStartVal = 100;
        int width = 50;
        int height = 30;
        int xStartVal;
        // building the left blocks
        for (int i = 0; i < 7; i++) {
            xStartVal = 25;
            for (int j = 0; j < 15; j++) {
                Rectangle brick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
                // getting the right color for the specific level as we added to the color list
                java.awt.Color color = (Color) (levelsColorsList.get(i));
                Block newBrick = new Block((brick), new PutColorInBlock(color));
                if (i == 0) {
                    newBrick.setNumOfHits(2);
                } else {
                    newBrick.setNumOfHits(1);
                }
                blocksInLevel.add(newBrick);
                xStartVal +=  width;
            }
            yStartVal += height;
        }
        return blocksInLevel;
    }
    /**
     * @return - Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 105;
    }
}