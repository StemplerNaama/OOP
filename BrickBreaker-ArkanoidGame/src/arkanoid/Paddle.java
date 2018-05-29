package arkanoid;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import core.Collidable;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

/**
 * the Paddle Class.
 */
public class Paddle implements Sprite, Collidable {
    //members
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle;
    private java.awt.Color color;
    private int widthOfScreen;
    private int thickOfBorders;
    private int paddleSpeed;
    // constructor
    /**
     * @param keyboard - the keyboard
     * @param paddle - the paddle to play with
     * @param color - the paddle's color
     * @param widthOfScreen - the width of screen to define the paddle's limits
     * @param heightOfScreen - the height of screen to define the paddle's limits
     * @param thickOfBorders - the thick of the borders
     * @param paddleSpeed - the speed of the paddle
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle paddle, java.awt.Color color, int widthOfScreen,
            int heightOfScreen, int thickOfBorders, int paddleSpeed) {
        this.keyboard = keyboard;
        this.paddle = paddle;
        this.color = color;
        this.widthOfScreen = widthOfScreen;
        this.thickOfBorders = thickOfBorders;
        this.paddleSpeed = paddleSpeed;
    }
    /**
     * move the paddle 10 steps to the left side.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    public void moveLeft(double dt) {
        int dx = (int) (this.paddleSpeed * dt);
        double xValueCorner = this.paddle.getUpperLeft().getX();
        double yValueCorner = this.paddle.getUpperLeft().getY();
        if (xValueCorner - dx >= this.thickOfBorders) {
            // defining the new location of the paddle after moving
            Point newCorner = new Point(xValueCorner - dx, yValueCorner);
            this.setRectToPaddle(new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight()));
        } else {
            // defining the new location of the paddle after moving
            Point newCorner = new Point(this.thickOfBorders, yValueCorner);
            this.setRectToPaddle(new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight()));
        }
    }
    /**
     * move the paddle 10 steps to the left side.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    public void moveRight(double dt) {
        int dx = (int) (this.paddleSpeed * dt);
        double xValueCorner = this.paddle.getUpperLeft().getX();
        double yValueCorner = this.paddle.getUpperLeft().getY();
        if (xValueCorner <= this.widthOfScreen - thickOfBorders - this.paddle.getWidth() - dx) {
            // defining the new location of the paddle after moving
            Point newCorner = new Point(xValueCorner + dx, yValueCorner);
            this.setRectToPaddle(new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight()));
        } else {
            // defining the new location of the paddle after moving
            Point newCorner = new Point(this.widthOfScreen - thickOfBorders - this.paddle.getWidth(), yValueCorner);
            this.setRectToPaddle(new Rectangle(newCorner, this.paddle.getWidth(), this.paddle.getHeight()));
        }
    }
    /**
     * notify objects to move.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    @Override
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }
    /**
     * @param d - the surface to draw on it.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
        d.setColor(java.awt.Color.black);
        d.drawRectangle((int) this.paddle.getUpperLeft().getX(), (int) this.paddle.getUpperLeft().getY(),
                (int) this.paddle.getWidth(), (int) this.paddle.getHeight());
    }
    /**
     * @return the "collision shape" of the object.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }
    /**
     *  Notify the object that we collided with it at collisionPoint with
     *  a given velocity.
     * @param hitter - the ball that hits
     * @param collisionPoint - the collision point with line and block
     * @param currentVelocity - the velocity before it changes
     * @return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        /* dividing the width of the paddle to 5 regions
         * each of the region returns a new velocity when the ball hits it
         */
        double lengthOfRegion = this.paddle.getWidth() / 5;
        double limitRegion1 = this.paddle.getUpperLeft().getX() + lengthOfRegion;
        double limitRegion2 = this.paddle.getUpperLeft().getX() + 2 * lengthOfRegion;
        double limitRegion3 = this.paddle.getUpperLeft().getX() + 3 * lengthOfRegion;
        double limitRegion4 = this.paddle.getUpperLeft().getX() + 4 * lengthOfRegion;
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx() + currentVelocity.getDy()
        * currentVelocity.getDy());
        boolean betweenX = false;
        boolean betweenY = false;
        if ((collisionPoint.getX() >= this.paddle.getUpperLeft().getX())
                && (collisionPoint.getX() <= this.paddle.getUpperLine().end().getX())) {
            betweenX = true;
            }
        if ((collisionPoint.getY() >= this.paddle.getUpperLeft().getY())
                && (collisionPoint.getY() <= this.paddle.getDownLine().end().getY())) {
                    betweenY = true;
                    }
        // if the collision point is in the paddle
        if (betweenX && betweenY) {
            double xValue = this.middlePaddle().getX();
            double yValue = this.paddle.getUpperLeft().getY() - 5;
            Point almostP = new Point(xValue, yValue);
            // update the center almost to the collision point.
            hitter.setCenter(almostP);
        }
        if (collisionPoint.getX() >= this.paddle.getUpperLeft().getX() && collisionPoint.getX() < limitRegion1) {
            currentVelocity = Velocity.fromAngleAndSpeed(150, speed);
        } else if (collisionPoint.getX() >= limitRegion1 && collisionPoint.getX() < limitRegion2) {
            currentVelocity = Velocity.fromAngleAndSpeed(120, speed);
        } else if (collisionPoint.getX() >= limitRegion2 && collisionPoint.getX() < limitRegion3) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -1 * currentVelocity.getDy());
        } else if (collisionPoint.getX() >= limitRegion3 && collisionPoint.getX() < limitRegion4) {
            currentVelocity = Velocity.fromAngleAndSpeed(60, speed);
        } else if (collisionPoint.getX() >= limitRegion4 && collisionPoint.getX()
                <= this.paddle.getUpperLeft().getX() + this.paddle.getWidth()) {
            currentVelocity = Velocity.fromAngleAndSpeed(30, speed);
        }
        return currentVelocity;
    }

    /**
     * Add this paddle to the game.
     * @param gameLevel - the game to add the paddle to
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }
    /**
     * @param gameLevel -this is the game that from it- we remove the paddle
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }
    /**
     * @param rect -this is the rectangle to set the paddle to be alike
     */
    public void setRectToPaddle(Rectangle rect) {
        this.paddle = rect;
    }
    /**
     * @return the middle of the paddle
     */
    public Point middlePaddle() {
        return this.paddle.getUpperLine().middle();
    }
    /**
     * @return -this is the speed of the paddle
     */
    public int getSpeed() {
        return this.paddleSpeed;
    }
    /**
     * @return -this is the width of the paddle
     */
    public int getWidth() {
        return (int) this.paddle.getUpperLine().length();
    }
}
