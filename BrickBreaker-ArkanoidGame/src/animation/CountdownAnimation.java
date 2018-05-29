package animation;
import arkanoid.SpriteCollection;
import biuoop.DrawSurface;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) seconds, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    // member
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private biuoop.Sleeper sleeper;
   /**
 * @param numOfSeconds -the num of seconds to display.
 * @param countFrom -the number to count down from
 * @param gameScreen -the sprite collection to add the coutdown numbers
 */
public CountdownAnimation(double numOfSeconds,
                             int countFrom,
                             SpriteCollection gameScreen) {
       this.numOfSeconds = numOfSeconds;
       this.countFrom = countFrom;
       this.gameScreen = gameScreen;
       this.stop = false;
       this.sleeper = new biuoop.Sleeper();
       }
/**
 * operate a frame of the game.
 * @param d is the surface to draw on it
 * @param dt specifies the amount of seconds passed since the last call
 */
@Override
public void doOneFrame(DrawSurface d, double dt) {
       //show the game during the "3..2..1..go"
       gameScreen.drawAllOn(d);
       if (countFrom == 3) {
           //draw the digit 3 on surface
           d.drawText((d.getWidth() / 2) , (d.getHeight() / 2) + 70, "3", 52);
           countFrom--;
           } else {
           if (countFrom == 0) {
               //if count from equal 0 draw "go"
               countFrom--;
               d.drawText((d.getWidth() / 2) , (d.getHeight() / 2) + 70, "GO", 52);
           } else if (countFrom < 0) {
               this.stop = true;

           } else {
               //continue the count down
               d.drawText((d.getWidth() / 2) , (d.getHeight() / 2) + 70 , "" + this.countFrom + "" , 52);
               countFrom--;
           }
           //displaying for 2 seconds
           this.sleeper.sleepFor((long) ((1000 * numOfSeconds) / 4));
       }
   }
/**
 * @return true if animation should stop and false if other
 */
@Override
public boolean shouldStop() {
       return this.stop;
   }
}