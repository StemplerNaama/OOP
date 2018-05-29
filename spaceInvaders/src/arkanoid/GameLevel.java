package arkanoid;

import java.util.List;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import core.Collidable;
import core.Counter;
import core.Sprite;

/**
 * the GameLevel Class.
 */
public class GameLevel implements Animation {
    // members
    private AnimationRunner runner;
    private boolean running;
    //private Paddle paddle;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private biuoop.KeyboardSensor keyboard;
    private Counter numberOfAliensToRemove;
    private Counter numberOfShields;
    private Counter scoreCounter;
    private Counter livesCounter;
    private Level level;
    // constructor
    /**
     * GameLevel Constructor.
     * @param keyboard -this is the keyboard
     * @param runner -this is the animation runner
     * @param livesCounter -this is the lives counter
     * @param scoreCounter -this is the score counter
     */
    public GameLevel(biuoop.KeyboardSensor keyboard, AnimationRunner runner,
            Counter livesCounter, Counter scoreCounter, Level level) {
        this.runner = runner;
        this.running = true;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.level = level;
        this.numberOfAliensToRemove = new Counter(this.level.numberOfAliensToRemove());
        this.numberOfShields = new Counter(this.level.numberOfShields());
        // Initializing the number of balls to be 0 at the beginning
        this.scoreCounter = scoreCounter;
        this.livesCounter = livesCounter;
        this.keyboard = keyboard;
        // Initializing paddle
        /*int paddleWidth = 80;
        int paddleSpeed = 600;
        int paddleHeight = 25;
        Point cornerPaddle = new Point(this.runner.getWidthOfScreen() / 2 - paddleWidth / 2,
                this.runner.getHeigtOfScreen() - 30);
        Rectangle paddleRect = new Rectangle(cornerPaddle, paddleWidth, paddleHeight);
        this.paddle = new Paddle(this.keyboard, paddleRect, java.awt.Color.orange,
                this.runner.getWidthOfScreen(), this.runner.getHeigtOfScreen(), paddleSpeed);*/
        
    }
    /**
     * @param c - a collidable object to add to the game environment
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    /**
     * @param c -the collidable obect to remove to list
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    /**
     * @param s - a sprite object to add to the game environment
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * @param s -the sprite obect to remove from list
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        // set background
        this.level.getBackground().addToGame(this);
        // add level name
        String s = this.level.levelName();
        System.out.println(s);
        LevelNameIndicator levelName = new LevelNameIndicator(this.level.levelName());
        levelName.addToGame(this);
        // add score viewer
        ScoreIndicator scoreToPrint = new ScoreIndicator(this.scoreCounter);
        scoreToPrint.addToGame(this);
        // add lives viewer
        LivesIndicator livesToPrint = new LivesIndicator(this.livesCounter);
        livesToPrint.addToGame(this);
        BlockRemover aliensRemover = new BlockRemover(this, this.numberOfAliensToRemove);
        BlockRemover shieldsRemover = new BlockRemover(this, this.numberOfShields);
        //BallRemover ballR = new BallRemover(this, this.numOfBalls);
        ScoreTrackingListener gameScore = new ScoreTrackingListener(this.scoreCounter);
        System.out.println("end initialize in game level");

        // adding the borders to the games
        List<Block> shieldsList = this.level.createShieldsList();
        List<Block> aliensList = this.level.createAliensList();
        
        int sizeShieldsList = shieldsList.size();
        int sizeAliensList = aliensList.size();
        // adding the new blocks to the game
        for (int i = 0; i < sizeShieldsList; i++) {
            Block newBlock = shieldsList.get(i);
            newBlock.addHitListener(shieldsRemover);
            newBlock.addHitListener(gameScore);
            newBlock.addToGame(this);
            }
        for (int i = 0; i < sizeAliensList; i++) {
            Block newBlock = aliensList.get(i);
            newBlock.addHitListener(aliensRemover);
            newBlock.addHitListener(gameScore);
            newBlock.addToGame(this);
            }
        // adding a paddle
        this.level.getPaddle().addToGame(this);

    }
    /**
     * the method creates everytime the balls- while their centers are a slightly above the paddle.
     */
    /*public void createBallsOnTopOfPaddle() {
     // setting the paddle to the center
        Point cornerPaddle = new Point(this.runner.getWidthOfScreen() / 2 - level.paddleWidth() / 2,
                this.runner.getHeigtOfScreen() - 25 - 25);
        this.paddle.setRectToPaddle(new Rectangle(cornerPaddle, level.paddleWidth(), 20));
        //creating ball from the top of the paddle's middle
        Point ballsCenter = new Point(this.paddle.middlePaddle().getX(), this.paddle.middlePaddle().getY() - 5);
        for (int i = 0; i < this.level.numberOfBalls(); i++) {
            Ball newBall = new Ball(ballsCenter, 10, this.environment, java.awt.Color.white);
            newBall.setVelocity(this.level.initialBallVelocities().get(i));
            newBall.addToGame(this);
        }
     // adding the ball to the game
        this.numOfBalls.increase(this.level.numberOfBalls());
    }*/
    // Run the game -- start the animation loop.
    /**
     * the method runs the game.
     */
    public void playOneTurn() {
        //this.createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites)); // countdown before turn starts.
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }
    /**
     * @param bool - determine the running status
     */
    public void setRunning(Boolean bool) {
        this.running = bool;
    }
    /**
     * @return -return the running status
     */
    public Boolean getRunning() {
        return this.running;
    }
    /**
     * operate a frame of the game.
     * @param d is the surface to draw on it
     * @param dt specifies the amount of seconds passed since the last call
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY , new PauseScreen()));
         }
        this.shouldFinish();
    }
    /**
     * @return true if animation should stop and false if other
     */
    @Override
    public boolean shouldStop() {
        return !this.getRunning();
    }
    /**
     * @return -return "true" if there are still blocks in the game. and "false" if else.
     */
    public boolean isRemainBlocks() {
        return (this.numberOfAliensToRemove.getValue() > 0);
    }
    /**
     * if the level should finish this method will increase the score counter or decrease the num of lives.
     */
    public void shouldFinish() {
        //if all the balls fell out the screen, decrease life.
        /*if (this.numOfBalls.getValue() == 0) {
            this.livesCounter.decrease(1);
            this.setRunning(false);
        }*/
        if (this.numberOfAliensToRemove.getValue() == 0) {
            this.scoreCounter.increase(100);
            this.setRunning(false);
        }
    }
}
