package arkanoid;
import biuoop.DrawSurface;
import core.Sprite;
import geometry.Rectangle;

public class PutColorInBlock implements BlockDrawer, Sprite {
    // members
    private java.awt.Color color;
    // constructor
    public PutColorInBlock(java.awt.Color color) {
        this.color = color;
    }
    @Override
    public void fillBlock(DrawSurface ds, Rectangle rect) {
        ds.setColor(this.color);
        ds.fillRectangle((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(),
                (int) rect.getWidth(), (int) rect.getHeight());
    }
    @Override
    public void drawOn(DrawSurface d) {
        // TODO Auto-generated method stub
        d.setColor(this.color);
        d.fillRectangle(0, 0, 800, 600);
    }
    @Override
    public void timePassed(double dt) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void addToGame(GameLevel g) {
        // TODO Auto-generated method stub
        g.addSprite(this);
    }
}
