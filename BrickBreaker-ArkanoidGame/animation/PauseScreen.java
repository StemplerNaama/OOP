package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * this is the screen that in charge of the pausing screen.
 *
 */
public class PauseScreen implements Animation {
    private boolean stop;
   /**
 * @param k -this is the keyboard.
 */
public PauseScreen() {
      this.stop = false;
   }
/**
 * operate a frame of the game.
 * @param d is the surface to draw on it
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