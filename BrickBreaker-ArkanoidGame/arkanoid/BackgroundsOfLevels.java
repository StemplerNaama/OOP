package arkanoid;

import biuoop.DrawSurface;
import core.Sprite;
import java.awt.Color;

/**
 * the class that is in charge of the background of the levels.
 */
public class BackgroundsOfLevels implements Sprite {
    // members
    private int numberOfLevel;
    // constructor
    /**
     * @param numberOfLevel - the number of lives that remains
     */
    public BackgroundsOfLevels(int numberOfLevel) {
        this.numberOfLevel = numberOfLevel;
    }
    /**
     * draw the background of each level- according to the number of a level.
     * @param d
     *            - the surface to draw on it.
     */
    public void drawOn(DrawSurface d) {
        switch (this.numberOfLevel) {
        case 1:
            this.drawLevelOne(d);
            break;
        case 2:
            this.drawLevelTwo(d);
            break;
        case 3:
            this.drawLevelThree(d);
            break;
        case 4:
            this.drawLevelFour(d);
            break;
        default:
            break;
        }
    }

    /**
     * this is the background of the first level.
     * @param d - the drawsurface to draw on it
     */
    public void drawLevelOne(DrawSurface d) {
        // background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);
        // target
        d.setColor(Color.BLUE);
        d.drawLine(400, 55, 400, 290);
        d.drawLine(280, 170, 520, 170);
        d.drawCircle(400, 170, 50);
        d.drawCircle(400, 170, 80);
        d.drawCircle(400, 170, 110);
    }

    /**
     * this is the background of the second level.
     * @param d - the drawsurface to draw on it.
     */
    public void drawLevelTwo(DrawSurface d) {
        // background
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);

        // lines
        d.setColor(new Color(255, 250, 0));
        for (int i = 0; i < 100; i++) {
            d.drawLine(180, 120, 40 + i * 7, 200);
        }

        // sun
        d.setColor(new Color(230, 230, 0));
        d.fillCircle(180, 120, 45);
        d.setColor(new Color(240, 240, 0));
        d.fillCircle(180, 120, 40);
        d.setColor(new Color(255, 255, 0));
        d.fillCircle(180, 120, 35);
    }

    /**
     * this is the background of the third level.
     * @param d - the drawsurface to draw on it
     */
    public void drawLevelThree(DrawSurface d) {
        // background
        d.setColor(new Color(42, 130, 21));
        d.fillRectangle(0, 0, 800, 600);

        // building
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(150, 260, 12, 200);
        d.fillRectangle(142, 420, 30, 50);
        d.setColor(Color.BLACK);
        d.fillRectangle(100, 460, 110, 200);
        d.setColor(Color.WHITE);
        for (int xVal = 110; xVal <= 190; xVal += 20) {
            for (int yVal = 470; yVal <= 590; yVal += 30) {
                d.fillRectangle(xVal, yVal, 10, 20);
            }
        }
        d.setColor(Color.ORANGE);
        d.fillCircle(156, 250, 15);
        d.setColor(Color.RED);
        d.fillCircle(156, 250, 10);
        d.setColor(Color.YELLOW);
        d.fillCircle(156, 250, 5);
    }

    /**
     * this is the background of the fourth level.
     * @param d - the drawsurface to draw on it
     */
    private void drawLevelFour(DrawSurface d) {
        // background
        d.setColor(new Color(30, 144, 255));
        d.fillRectangle(0, 0, 800, 600);

        // lines 1
        d.setColor(new Color(255, 250, 250));
        for (int i = 0; i < 10; i++) {
            d.drawLine(65 + i * 7, 450, 40 + i * 7, 600);
        }

        // cloud 1
        d.setColor(new Color(190, 190, 190));
        d.fillCircle(60, 440, 22);
        d.fillCircle(80, 455, 22);
        d.setColor(new Color(180, 180, 180));
        d.fillCircle(90, 430, 30);
        d.setColor(new Color(170, 170, 170));
        d.fillCircle(120, 440, 25);
        d.fillCircle(110, 450, 20);

        // lines 2
        d.setColor(new Color(255, 250, 250));
        for (int i = 0; i < 10; i++) {
            d.drawLine(660 + i * 8, 350, 670 + i * 8, 600);
        }

        // cloud 2
        d.setColor(new Color(190, 190, 190));
        d.fillCircle(650, 330, 32);
        d.fillCircle(680, 355, 32);
        d.setColor(new Color(180, 180, 180));
        d.fillCircle(690, 330, 40);
        d.setColor(new Color(170, 170, 170));
        d.fillCircle(725, 340, 35);
        d.fillCircle(705, 350, 30);
    }

    /**
     * notify the sprite that time has passed
     * notify objects to move.
     */
    public void timePassed(double dt) {
    }

    /**
     * add an object as a sprite.
     * notify objects to move.
     * @param game - the game to add the sprite to.
     */
    @Override
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }
}
