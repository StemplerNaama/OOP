package animation;

import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * the class that runs an animation.
 */
public class AnimationRunner {
    // members
   private GUI gui;
   private int framesPerSecond;
   private biuoop.Sleeper sleeper;

   //constructor
   /**
 * constructor.
 * @param framesPerSec - the FPS
 */
public AnimationRunner(int framesPerSec) {
       this.gui = new GUI("Arkanoid", 800, 600);
       this.framesPerSecond = framesPerSec;
       this.sleeper = new biuoop.Sleeper();
   }
   /**
 * @return GUI - the gui surface
 */
   public GUI getGui() {
       return this.gui;
   }
   /**
 * @param animation - the animation to run
 */
public void run(Animation animation) {
       long millisecondsPerFrame = 1000 / this.framesPerSecond;
      while (!animation.shouldStop()) {
         long startTime = System.currentTimeMillis(); // timing
         DrawSurface d = this.gui.getDrawSurface();
         double dt = 1.0 / this.framesPerSecond;
         animation.doOneFrame(d, dt);
         if (!animation.shouldStop()) {
             this.gui.show(d);
         }
         //this.gui.show(d);
         long usedTime = System.currentTimeMillis() - startTime;
         long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
         if (milliSecondLeftToSleep > 0) {
             this.sleeper.sleepFor(milliSecondLeftToSleep);
         }
      }
   }
/**
 * @return the width of the screen according to the gui in animationRunner
 */
public int getWidthOfScreen() {
    return this.getGui().getDrawSurface().getWidth();
    }
/**
 * @return the height of the screen according to the gui in animationRunner
 */
public int getHeigtOfScreen() {
    return this.getGui().getDrawSurface().getHeight();
    }
}