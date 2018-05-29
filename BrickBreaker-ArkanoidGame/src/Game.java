import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * the Game Class.
 */
public class Game {
    // members
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.GUI gui;
    // constructor
    /**
     * Game Constructor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("test", 800, 700);
    }
    /**
     * @param c - a collidable object to add to the game environment
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    /**
     * @param s - a sprite object to add to the game environment
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        // getting the screen sizes
        int widthOfScreen = this.gui.getDrawSurface().getWidth();
        int heightOfScreen = this.gui.getDrawSurface().getHeight();
        // creating the borders of the game
        int thickOfBorders = 30;
        Rectangle upWall = new Rectangle(new Point(0, 0), widthOfScreen, thickOfBorders);
        Rectangle downWall = new Rectangle(new Point(thickOfBorders, heightOfScreen - thickOfBorders),
                widthOfScreen - 2 * thickOfBorders, thickOfBorders);
        Rectangle leftWall = new Rectangle(new Point(0, thickOfBorders), thickOfBorders,
                heightOfScreen - thickOfBorders);
        Rectangle rightWall = new Rectangle(new Point(widthOfScreen - thickOfBorders, thickOfBorders),
                thickOfBorders,  heightOfScreen - thickOfBorders);
        // adding the borders to the game
        (new Block(upWall)).addToGame(this);
        (new Block(downWall)).addToGame(this);
        (new Block(leftWall)).addToGame(this);
        (new Block(rightWall)).addToGame(this);
        // storing the colors of the levels
        List levelsColorsList = new ArrayList();
        levelsColorsList.add(java.awt.Color.gray);
        levelsColorsList.add(java.awt.Color.red);
        levelsColorsList.add(java.awt.Color.yellow);
        levelsColorsList.add(java.awt.Color.blue);
        levelsColorsList.add(java.awt.Color.pink);
        levelsColorsList.add(java.awt.Color.green);
        // creating the blocks by levels
        int widthOfBrick = 50;
        int heightOfBrick = 30;
        int numOfBricksForFirstLast = 12;
        int yValStartBricksFromHere = heightOfScreen - 550;
        for (int i = 0; i < 6; i++) {
            int xValStartBricksFromHere = widthOfScreen - thickOfBorders - widthOfBrick;
            int j;
            for (j = 0; j < numOfBricksForFirstLast; j++) {
                Rectangle brick = new Rectangle(new Point(xValStartBricksFromHere, yValStartBricksFromHere),
                        widthOfBrick, heightOfBrick);
                // getting the right color for the specific level as we added to the color list
                java.awt.Color color = (Color) (levelsColorsList.get(i));
                Block newBrick = new Block((brick), color);
                // initialize the top level with "2" in the middle and "1" in the rest levels
                if (i == 0) {
                    newBrick.setNumOfHits(2);
                } else {
                    newBrick.setNumOfHits(1);
                }
                // adding the new block to the game
                newBrick.addToGame(this);
                xValStartBricksFromHere -= widthOfBrick;
            }
            yValStartBricksFromHere += heightOfBrick;
            // every level has less bricks than the one above
            numOfBricksForFirstLast--;
        }
        // creating a paddle
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Rectangle paddleRect = new Rectangle(new Point(widthOfScreen / 2 - 50,
                heightOfScreen - thickOfBorders - 25), 100, 25);
        Paddle paddle = new Paddle(keyboard, paddleRect, java.awt.Color.orange, widthOfScreen,
                heightOfScreen, thickOfBorders);
        paddle.addToGame(this);
        // creating the ball and send the blocks list as "environment"
        Ball ball1 = new Ball(580, 480, 10, this.environment, java.awt.Color.white);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(-45, 8));
        Ball ball2 = new Ball(200, 480, 10, this.environment, java.awt.Color.magenta);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(30, 8));
        // adding the ball to the game
        ball1.addToGame(this);
        ball2.addToGame(this);
    }
    // Run the game -- start the animation loop.
    /**
     * the method runs the game.
     */
    public void run() {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Block background = new Block((new Rectangle(new Point(0, 0), this.gui.getDrawSurface().getWidth(),
                this.gui.getDrawSurface().getHeight())),
                java.awt.Color.blue.darker().darker());
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            background.drawBackground(d);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}