package arkanoid;
import biuoop.DrawSurface;
import core.Counter;
import core.Sprite;

/**
 * the ScoreIndicator class is in charge of adding the score as a sprite and showing it.
 *
 */
public class ScoreIndicator implements Sprite {
    //members
    private Counter scoreCounter;
    //constructor
    /**
     * @param scoreCounter -this is the score of the game.
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }
    /**
     * draw the sprite to the screen.
     * @param surface - the surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(java.awt.Color.white);
        surface.fillRectangle(350, 0, 200, 25);
        // will write the number of hits in white font
        surface.setColor(java.awt.Color.black);
        surface.drawText(350, 20, "Score: " + this.scoreCounter.getValue() + "", 15);
    }
    /**
     * notify the sprite that time has passed
     * notify objects to move.
     */
    @Override
    public void timePassed(double dt) {
    }
    /**
     * @param gameLevel - add an object as a sprite
     */
    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
