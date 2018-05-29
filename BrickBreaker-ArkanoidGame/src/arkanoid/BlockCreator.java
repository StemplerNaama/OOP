package arkanoid;
/**
 * this is the block creator interface.
 */
public interface BlockCreator {
   // Create a block at the specified location.
   /**
 * @param xpos - the x coordinate of drawing the block
 * @param ypos - the y coordinate of drawing the block
 * @return a block
 */
Block create(int xpos, int ypos);
}