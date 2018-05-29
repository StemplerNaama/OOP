package arkanoid;
import java.awt.Image;

import biuoop.DrawSurface;
import geometry.Rectangle;

public class PutImageInBlock implements BlockDrawer {
    //members
    private Image img;
    // constructor
    public PutImageInBlock(Image img) {
        this.img = img;
    } 
    
 @Override
 public void fillBlock(DrawSurface ds, Rectangle rect) {
     ds.drawImage((int) rect.getUpperLeft().getX(), (int) rect.getUpperLeft().getY(), this.img);
     }

@Override
public void drawOn(DrawSurface d) {
    // TODO Auto-generated method stub
    d.drawImage(0, 0, this.img);
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