package animation;
import biuoop.DrawSurface;

/**
 * this is the screen that in charge of the pausing screen.
 *
 */
public class PauseScreen implements Animation {
    //members
    private boolean stop;
    //constructor
/**
 * constructor- initializing boolean stop- to false.
 */
public PauseScreen() {
      this.stop = false;
   }
/**
 * operate a frame of the game.
 * @param d is the surface to draw on it
 * @param dt specifies the amount of seconds passed since the last call
 */
@Override
public void doOneFrame(DrawSurface d, double dt) {
    d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);

    }
/**
 * @return true if animation should stop and false if other
 */
@Override
public boolean shouldStop() {
       return this.stop;
       }
}