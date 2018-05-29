/**
 * a CollisionInfo class- hold info about the collision.
 */
public class CollisionInfo {
    //members
    private Point collisionInfoPoint;
    private Collidable collisionInfoCollidable;
    // constructors
    /**
     * @param collisionInfoPoint - the point of the collision
     * @param collisionInfoCollidable - the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionInfoPoint, Collidable collisionInfoCollidable) {
        this.collisionInfoPoint = collisionInfoPoint;
        this.collisionInfoCollidable = collisionInfoCollidable;
    }
    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionInfoPoint;
    }
    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionInfoCollidable;
    }
}