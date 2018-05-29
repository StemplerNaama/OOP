package animation;

import arkanoid.HighScoresTable;
import biuoop.DrawSurface;

/**
 * this is the high scores animation class.
 */
public class HighScoresAnimation implements Animation {
    // members
    private HighScoresTable scores;
    private boolean stop;
    // constructor
    /**
     * @param scores is the high scores table game
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }
    /**
     * operate a frame of the game.
     * @param d is the surface to draw on it
  * @param dt specifies the amount of seconds passed since the last call
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(java.awt.Color.GREEN);
        d.drawText(40, 80 , "High Scores", 50);
        d.setColor(java.awt.Color.blue);
        d.drawText(170, 200 , "Player Name", 32);
        d.drawText(500, 200 , "Score", 32);
        d.drawLine(170, 220, 630, 220);
        int place = 270;
        d.setColor(java.awt.Color.BLACK);
        // draw the high scores
        for (int i = 0; i < scores.getHighScores().size(); i++) {
            d.drawText(170, place, scores.getHighScores().get(i).getName(), 35);
            d.drawText(500, place, Integer.toString(scores.getHighScores().get(i).getScore()) , 35);
            place += 40;
        }
        d.setColor(java.awt.Color.red);
        d.drawText(200, 520, "press space to continue", 32);
    }
    /**
     * @return true if animation should stop and false if other
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
