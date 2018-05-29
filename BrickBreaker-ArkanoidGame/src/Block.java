import biuoop.DrawSurface;

/**
 * a Block class.
 */
public class Block implements Collidable, Sprite {
    // members
    private Rectangle rec;
    private int numOfHits;
    private java.awt.Color color;
    // constructors
    /**
     * @param rec - the rectangle that we want to implement as a block.
     */
    public Block(Rectangle rec) {
        this.rec = rec;
        this.numOfHits = 0;
        this.color = java.awt.Color.gray;
    }
    /**
     * @param rec - the rectangle that we want to implement as a block.
     * @param color - the color of ball
     */
    public Block(Rectangle rec, java.awt.Color color) {
        this.rec = rec;
        this.numOfHits = 0;
        this.color = color;
    }
    /**
     * @param game - the game that has the sprites and collidables
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
    /**
     * decrease num of hits in block.
     */
    public void decreaseNumOfHits() {
        this.numOfHits = this.numOfHits - 1;
    }
    /**
     * set the num of hits in block.
     * @param number - the update num of hits
     */
    public void setNumOfHits(int number) {
        this.numOfHits = number;
    }
    /**
     * drawing the outline of the blocks.
     * @param surface - the surface to draw the rectangle on
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
        surface.setColor(java.awt.Color.black);
        surface.drawRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
        surface.setColor(java.awt.Color.black);
        this.drawNumOfHits(surface);
    }
    /**
     * draw the background.
     * @param surface - the surface to draw the background on
     */
    public void drawBackground(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
    }
    /**
     * draw the num of hits on the middle of the block.
     * @param surface - the surface that the bricks are on.
     */
    public void drawNumOfHits(DrawSurface surface) {
        double xMiddle = this.getCollisionRectangle().getUpperLine().middle().getX();
        double yMiddle = this.getCollisionRectangle().getLeftLine().middle().getY();
        // will write the number of hits in white font
        surface.setColor(java.awt.Color.white);
        // x will be shown on a brick when it turns to 0
        if (this.numOfHits <= 0) {
            surface.drawText((int) xMiddle - 3, (int) yMiddle + 7, "X", 15);
        } else {
            surface.drawText((int) xMiddle - 3, (int) yMiddle + 7, "" + this.numOfHits + "", 15);
        }
    }
    /* (non-Javadoc)
     * @see Sprite#timePassed()
     */
    @Override
    public void timePassed() {
    }
    /* (non-Javadoc)
     * @see Collidable#getCollisionRectangle()
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }
    /* (non-Javadoc)
     * @see Collidable#hit(Point, Velocity)
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        /*
         * if the collision point is equal to one of the limits of the rectangle- change the direction.
         */
        //if equal to the up border change the dY value.
        if (collisionPoint.getY() == this.getCollisionRectangle().getUpperLine().start().getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), -1 * Math.abs(currentVelocity.getDy()));
        }
        // if equal to the up border change the dY value.
        if (collisionPoint.getY() == this.getCollisionRectangle().getDownLine().start().getY()) {
            currentVelocity = new Velocity(currentVelocity.getDx(), Math.abs(currentVelocity.getDy()));
        }
        // if equal to the up border change the dX value.
        if (collisionPoint.getX() == this.getCollisionRectangle().getRightLine().start().getX()) {
            currentVelocity = new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        // if equal to the up border change the dX value.
        if (collisionPoint.getX() == this.getCollisionRectangle().getLeftLine().start().getX()) {
            currentVelocity = new Velocity(-1 * Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        // decrease the num of hits
        this.decreaseNumOfHits();
        // return the current velocity
        return currentVelocity;
    }
}
