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
 * @author - this is the second level class.
 */
public class LevelTwo implements LevelInformation {
    // constructor
    /**
     * this is the constructor.
     */
    public LevelTwo() {
    }
    /**
     * @return - the number of the level's balls.
     */
    @Override
    public int numberOfBalls() {
        return 10;
    }
    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - the list of the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocity = new ArrayList<Velocity>();
        int angle = 20;
        for (int i = 0; i < this.numberOfBalls() / 2; i++) {
        Velocity ballVelocity = Velocity.fromAngleAndSpeed(angle, 350);
        ballsVelocity.add(ballVelocity);
        Velocity ballVelocity2 = Velocity.fromAngleAndSpeed(180 - angle, 350);
        ballsVelocity.add(ballVelocity2);
        angle += 15;
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
        return 610;
    }
    /**
     * the level name will be displayed at the top of the screen.
     * @return - the levelName.
     */
    @Override
    public String levelName() {
        return "Wide Easy";
    }
    /**
     * Returns a sprite with the background of the level
     * the level name will be displayed at the top of the screen.
     * @return - the getBackground.
     */
    @Override
    public Sprite getBackground() {
        return new BackgroundsOfLevels(2);
    }
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return - the list of the blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocksInLevel = new ArrayList<Block>();
        // creating the block to hit
        int yStartVal = 200;
        int width = 50;
        int height = 30;
        int xStartVal = 25;
        // storing the colors of the levels
        List<java.awt.Color> levelsColorsList = new ArrayList<java.awt.Color>();
        levelsColorsList.add(java.awt.Color.red);
        levelsColorsList.add(java.awt.Color.orange);
        levelsColorsList.add(java.awt.Color.yellow);
        levelsColorsList.add(java.awt.Color.green);
        levelsColorsList.add(java.awt.Color.blue);
        levelsColorsList.add(java.awt.Color.pink);
        levelsColorsList.add(java.awt.Color.cyan);
        // building the left blocks
        for (int i = 0; i < 3; i++) {
            Rectangle brick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
            Rectangle brick2 = new Rectangle(new Point(xStartVal + width, yStartVal), width, height);
            // getting the right color for the specific level as we added to the color list
            java.awt.Color color = (Color) (levelsColorsList.get(i));
            Block newBrick = new Block((brick), new PutColorInBlock(color), java.awt.Color.black);
            Block newBrick2 = new Block((brick2), new PutColorInBlock(color), java.awt.Color.black);
            newBrick.setNumOfHits(1);
            newBrick2.setNumOfHits(1);
            blocksInLevel.add(newBrick);
            blocksInLevel.add(newBrick2);
            xStartVal += (2 * width);
        }
        // building the middle blocks
        for (int i = 0; i < 3; i++) {
            Rectangle brick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
            // getting the right color for the specific level as we added to the color list
            Block newBrick = new Block((brick), new PutColorInBlock(levelsColorsList.get(3)), java.awt.Color.black);
            newBrick.setNumOfHits(1);
            blocksInLevel.add(newBrick);
            xStartVal += width;
        }
        // building the right blocks
        for (int i = 4; i < 7; i++) {
            Rectangle brick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
            Rectangle brick2 = new Rectangle(new Point(xStartVal + width, yStartVal), width, height);
            // getting the right color for the specific level as we added to the color list
            java.awt.Color color = (Color) (levelsColorsList.get(i));
            Block newBrick = new Block((brick),new PutColorInBlock(color), java.awt.Color.black);
            Block newBrick2 = new Block((brick2), new PutColorInBlock(color), java.awt.Color.black);
            newBrick.setNumOfHits(1);
            newBrick2.setNumOfHits(1);
            blocksInLevel.add(newBrick);
            blocksInLevel.add(newBrick2);
            xStartVal += (2 * width);
        }
        return blocksInLevel;
    }
    /**
     * @return - Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 15;
    }
}