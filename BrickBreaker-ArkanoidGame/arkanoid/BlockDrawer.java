package arkanoid;


import biuoop.DrawSurface;
import core.Sprite;
import geometry.Rectangle;

public interface BlockDrawer extends Sprite
  {
    void fillBlock(DrawSurface ds, Rectangle rect);
  }