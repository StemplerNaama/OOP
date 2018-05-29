package arkanoid;
import biuoop.DrawSurface;
import core.CollisionInfo;
import core.Sprite;
import core.Velocity;
import geometry.Limit;
import geometry.Line;
import geometry.Point;

/**
 * A Ball class.
 */
public class Ball implements Sprite {
    //members
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;
    private Limit limit;
    private GameEnvironment gameEnvironment;
    /**
     * @param center - the center of ball
     * @param radius - the radius of ball
     * @param color - the color of ball
     */
    public Ball(Point center, int radius, java.awt.Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }
    /**
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param radius - the radius of ball
     * @param color - the color of ball
     */
    public Ball(double x, double y, int radius, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }
    /**
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param radius - the radius of ball
     * @param color - the color of ball
     * @param limit - the limits that the ball can move between
     */
    public Ball(double x, double y, int radius, java.awt.Color color, Limit limit) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.limit = limit;
    }
    /**
     * @param x - the x coordinate
     * @param y - the y coordinate
     * @param radius - the radius of ball
     * @param game - the game
     * @param color - the color of ball
     */
    public Ball(double x, double y, int radius, GameEnvironment game, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.gameEnvironment = game;
    }
    /**
     * @param center - the center of the ball
     * @param radius - the radius of ball
     * @param game - the game
     * @param color - the color of ball
     */
    public Ball(Point center, int radius, GameEnvironment game, java.awt.Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.velocity = new Velocity(0, 0);
        this.gameEnvironment = game;
    }
    /**
     * @return value of x of center
     */
    public double getX() {
        return  this.center.getX();
    }
    /**
     * @return value of y of center
     */
    public double getY() {
        return  this.center.getY();
    }
    /**
     * @return size of ball = radius
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * @return color of ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * notify objects to move.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }
    /**
     * @param d - the surface to draw the ball on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.getColor());
        d.fillCircle((int) this.getX(), (int) this.getY(), this.getSize());
        d.setColor(java.awt.Color.black);
        d.drawCircle((int) this.getX(), (int) this.getY(), this.getSize());
        d.setColor(java.awt.Color.red);
        d.fillCircle((int) this.getX(), (int) this.getY(), 2);
    }
    /**
     * func will redefine the new center of ball.
     * @param newCenter - the new center of the ball
     */
    public void setCenter(Point newCenter) {
        this.center = newCenter;
    }
    /**
     * func will redefine the new velocity of ball.
     * @param v - the new velocity of ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     * func will redefine the new velocity of ball.
     * @param dx - the change in x coordinate.
     * @param dy - the change in y coordinate.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    /**
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * @return the limits of the ball.
     */
    public Limit getLimit() {
        return this.limit;
    }
    /**
     * func will redefine the new game Environment of ball.
     * @param gameEnviron - the new gameEnvironment of ball
     */
    public void setGameEnvironment(GameEnvironment gameEnviron) {
        this.gameEnvironment = gameEnviron;
    }
    /**
     * @param gameLevel - the game that has the sprites
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
    /**
     * @param gameLevel - the game that has the sprites
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
    /**
     * move the ball one step.
     * @param dt - is specifies the amount of seconds passed since the last call
     */
    public void moveOneStep(double dt) {
        double endX = (this.getX() + this.getVelocity().getDx() * dt);
        double endY = (this.getY() + this.getVelocity().getDy() * dt);
        //trajectory- line from the current position of center to the next step of it.
        Line trajectory = new Line(this.getX(), this.getY(), endX, endY);
        CollisionInfo closestCollision = this.gameEnvironment.getClosestCollision(trajectory);
        // if moving on this trajectory won't hit anything.
        if (closestCollision == null) {
            this.center = this.getVelocity().applyToPoint(this.center, dt);
            } else {
            // if moving on this trajectory will hit something.
            // getting the collision point
            Point newcollisionPoint = closestCollision.collisionPoint();
            Point collisionPoint = new Point(newcollisionPoint.getX(), Math.round(newcollisionPoint.getY()));
            // call hit method to get the update velocity.
            Velocity newVelocity = closestCollision.collisionObject().hit(this, collisionPoint, this.velocity);
            // update velocity.
            this.setVelocity(newVelocity);
            double xValue = this.getX() + ((collisionPoint.getX() - this.getX()) * 0.9999);
            double yValue = this.getY() - ((this.getY() - collisionPoint.getY()) * 0.9999);
            Point almostP = new Point(xValue, yValue);
            // update the center almost to the collision point.
            this.setCenter(almostP);
        }
    }
}
