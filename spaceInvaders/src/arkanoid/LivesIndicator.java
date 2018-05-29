package arkanoid;
import biuoop.DrawSurface;
import core.Counter;
import core.Sprite;

/**
 * the livesIndicator class is in charge of adding the number of lives as a sprite and showing it.
 */
public class LivesIndicator implements Sprite {
    //members
    private Counter livesCounter;
    //constructor
    /**
     * @param livesCounter -this is the lives number.
     */
    public LivesIndicator(Counter livesCounter) {
        this.livesCounter = livesCounter;
    }
    /**
     * draw the sprite to the screen.
     * @param surface - the surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(java.awt.Color.white);
        surface.fillRectangle(0, 0, 350, 25);
        // will write the number of hits in white font
        surface.setColor(java.awt.Color.black);
        surface.drawText(100, 20, "Lives: " + this.livesCounter.getValue() + "", 15);
    }
    /**
     * notify objects to move.
     * @param dt - is specifies the amount of seconds passed since the last call
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