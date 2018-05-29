import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * the Paddle Class.
 */
public class Paddle implements Sprite, Collidable {
    //members
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle;
    private java.awt.Color color;
    private int heightOfScreen;
    private int widthOfScreen;
    private int thickOfBorders;
    // constructor
    /**
     * @param keyboard - the keyboard
     * @param paddle - the paddle to play with
     * @param color - the paddle's color
     * @param widthOfScreen - the width of screen to define the paddle's limits
     * @param heightOfScreen - the height of screen to define the paddle's limits
     * @param thickOfBorders - the thick of the borders
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle paddle, java.awt.Color color, int widthOfScreen,
            int heightOfScreen, int thickOfBorders) {
        this.keyboard = keyboard;
        this.paddle = paddle;
        this.color = color;
        this.widthOfScreen = widthOfScreen;
        this.heightOfScreen = heightOfScreen;
        this.thickOfBorders = thickOfBorders;
    }
    /**
     * move the paddle 10 steps to the left side.
     */
    public void moveLeft() {
        double xValueCorner = this.paddle.getUpperLeft().getX() - 10;
        double yValueCorner = this.paddle.getUpperLeft().getY();
        if (xValueCorner < thickOfBorders) {
            xValueCorner = this.paddle.getUpperLeft().getX();
        }
        // defining the new location of the paddle after moving
        Point newCorner = new Point(xValueCorner, yValueCorner);
        this.paddle = new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight());
    }
    /**
     * move the paddle 10 steps to the left side.
     */
    public void moveRight() {
        double xValueCorner = this.paddle.getUpperLeft().getX() + 10;
        double yValueCorner = this.paddle.getUpperLeft().getY();
        if (xValueCorner + this.paddle.getWidth() > this.widthOfScreen - thickOfBorders) {
            xValueCorner = this.widthOfScreen - thickOfBorders - this.paddle.getWidth();
        }
     // defining the new location of the paddle after moving
        Point newCorner = new Point(xValueCorner, yValueCorner);
        this.paddle = new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight());
    }
    // Sprite
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        d.setColor(java.awt.Color.black);
        d.drawRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }
    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        /* deviding the width of the paddle to 5 regions
         * each of the region returns a new velocity when the ball hits it
         */
        double lengthOfRegion = this.paddle.getWidth() / 5;
        double limitRegion1 = this.paddle.getUpperLeft().getX() + lengthOfRegion;
        double limitRegion2 = this.paddle.getUpperLeft().getX() + 2 * lengthOfRegion;
        double limitRegion3 = this.paddle.getUpperLeft().getX() + 3 * lengthOfRegion;
        double limitRegion4 = this.paddle.getUpperLeft().getX() + 4 * lengthOfRegion;
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx() + currentVelocity.getDy()
        * currentVelocity.getDy());
        if (collisionPoint.getX() >= this.paddle.getUpperLeft().getX() && collisionPoint.getX() < limitRegion1) {
            currentVelocity = Velocity.fromAngleAndSpeed(150, speed);
        } else if (collisionPoint.getX() >= limitRegion1 && collisionPoint.getX() < limitRegion2) {
            currentVelocity = Velocity.fromAngleAndSpeed(120, speed);
        } else if (collisionPoint.getX() >= limitRegion2 && collisionPoint.getX() < limitRegion3) {
            currentVelocity = Velocity.fromAngleAndSpeed(90, speed);
        } else if (collisionPoint.getX() >= limitRegion3 && collisionPoint.getX() < limitRegion4) {
            currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
        } else if (collisionPoint.getX() >= limitRegion4 && collisionPoint.getX()
                <= this.paddle.getUpperLeft().getX() + this.paddle.getWidth()) {
            currentVelocity = Velocity.fromAngleAndSpeed(30, speed);
        }
        return currentVelocity;
    }

    // Add this paddle to the game.
    /**
     * @param game - the game to add the paddle to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
}
