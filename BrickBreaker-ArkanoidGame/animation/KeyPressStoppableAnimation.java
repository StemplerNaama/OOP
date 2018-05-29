package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
    // members
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean run;
    //constructor
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.run = false;
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(key)) {
            if (this.isAlreadyPressed == false) {
                this.run = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
        
    }
    @Override
    public boolean shouldStop() {
        return this.run;
    }
    

}
