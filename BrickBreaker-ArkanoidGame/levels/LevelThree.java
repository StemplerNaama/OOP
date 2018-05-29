package levels;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import arkanoid.BackgroundsOfLevels;
import arkanoid.Block;
import arkanoid.PutColorInBlock;
import arkanoid.PutImageInBlock;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * @author - this is the third level class.
 */
public class LevelThree implements LevelInformation {
    // constructor
    /**
     * this is the constructor.
     */
    public LevelThree() {
    }
    /**
     * @return - the number of the level's balls.
     */
    @Override
    public int numberOfBalls() {
        return 2;
    }
    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - the list of the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocity = new ArrayList<Velocity>();
        ballsVelocity.add(Velocity.fromAngleAndSpeed(45, 370));
        ballsVelocity.add(Velocity.fromAngleAndSpeed(180 - 45, 370));
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
        return "Green 3";
    }
    /**
     * Returns a sprite with the background of the level
     * the level name will be displayed at the top of the screen.
     * @return - the getBackground.
     */
    @Override
    public Sprite getBackground() {
        return new BackgroundsOfLevels(3);
    }
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return - the list of the blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocksInLevel = new ArrayList<Block>();
        // creating the blocks by levels
        int widthOfBrick = 50;
        int heightOfBrick = 30;
        int numOfBricksForFirstLast = 10;
        int yValStartBricksFromHere = 150;
        // storing the colors of the levels
        List<java.awt.Color> levelsColorsList = new ArrayList<java.awt.Color>();
        levelsColorsList.add(java.awt.Color.gray);
        levelsColorsList.add(java.awt.Color.red);
        levelsColorsList.add(java.awt.Color.yellow);
        levelsColorsList.add(java.awt.Color.blue);
        levelsColorsList.add(java.awt.Color.pink);
        levelsColorsList.add(java.awt.Color.green);
        Image img = null;
        try {
            img = ImageIO.read(new File("/Users/Naama/workspace/ass3e/zebra.jpg"));
        } catch (IOException e) {
            
        }
        for (int i = 0; i < 5; i++) {
            int xValStartBricksFromHere = 800 - 25 - widthOfBrick;
            int j;
     
            
            for (j = 0; j < numOfBricksForFirstLast; j++) {
                Rectangle brick = new Rectangle(new Point(xValStartBricksFromHere, yValStartBricksFromHere),
                        widthOfBrick, heightOfBrick);
                // getting the right color for the specific level as we added to
                // the color list
                java.awt.Color color = (Color) (levelsColorsList.get(i));
                Block newBrick = new Block((brick), new PutColorInBlock(color), java.awt.Color.black);
                newBrick.addFillToMap(2, new PutImageInBlock(img));
                // initialize the top level with "2" in the middle and "1" in
                // the rest levels
                if (i == 0) {
                    newBrick.setNumOfHits(2);
                } else {
                    newBrick.setNumOfHits(1);
                }
                // adding the new block to the game
                blocksInLevel.add(newBrick);
                xValStartBricksFromHere -= widthOfBrick;
            }
            yValStartBricksFromHere += heightOfBrick;
            // every level has less bricks than the one above
            numOfBricksForFirstLast--;
        }
        return blocksInLevel;
    }
    /**
     * @return - Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 40;
    }
}