package io;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import arkanoid.Block;
import arkanoid.BlockCreator;
import arkanoid.BlockDrawer;
import arkanoid.ColorsParser;
import arkanoid.PutColorInBlock;
import arkanoid.PutImageInBlock;
import biuoop.DrawSurface;
import core.Sprite;
import core.Velocity;
import geometry.Point;
import geometry.Rectangle;
import io.BlocksDefinitionReader.CreateBlock;
import levels.LevelInformation;

public class LevelSpecificationReader {
    // members
     //constructor
    public LevelSpecificationReader() {
    }
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
                    //CreateLevel levelCreator = buildingLevel(levelText);
                    List<String> blocksScetch = new ArrayList<String>();
                    line = br.readLine();
                    while ((!line.equals("END_BLOCKS")) && (!line.equals(""))) {
                    //BlocksFromSymbolsFactory b = BlocksDefinitionReader.fromReader(levelCreator.getBlockDef());
                        blocksScetch.add(line);
                        line = br.readLine();
                    }
                    CreateLevel levelCreator = buildingLevel(levelText, blocksScetch);
                    levels.add(levelCreator); 
                }
                    line = br.readLine();
                } 
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if(br != null){
                    br.close();
                }
                } catch (IOException e) {
                    System.out.println("could not close the stream");
                }
            }
            return levels; 
        }
    
    public CreateLevel buildingLevel(List<String> levelText, List<String> blocksScetch) throws Exception {
        String level_name = null;
        List<Velocity> ball_velocities = new ArrayList<Velocity>();
        BlockDrawer background = null;
        int paddle_speed = 0;
        int paddle_width = 0;
        java.io.Reader block_definitions = null;
        int blocks_start_x = -1;
        int blocks_start_y = -1;
        int row_height = 0;
        int num_blocks = 0;
        Map<String, String> levelStringMap = new HashMap<String, String>();
        int size = levelText.size();
        for (int i = 0; i < size; i++) {
            String[] splitCategory = levelText.get(i).split(":");
            levelStringMap.put(splitCategory[0], splitCategory[1]);
        }
        if (levelStringMap.containsKey("level_name")) {
            level_name = levelStringMap.get("level_name");
        } else {
            throw new Exception("level_name doesn't exist");
            }
        if (levelStringMap.containsKey("ball_velocities")) {
            ball_velocities = CreateVelocities(levelStringMap.get("ball_velocities"));
        } else {
            throw new Exception("ball_velocities doesn't exist");
        }
        if (levelStringMap.containsKey("background")) {
            background = getLevelBackground(levelStringMap.get("background"));
        } else {
            throw new Exception("background doesn't exist");
        }
        if (levelStringMap.containsKey("paddle_speed")) {
            paddle_speed = Integer.parseInt(levelStringMap.get("paddle_speed"));
        } else {
            throw new Exception("paddle_speed doesn't exist");
        }
        if (levelStringMap.containsKey("paddle_width")) {
            paddle_width = Integer.parseInt(levelStringMap.get("paddle_width"));
        } else {
            throw new Exception("paddle_width doesn't exist");
        }
        if (levelStringMap.containsKey("block_definitions")) {
            String filePath = levelStringMap.get("block_definitions");
            block_definitions = new FileReader(filePath);
        } else {
            throw new Exception("block_definitions doesn't exist");
        }
        if (levelStringMap.containsKey("blocks_start_x")) {
            blocks_start_x = Integer.parseInt(levelStringMap.get("blocks_start_x"));
        } else {
            throw new Exception("blocks_start_x doesn't exist");
        }
        if (levelStringMap.containsKey("blocks_start_y")) {
            blocks_start_y = Integer.parseInt(levelStringMap.get("blocks_start_y"));
        } else {
            throw new Exception("blocks_start_y doesn't exist");
        }
        if (levelStringMap.containsKey("row_height")) {
            row_height = Integer.parseInt(levelStringMap.get("row_height"));
        } else {
            throw new Exception("row_height doesn't exist");
        }
        if (levelStringMap.containsKey("num_blocks")) {
            num_blocks = Integer.parseInt(levelStringMap.get("num_blocks"));
        } else {
            throw new Exception("num_blocks doesn't exist");
        }
        List<Block> blocksList = new ArrayList<Block>();
        blocksList = blocksInGame(blocksScetch, block_definitions, blocks_start_x, blocks_start_y, row_height);
        CreateLevel tempLevel = new CreateLevel(level_name, ball_velocities, background, paddle_speed, paddle_width, block_definitions, blocks_start_x, blocks_start_y, row_height, num_blocks, blocksList);
        return tempLevel;
    }
    
    public List<Block> blocksInGame(List<String> blocksScetch, java.io.Reader block_definitions, int blocks_start_x, int blocks_start_y, int row_height) {
        int temp_start_y = blocks_start_y;
        BlocksFromSymbolsFactory b = BlocksDefinitionReader.fromReader(block_definitions);
        int size = blocksScetch.size();
        List<Block> blocksList = new ArrayList<Block>();
        for (int i = 0; i < size; i++) {
            int temp_start_x = blocks_start_x;
            //if there is only one space symbol in line- should skip line
            for (int j = 0; j < blocksScetch.get(i).length(); j++) {
                String symbol = "" + blocksScetch.get(i).charAt(j);
                if (b.isSpaceSymbol(symbol)) {
                    temp_start_x += b.getSpaceWidth(symbol);
                } else if (b.isBlockSymbol((symbol))) {
                    Block block = b.getBlock(symbol, temp_start_x, temp_start_y);
                    blocksList.add(block);
                    temp_start_x += block.getWidth();
                }
            }
            temp_start_y += row_height;
        }
            return blocksList;
    }
        

    public List<Velocity> CreateVelocities(String category) {
        List<Velocity> velocities = new ArrayList<Velocity>();
        String[] splitCategory = category.split(" ");
        int size = splitCategory.length;
        for (int i = 0; i < size; i++) {
            String[] splitValues = splitCategory[i].split(",");
            Velocity newVelocity = Velocity.fromAngleAndSpeed(Integer.parseInt(splitValues[0]), Integer.parseInt(splitValues[1]));
            velocities.add(newVelocity);
        }
        return velocities;
    }
    public BlockDrawer getLevelBackground(String category) {
        int firstOccurencse = category.indexOf( '(' );
        String categoryOfBackground = category.substring(0, firstOccurencse);
        BlockDrawer background = null;
        switch (categoryOfBackground) {
        case ("color"):
            String fillColor = category.substring(firstOccurencse + 1, category.length() -1);
            background = new PutColorInBlock(new ColorsParser().colorFromString(fillColor));
            break;
        case ("image"):
            String fillImage = category.substring(firstOccurencse + 1, category.length() -1);
            Image img = null;
            try {
                img = ImageIO.read(new File(fillImage));
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
    
    public class CreateLevel implements LevelInformation {
        //members
        private String level_name;
        private List<Velocity> ball_velocities;
        private BlockDrawer background;
        private int paddle_speed;
        private int paddle_width;
        private java.io.Reader block_definitions;
        private int blocks_start_x;
        private int blocks_start_y;
        private int row_height;
        private int num_blocks;
        private List<Block> blocksList;
        //constructor
        public CreateLevel(String level_name, List<Velocity> ball_velocities, BlockDrawer background, int paddle_speed,
                int paddle_width, java.io.Reader block_definitions, int blocks_start_x, int blocks_start_y,
                int row_height, int num_blocks, List<Block> blocksList) {
            this.level_name = level_name;
            this.ball_velocities = ball_velocities;
            this.background = background;
            this.paddle_speed = paddle_speed;
            this.paddle_width = paddle_width;
            this.block_definitions = block_definitions;
            this.blocks_start_x = blocks_start_x;
            this.blocks_start_y = blocks_start_y;
            this.row_height = row_height;
            this.num_blocks = num_blocks;
            this.blocksList = blocksList;
        }
        public java.io.Reader getBlockDef() {
            return this.block_definitions;
        }
        /**
         * @return - the number of the level's balls.
         */
        @Override
        public int numberOfBalls() {
            return this.initialBallVelocities().size();
        }
           // The initial velocity of each ball
           // Note that initialBallVelocities().size() == numberOfBalls()
        /**
         * The initial velocity of each ball.
         * Note that initialBallVelocities().size() == numberOfBalls()
         * @return - the list of the velocities.
         */
        @Override
        public List<Velocity> initialBallVelocities() {
            return this.ball_velocities;
        }
        /**
         * @return - the paddleSpeed.
         */
        @Override
        public int paddleSpeed() {
            return this.paddle_speed;
        }
        /**
         * @return - the paddleWidth.
         */
        @Override
        public int paddleWidth() {
            return this.paddle_width;
        }
        /**
         * the level name will be displayed at the top of the screen.
         * @return - the levelName.
         */
        @Override
        public String levelName() {
            return this.level_name;
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
            return this.num_blocks;
        }
}
    public static void main(String[] args) {
        try {
            LevelSpecificationReader levels = new LevelSpecificationReader();
            List<LevelInformation> levelsList = levels.fromReader(new FileReader("/Users/Naama/workspace/ass3e/levels.txt"));
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
    }

