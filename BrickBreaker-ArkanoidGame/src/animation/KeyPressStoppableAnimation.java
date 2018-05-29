package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * this is the key press stoppable animation class.
 */
public class KeyPressStoppableAnimation implements Animation {
    // members
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean run;
    //constructor
    /**
     * @param sensor - it is the keyboard
     * @param key - the key to press on
     * @param animation - it is the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.run = false;
    }
    /**
     * operate a frame of the game.
     * @param d is the surface to draw on it
  * @param dt specifies the amount of seconds passed since the last call
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(key)) {
            if (!this.isAlreadyPressed) {
                this.run = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }
    /**
     * @return true if animation should stop and false if other
     */
    @Override
    public boolean shouldStop() {
        return this.run;
    }
}
