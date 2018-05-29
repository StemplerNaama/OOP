package levels;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import arkanoid.BackgroundsOfLevels;
import arkanoid.Block;
import arkanoid.ColorsParser;
import arkanoid.PutColorInBlock;
import arkanoid.PutImageInBlock;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;
import io.BlocksDefinitionReader;
import io.BlocksFromSymbolsFactory;

/**
 * @author - this is the first level class.
 */
public class LevelOne implements LevelInformation {
    // constructor
    /**
     * this is the constructor.
     */
    public LevelOne() {
    }
    /**
     * @return - the number of the level's balls.
     */
    @Override
    public int numberOfBalls() {
        return 1;
    }
    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return - the list of the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocity = new ArrayList<Velocity>();
        Velocity ballVelocity = Velocity.fromAngleAndSpeed(90, 250);
        ballsVelocity.add(ballVelocity);
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
        return 150;
    }
    /**
     * the level name will be displayed at the top of the screen.
     * @return - the levelName.
     */
    @Override
    public String levelName() {
        return "Direct Hit";
    }
    /**
     * Returns a sprite with the background of the level
     * the level name will be displayed at the top of the screen.
     * @return - the getBackground.
     */
    @Override
    public Sprite getBackground() {
        return new BackgroundsOfLevels(1);
    }
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return - the list of the blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocksInLevel = new ArrayList<Block>();
        try {
            BlocksFromSymbolsFactory b = BlocksDefinitionReader.fromReader(new FileReader("/Users/Naama/workspace/ass3e/moon_block_definitions.txt"));
            if (b.isBlockSymbol("G")) {
                Block block = b.getBlock("G", 390, 158);
                blocksInLevel.add(block);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // creating the block to hit
        /*ColorsParser color = new ColorsParser();
        java.awt.Color color2 = color.colorFromString("red");
        Rectangle brick = new Rectangle(new Point(400 - 10, 158), 20, 20);
        Block newBrick = new Block((brick), new PutColorInBlock(color2), java.awt.Color.black);
        newBrick.setNumOfHits(1);*/
        return blocksInLevel;
    }
    /**
     * @return - Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}