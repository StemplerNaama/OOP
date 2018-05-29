package menu;


import java.util.ArrayList;
import java.util.List;
import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * this is the menu animation interface.
 * @param <T> - the type of menu
 */
public class MenuAnimation<T> implements Menu<T> {
    // members
    private String title;
    private AnimationRunner runner;
    private KeyboardSensor sensor;
    private List<String> keyList;
    private List<String> messageList;
    private List<T> returnValList;
    private T status;
    private boolean run;

    //constructor
    /**
     * @param title - the title of the menu animation
     * @param runner - this is the AnimationRunner
     * @param sensor - this is the keyboard
     */
    public MenuAnimation(String title, AnimationRunner runner, KeyboardSensor sensor) {
        this.title = title;
        this.runner = runner;
        this.sensor = sensor;
        this.keyList = new ArrayList<String>();
        this.messageList = new ArrayList<String>();
        this.returnValList = new ArrayList<T>();
        this.status = null;
        this.run = false;

    }
    /**
     * operate a frame of the game.
     * @param ds is the surface to draw on it
  * @param dt specifies the amount of seconds passed since the last call
     */
    @Override
    public void doOneFrame(DrawSurface ds, double dt) {
        // creating background menu
        ds.setColor(new java.awt.Color(77, 6, 91));
        ds.fillRectangle(0, 0, ds.getWidth(), ds.getHeight());
        ds.setColor(java.awt.Color.YELLOW);
        ds.drawText(300, 100, this.title, 50);
        for (int i = 0; i < this.messageList.size(); i++) {
            int startY = 50 * i + 200;
            int startX = 300;
          String printText = "(" + this.keyList.get(i) + ") " + this.messageList.get(i);
          ds.setColor(java.awt.Color.GREEN);
          ds.drawText(startX, startY, printText, 35);
        }
        for (int i = 0; i < this.returnValList.size(); i++) {
            if (this.sensor.isPressed(this.keyList.get(i))) {
                    this.status = this.returnValList.get(i);
                    this.run = true;
               
            }
        }
    }

    /**
     * @return true if animation should stop and false if other
     */
    @Override
    public boolean shouldStop() {
        return this.status != null;
    }
    /**
     * @param key - the key to press on
     * @param message - the description of what going to happen if key will pressed
     * @param returnVal - the task that should operate
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.returnValList.add(returnVal);
    }
    /**
     * @return the task that should operate
     */
    @Override
    public T getStatus() {
        return this.status;
    }
    /**
     * reset the boolean members.
     */
    public void reset() {
        this.status = null;
        this.run = false;

    }
}
