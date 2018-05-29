package core;
import arkanoid.Ball;
import geometry.Point;
import geometry.Rectangle;

/**
 * a Collidable interface.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     *  Notify the object that we collided with it at collisionPoint with
     *  a given velocity.
     * @param hitter - the ball that hits
     * @param collisionPoint - the collision point with line and block
     * @param currentVelocity - the velocity before it changes
     * @return is the new velocity expected after the hit (based on
     *  the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
