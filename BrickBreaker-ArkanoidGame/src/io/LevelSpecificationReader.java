package io;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import arkanoid.Block;
import arkanoid.BlockDrawer;
import arkanoid.ColorsParser;
import arkanoid.PutColorInBlock;
import arkanoid.PutImageInBlock;
import core.Sprite;
import core.Velocity;
import levels.LevelInformation;

/**
 * the LevelSpecificationReader class that read the levels file.
 */
public class LevelSpecificationReader {
     //constructor
    /**
     * constructor.
     */
    public LevelSpecificationReader() {
    }
    /**
     * @param reader - the levels definitions text.
     * @return a list of all the levels that were created.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        BufferedReader br = null;
        try {
            //open the file, with the decoration of bufferedReader
            br =  new BufferedReader(reader);
         // echo each line the user inputs with upper case
            String line = br.readLine();
            while (line != null) {
                if (line.equals("START_LEVEL")) {
                    line = br.readLine();
                    List<String> levelText = new ArrayList<String>();
                    while ((!line.equals("START_BLOCKS")) && (!line.equals(""))) {
                        levelText.add(line);
                        line = br.readLine();
                    }
                    List<String> blocksScetch = new ArrayList<String>();
                    line = br.readLine();
                    while ((!line.equals("END_BLOCKS")) && (!line.equals(""))) {
                        blocksScetch.add(line);
                        line = br.readLine();
                    }
                    CreateLevel levelCreator = buildingLevel(levelText, blocksScetch);
                    levels.add(levelCreator);
                }
                    line = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                    br.close();
                }
                } catch (IOException e) {
                    System.out.println("could not close the stream");
                }
            }
            return levels;
        }
    /**
     * @param levelText - list of definitions.
     * @param blocksScetch - the sketch of the blocks to draw on the screen
     * @return a level.
     * @throws Exception - if failed.
     */
    public CreateLevel buildingLevel(List<String> levelText, List<String> blocksScetch) throws Exception {
        String levelName = null;
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        BlockDrawer background = null;
        int paddleSpeed = 0;
        int paddleWidth = 0;
        InputStreamReader blockDefinitions = null;
        int blocksStartX = -1;
        int blocksStartY = -1;
        int rowHeight = 0;
        int numBlocks = 0;
        Map<String, String> levelStringMap = new HashMap<String, String>();
        int size = levelText.size();
        for (int i = 0; i < size; i++) {
            String[] splitCategory = levelText.get(i).split(":");
            levelStringMap.put(splitCategory[0], splitCategory[1]);
        }
        if (levelStringMap.containsKey("level_name")) {
            levelName = levelStringMap.get("level_name");
        } else {
            System.out.println("level_name doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("ball_velocities")) {
            ballVelocities = createVelocities(levelStringMap.get("ball_velocities"));
        } else {
            System.out.println("ball_velocities doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("background")) {
            background = getLevelBackground(levelStringMap.get("background"));
        } else {
            System.out.println("background doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("paddle_speed")) {
            paddleSpeed = Integer.parseInt(levelStringMap.get("paddle_speed"));
        } else {
            System.out.println("paddle_speed doesn't exist");
            System.exit(0);
        }
        if (levelStringMap.containsKey("paddle_width")) {
            paddleWidth = Integer.parseInt(levelStringMap.get("paddle_width"));
        } else {
            System.out.println("paddle_width doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("block_definitions")) {
            String filePath = levelStringMap.get("block_definitions");
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
            blockDefinitions = (new InputStreamReader(is));
        } else {
            System.out.println("block_definitions doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("blocks_start_x")) {
            blocksStartX = Integer.parseInt(levelStringMap.get("blocks_start_x"));
        } else {
            System.out.println("blocks_start_x doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("blocks_start_y")) {
            blocksStartY = Integer.parseInt(levelStringMap.get("blocks_start_y"));
        } else {
            System.out.println("blocks_start_y doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("row_height")) {
            rowHeight = Integer.parseInt(levelStringMap.get("row_height"));
        } else {
            System.out.println("row_height doesn't exist");
            System.exit(0);
            }
        if (levelStringMap.containsKey("num_blocks")) {
            numBlocks = Integer.parseInt(levelStringMap.get("num_blocks"));
        } else {
            System.out.println("num_blocks doesn't exist");
            System.exit(0);
            }
        List<Block> blocksList = new ArrayList<Block>();
        blocksList = blocksInGame(blocksScetch, blockDefinitions, blocksStartX, blocksStartY, rowHeight);
        BlocksDrawInfo blocksDInfo = new BlocksDrawInfo(blockDefinitions, blocksStartX, blocksStartY,
                rowHeight, numBlocks, blocksList);
        CreateLevel tempLevel = new CreateLevel(levelName, ballVelocities, background, paddleSpeed, paddleWidth,
                blocksDInfo);
        return tempLevel;
    }
    /**
     * @param blocksScetch - the sketch of the blocks to draw on the screen
     * @param blockDefinitions - the block_definions text file
     * @param blocksStartX - the value of x of the first block
     * @param blocksStartY - the value of y of the first block
     * @param rowHeight - the rowHeight
     * @return a list of blocks to draw on the screen
     */
    public List<Block> blocksInGame(List<String> blocksScetch, java.io.Reader blockDefinitions,
            int blocksStartX, int blocksStartY, int rowHeight) {
        int tempStartY = blocksStartY;
        BlocksFromSymbolsFactory b = BlocksDefinitionReader.fromReader(blockDefinitions);
        int size = blocksScetch.size();
        List<Block> blocksList = new ArrayList<Block>();
        for (int i = 0; i < size; i++) {
            int tempStartX = blocksStartX;
            //if there is only one space symbol in line- should skip line
            for (int j = 0; j < blocksScetch.get(i).length(); j++) {
                String symbol = "" + blocksScetch.get(i).charAt(j);
                if (b.isSpaceSymbol(symbol)) {
                    tempStartX += b.getSpaceWidth(symbol);
                } else if (b.isBlockSymbol((symbol))) {
                    Block block = b.getBlock(symbol, tempStartX, tempStartY);
                    blocksList.add(block);
                    tempStartX += block.getWidth();
                }
            }
            tempStartY += rowHeight;
        }
            return blocksList;
    }
    /**
     * @param category - the "ball_velocities" string
     * @return a list of all the balls velocities.
     */
    public List<Velocity> createVelocities(String category) {
        List<Velocity> velocities = new ArrayList<Velocity>();
        String[] splitCategory = category.split(" ");
        int size = splitCategory.length;
        for (int i = 0; i < size; i++) {
            String[] splitValues = splitCategory[i].split(",");
            Velocity newVelocity = Velocity.fromAngleAndSpeed(Integer.parseInt(splitValues[0]),
                    Integer.parseInt(splitValues[1]));
            velocities.add(newVelocity);
        }
        return velocities;
    }
    /**
     * @param category - the "background" string.
     * @return a background
     */
    public BlockDrawer getLevelBackground(String category) {
        int firstOccurencse = category.indexOf('(');
        String categoryOfBackground = category.substring(0, firstOccurencse);
        BlockDrawer background = null;
        switch (categoryOfBackground) {
        case ("color"):
            String fillColor = category.substring(firstOccurencse + 1, category.length() - 1);
            background = new PutColorInBlock(new ColorsParser().colorFromString(fillColor));
            break;
        case ("image"):
            String fillImage = category.substring(firstOccurencse + 1, category.length() - 1);
            Image img = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fillImage);
                img = ImageIO.read(is);
            } catch (IOException e) {
                e.getMessage();
            }
            background = new PutImageInBlock(img);
            break;
            default:
                System.out.println("there isn't background type as written");
                break;
        }
        return background;
    }
    /**
     * class that holds the blocks- to- draw definitions.
     */
    public class BlocksDrawInfo {
        //members
        private java.io.Reader blockDefinitions;
        private int blocksStartX;
        private int blocksStartY;
        private int rowHeight;
        private int numBlocks;
        private List<Block> blocksList;
        //constructor
        /**
         * @param blockDefinitions - blockDefinitions
         * @param blocksStartX - blocksStartX
         * @param blocksStartY - blocksStartY
         * @param rowHeight - rowHeight
         * @param numBlocks - numBlocks
         * @param blocksList - blocksList
         */
        public BlocksDrawInfo(java.io.Reader blockDefinitions, int blocksStartX, int blocksStartY,
                int rowHeight, int numBlocks, List<Block> blocksList) {
            this.blocksStartX = blocksStartX;
            this.blocksStartY = blocksStartY;
            this.rowHeight = rowHeight;
            this.blockDefinitions = blockDefinitions;
            this.numBlocks = numBlocks;
            this.blocksList = blocksList;
        }
    }
    /**
     * class that holds the level- to- create definitions.
     */
    public class CreateLevel implements LevelInformation {
        //members
        private String levelName;
        private List<Velocity> ballVelocities;
        private BlockDrawer background;
        private int paddleSpeed;
        private int paddleWidth;
        private java.io.Reader blockDefinitions;
        private int blocksStartX;
        private int blocksStartY;
        private int rowHeight;
        private int numBlocks;
        private List<Block> blocksList;
        private BlocksDrawInfo blocksDInfo;
        //constructor
        /**
         * @param levelName - levelName
         * @param ballVelocities - ballVelocities
         * @param background - background
         * @param paddleSpeed - paddleSpeed
         * @param paddleWidth - paddleWidth
         * @param blocksDInfo - blocksDInfo
         */
        public CreateLevel(String levelName, List<Velocity> ballVelocities, BlockDrawer background, int paddleSpeed,
                int paddleWidth, BlocksDrawInfo blocksDInfo) {
            this.levelName = levelName;
            this.ballVelocities = ballVelocities;
            this.background = background;
            this.paddleSpeed = paddleSpeed;
            this.paddleWidth = paddleWidth;
            this.blocksStartX = blocksDInfo.blocksStartX;
            this.blocksStartY = blocksDInfo.blocksStartY;
            this.rowHeight = blocksDInfo.rowHeight;
            this.blockDefinitions = blocksDInfo.blockDefinitions;
            this.numBlocks = blocksDInfo.numBlocks;
            this.blocksList = blocksDInfo.blocksList;
        }
        /**
         * @return the text file of blocks_def
         */
        public java.io.Reader getBlockDef() {
            return this.blockDefinitions;
        }
        /**
         * @return - the number of the level's balls.
         */
        @Override
        public int numberOfBalls() {
            return this.initialBallVelocities().size();
        }
        /**
         * The initial velocity of each ball.
         * Note that initialBallVelocities().size() == numberOfBalls()
         * @return - the list of the velocities.
         */
        @Override
        public List<Velocity> initialBallVelocities() {
            return this.ballVelocities;
        }
        /**
         * @return - the paddleSpeed.
         */
        @Override
        public int paddleSpeed() {
            return this.paddleSpeed;
        }
        /**
         * @return - the paddleWidth.
         */
        @Override
        public int paddleWidth() {
            return this.paddleWidth;
        }
        /**
         * the level name will be displayed at the top of the screen.
         * @return - the levelName.
         */
        @Override
        public String levelName() {
            return this.levelName;
        }
        /**
         * Returns a sprite with the background of the level
         * the level name will be displayed at the top of the screen.
         * @return - the getBackground.
         */
        @Override
        public Sprite getBackground() {
            return this.background;
        }
        /**
         * The Blocks that make up this level, each block contains
         * its size, color and location.
         * @return - the list of the blocks.
         */
        @Override
        public List<Block> blocks() {
            return this.blocksList;
        }
        /**
         * @return - Number of levels that should be removed before the level is considered to be "cleared".
         * This number should be <= blocks.size();
         */
        @Override
        public int numberOfBlocksToRemove() {
            return this.numBlocks;
        }
}
}
