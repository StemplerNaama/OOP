package arkanoid;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;

public class Level {
    private int numOfLevel;
    private Velocity ballVelocity;
    private biuoop.KeyboardSensor ks;
        // constructor
        /**
         * -aba.
         */
        public Level(int numOfLevel, biuoop.KeyboardSensor ks) {
            this.numOfLevel = numOfLevel;
            this.ballVelocity = Velocity.fromAngleAndSpeed(90, 8);
            this.ks = ks;
            //this.paddleSpeed = 600;
            //this.paddleWidth = 80;
        }

        public Velocity getVelocity() {
            return this.ballVelocity;
        }

        public int paddleSpeed() {
            return this.getPaddle().getSpeed();
        }

        public int paddleWidth() {
            return this.getPaddle().getWidth();
        }
        public Paddle getPaddle() {
            Point cornerPaddle = new Point(400 - 40, 600 - 50);
            Rectangle paddle = new Rectangle(cornerPaddle, 80, 20);
            return new Paddle(ks, paddle, java.awt.Color.yellow, 800, 600, 600);
        }
        
        public String levelName() {
            return Integer.toString(this.numOfLevel);
        }
        // Returns a sprite with the background of the level
        public Sprite getBackground() {
            PutColorInBlock background = new PutColorInBlock(java.awt.Color.black);
            return background;
        }
        // The Blocks that make up this level, each block contains
        // its size, color and location.
        
        public List<Block> createAliensList() {
            // the list that will hold all the alien blocks
            List<Block> aliens = new ArrayList<Block>();
            Image img = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("image/enemy.png");
                img = ImageIO.read(is);
            } catch (IOException e) {
                e.getMessage();
            }
            PutImageInBlock image = new PutImageInBlock(img);
            int yStartVal = 60;
            int width = 40;
            int height = 30;
            int xStartVal = 60;
            // creating the aliens blocks
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    Rectangle alienBrick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
                    Block newAlienBrick = new Block(alienBrick, image, null);
                    aliens.add(newAlienBrick);
                    xStartVal = xStartVal + width + 10;
                }
                yStartVal = yStartVal + height + 10;
                xStartVal = 60;
            }
            return aliens;
        }
        public List<Block> createShieldsList() {
            // the list that will hold all the shield blocks
            List<Block> shields = new ArrayList<Block>();
            java.awt.Color color = java.awt.Color.cyan;
            int xStartVal = 100;
            int yStartVal = 500;
            int width = 5;
            int height = 5;
            int i;
            int j;     
            int tempX;
                // create first part of the shields
            	for (i = 0; i < 3; i++) {
            		xStartVal = 100;
            		for (j = 0; j < 30; j++) {
            			Rectangle shieldBrick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
                        Block newshieldBrick = new Block(shieldBrick, new PutColorInBlock(color), null);
                        shields.add(newshieldBrick);
                        xStartVal += width;
                        System.out.println(j);
                        }
                    yStartVal += height;
                    }
            	xStartVal += 100;
            	yStartVal = 500;
            	tempX = xStartVal;
        // create second part of the shields
    	for (i = 0; i < 3; i++) {
    		for (j = 0; j < 30; j++) {
    			Rectangle shieldBrick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
                Block newshieldBrick = new Block(shieldBrick, new PutColorInBlock(color), null);
                shields.add(newshieldBrick);
                xStartVal += width;
                System.out.println(j);
            }
    		xStartVal = tempX;
        yStartVal += height;
        }
	xStartVal += 100;
	yStartVal = 500;
	tempX = xStartVal;
    // create third part of the shields
	for (i = 0; i < 3; i++) {
		for (j = 0; j < 30; j++) {
			Rectangle shieldBrick = new Rectangle(new Point(xStartVal, yStartVal), width, height);
            Block newshieldBrick = new Block(shieldBrick, new PutColorInBlock(color), null);
            shields.add(newshieldBrick);
            xStartVal += width;
            System.out.println(j);
        }
		xStartVal = tempX;
    yStartVal += height;
    }
	return shields;
        }


        

        

        // Number of levels that should be removed
        // before the level is considered to be "cleared".
        // This number should be <= blocks.size();
        public int numberOfBlocks() {
            return 320;
        }
        public int numberOfAliensToRemove() {
            return 50;
        }
        public int numberOfShields() {
            return 270;
        }
    }

