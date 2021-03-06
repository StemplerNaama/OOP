package animation;

import biuoop.DrawSurface;

/**
 * an Animation interface.
 */
public interface Animation {
   /**
    * operate a frame of the game.
    * @param d is the surface to draw on it
 * @param dt specifies the amount of seconds passed since the last call
    */
    void doOneFrame(DrawSurface d, double dt);
   /**
    * @return true if animation should stop and false if other
    */
    boolean shouldStop();
    }