package arkanoid;
import java.util.ArrayList;
import java.util.List;

import core.Collidable;
import core.CollisionInfo;
import geometry.Line;
import geometry.Point;

/**
 * GmeEnvironment Class.
 */
public class GameEnvironment {
    //members
    private List<Collidable> listOfCollidables;
    /**
     * add the given collidable to the environment.
     */
    public GameEnvironment() {
        this.listOfCollidables = new ArrayList<Collidable>();
    }
    /**
     * @param c - the collidable obect to add to list
     */
    public void addCollidable(Collidable c) {
        this.listOfCollidables.add(c);
    }
    /**
     * @param c -the collidable obect to remove to list
     */
    public void removeCollidable(Collidable c) {
        this.listOfCollidables.remove(c);
    }
    /**
     * @param trajectory - this is the current trajectory of the ball
     * @return - return the closest intersection point from all the blocks in the game
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // part 1- exchange the list of collidable objects in screen- to collisonInfo list.
        List<CollisionInfo> listRectsOnTrajectory = new ArrayList<CollisionInfo>();
        for (int i = 0; i < this.listOfCollidables.size(); i++) {
            Collidable col = (Collidable) this.listOfCollidables.get(i);
            Point inter = trajectory.closestIntersectionToStartOfLine(col.getCollisionRectangle());
            // if the block is on the trajectory- add it to the list
            if (inter != null) {
                // defining a collisionInfo new object
                CollisionInfo collisionRect = new CollisionInfo(inter, col);
                listRectsOnTrajectory.add(collisionRect);
            }
        }
        // if there is no blocks at all on the trajectory
        if (listRectsOnTrajectory.isEmpty()) {
            return null;
        } else {
            CollisionInfo closestRect = (CollisionInfo) listRectsOnTrajectory.get(0);
            double minDistance = trajectory.start().distance(closestRect.collisionPoint());
            for (int i = 1; i < listRectsOnTrajectory.size(); i++) {
                CollisionInfo closestTempRect = (CollisionInfo) listRectsOnTrajectory.get(i);
                Point closestPoint = closestTempRect.collisionPoint();
                // update the closest intersection point by its minimal distance to start of line.
                double tempDistance = trajectory.start().distance(closestPoint);
                if (tempDistance < minDistance) {
                    closestRect = closestTempRect;
                    minDistance = tempDistance;
                }
            }
            return closestRect;
        }
    }
}
