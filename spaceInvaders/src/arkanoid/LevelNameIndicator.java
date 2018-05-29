package arkanoid;
import biuoop.DrawSurface;
import core.Sprite;

/**
 * the levelNameIndicator class is in charge of adding the level name as a sprite and showing it.
 */
public class LevelNameIndicator implements Sprite {
    //members
    private String levelName;
    //constructor
    /**
     * @param levelName -this is the level name.
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }
    /**
     * draw the sprite to the screen.
     * @param surface - the surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(java.awt.Color.white);
        surface.fillRectangle(550, 0, 250, 25);
        // will write the number of hits in white font
        surface.setColor(java.awt.Color.black);
        surface.drawText(550, 20, "Level Name: Battle no." + this.levelName + "", 15);
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