package Menu;
import java.util.ArrayList;
import java.util.List;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class MenuAnimation<T> implements Menu<T> {
    // members
    private String title;
    private AnimationRunner runner;
    private KeyboardSensor sensor;
    private List<String> keyList;
    private List<String> messageList;
    private List<T> returnValList;
    private List<Menu<T>> subMenus;
    private List<Boolean> haveSubMenu;
    private T status;
    //private boolean run;
    //constructor
    public MenuAnimation(String title, AnimationRunner runner, KeyboardSensor sensor) {
        this.title = title;
        this.runner = runner;
        this.sensor = sensor;
        this.keyList = new ArrayList<String>();
        this.messageList = new ArrayList<String>();
        this.returnValList = new ArrayList<T>();
        this.haveSubMenu = new ArrayList<Boolean>();
        this.subMenus = new ArrayList<Menu<T>>();
        //this.run = false;
        this.status = null;
    }
    @Override
    public void doOneFrame(DrawSurface ds, double dt) {
        // creating background menu
        ds.setColor(new java.awt.Color(77, 6, 91));
        ds.fillRectangle(0, 0, ds.getWidth(), ds.getHeight());
        ds.setColor(java.awt.Color.YELLOW);
        ds.drawText(300, 100, this.title, 50);
        for (int i = 0; i < this.messageList.size(); i++)
        {
            int start_Y = 50 * i + 200;
            int start_X = 300;
          String printText = "(" + this.keyList.get(i) + ") " + this.messageList.get(i);
          ds.setColor(java.awt.Color.GREEN);
          ds.drawText(start_X, start_Y, printText, 35);
        }
        for (int i = 0; i < this.returnValList.size(); i++) {
            if (this.sensor.isPressed(this.keyList.get(i))) {
                if(this.haveSubMenu.get(i)) {
                    Menu<T> subMenuTemp = this.subMenus.get(i);
                    this.runner.run(subMenuTemp);
                    this.status = subMenuTemp.getStatus();
                    //this.run = true;
                    subMenuTemp.reset();
                    break;
                //this.run = true;
                } else {
                    this.status = this.returnValList.get(i);
                    break;

                }
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.status != null;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.returnValList.add(returnVal);
        this.subMenus.add(null);
        this.haveSubMenu.add(false);
    }

    @Override
    public T getStatus() {
        return this.status;
    }
    public void reset() {
        this.status = null;
        //this.run = false;
    }
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.returnValList.add(null);
        this.subMenus.add(subMenu);
        this.haveSubMenu.add(true);


    }
}
