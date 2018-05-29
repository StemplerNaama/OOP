package animation;
import biuoop.DrawSurface;

/**
 * the EndScreen is responsible to display the end screen of the game.
 */
public class EndScreen implements Animation {
    //members
   private boolean stop;
   private int numberOfLives;
   private int gameScore;
   /**
 * @param numberOfLives -this is the number of lives
 * @param gameScore -this is the game's score
 */
public EndScreen(int numberOfLives, int gameScore) {
      this.stop = false;
      this.numberOfLives = numberOfLives;
      this.gameScore = gameScore;
   }
/**
 * operate a frame of the game.
 * @param d is the surface to draw on it
 * @param dt specifies the amount of seconds passed since the last call
 */
@Override
public void doOneFrame(DrawSurface d, double dt) {
       if (this.numberOfLives <= 0) {
           d.setColor(java.awt.Color.red);
           d.drawText(150, d.getHeight() / 2 - 100, "Game Over.", 100);
           d.setColor(java.awt.Color.blue);
           d.drawText(260, d.getHeight() / 2 + 50, "Your score is ", 50);
           d.drawText(370, d.getHeight() / 2 + 150, "" + this.gameScore + "", 50);
           d.drawText(35, 500, "press space to continue", 15);
       } else {
           d.setColor(java.awt.Color.red);
           d.drawText(210, d.getHeight() / 2 - 100, "You Win!", 100);
           d.setColor(java.awt.Color.blue);
           d.drawText(260, d.getHeight() / 2 + 50, "Your score is ", 50);
           d.drawText(370, d.getHeight() / 2 + 150, "" + this.gameScore + "", 50);
           d.drawText(35, 500, "press space to continue", 15);
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