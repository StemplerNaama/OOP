package io;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
import geometry.Point;
import geometry.Rectangle;

/**
 * this is the blocks definition reader class.
 */
public class BlocksDefinitionReader {
    /**
     * constructor.
     */
    public BlocksDefinitionReader() {
    }
    /**
     * @param reader - the text of block_definitions
     * @return a BlocksFromSymbolsFactory object- containing the sdef and bdef maps.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, BlockCreator> symbolBlockCreators = new HashMap<String, BlockCreator>();
        Map<String, String> blockCreatorsDefault = new HashMap<String, String>();
        Map<String, String> blockCreatorsSdef = new HashMap<String, String>();

     // read lines
        BufferedReader br = null;
        try {
            //open the file, with the decoration of bufferedReader
            br =  new BufferedReader(reader);
         // echo each line the user inputs with upper case
            String line = br.readLine();

            while (line != null) {

                int i;
                if (!line.startsWith("#")) {
                    String[] lineSplit = line.split(" ");
                    switch (lineSplit[0]) {
                    case "default":
                        for (i = 1; i < lineSplit.length; i++) {
                            String[] indexSplit = lineSplit[i].split(":");
                            blockCreatorsDefault.put(indexSplit[0], indexSplit[1]);
                        }
                        break;
                    case "bdef":
                        Map<String, String> blockCreatorsBdef = new HashMap<String, String>();

                        for (i = 2; i < lineSplit.length; i++) {
                            String[] indexSplit = lineSplit[i].split(":");
                            blockCreatorsBdef.put(indexSplit[0], indexSplit[1]);
                        }
                        String[] symbolSplit = lineSplit[1].split(":");
                        CreateBlock tempBlockCreator = buildingBlock(blockCreatorsBdef, blockCreatorsDefault);
                        symbolBlockCreators.put(symbolSplit[1], tempBlockCreator);
                        //BlockCreator
                        break;
                    case "sdef":
                        String[] symbol = lineSplit[1].split(":");
                        String[] width = lineSplit[2].split(":");
                        blockCreatorsSdef.put(symbol[1], width[1]);
                        break;
                    default:
                        break;
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("something happened while reading the stream");
        } finally {
            try {
                if (br != null) {
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the stream");
            }
        }
        BlocksFromSymbolsFactory blocksTextMaps = new BlocksFromSymbolsFactory(symbolBlockCreators, blockCreatorsSdef);
        return blocksTextMaps;
    }
    /**
     * @param bdef the map of blocks definitions
     * @param defaults - the map of the defaults definitions
     * @return a CreateBlock with definition o blocks.
     */
    public static CreateBlock buildingBlock(Map<String, String> bdef, Map<String, String> defaults) {
        int height = -1;
        int width = -1;
        int hitPoints = -1;
        BlockDrawer fill = null;
        List<BlockDrawer> fillK = new ArrayList<BlockDrawer>();
        java.awt.Color stroke = null;
        if (bdef.containsKey("height")) {
            height = Integer.parseInt(bdef.get("height"));
        } else if (defaults.containsKey("height")) {
            height = Integer.parseInt(defaults.get("height"));
        } else {
            System.out.println("height doesn't exist");
            System.exit(0);
        }
        if (bdef.containsKey("width")) {
            width = Integer.parseInt(bdef.get("width"));
        } else if (defaults.containsKey("width")) {
            width = Integer.parseInt(defaults.get("width"));
        } else {
            System.out.println("width doesn't exist");
            System.exit(0);
        }
        if (bdef.containsKey("hit_points")) {
            hitPoints = Integer.parseInt(bdef.get("hit_points"));
        } else if (defaults.containsKey("hit_points")) {
            hitPoints = Integer.parseInt(defaults.get("hit_points"));
        } else {
            System.out.println("hit_points doesn't exist");
            System.exit(0);
        }
        if (bdef.containsKey("fill")) {
            fill = fillExists(bdef, fill, "fill");
            // if doesnt exist in bdef map - get info from the default map.
            } else if (defaults.containsKey("fill")) {
                fill = fillExists(defaults, fill, "fill");
            }
        for (int i = 0; i < hitPoints; i++) {
            fillK.add(i, fill);
        }
        fillK = fillKExists(bdef, fillK);
        fillK = fillKExists(defaults, fillK);
        // converting fillK list to fillK map
        Map<Integer, BlockDrawer> fillKMap = new HashMap<Integer, BlockDrawer>();
        for (int i = 0; i < fillK.size(); i++) {
            fillKMap.put(i + 1, fillK.get(i));
        }
        if (bdef.containsKey("stroke")) {
            stroke = strokeExists(bdef, stroke);
        } else if (defaults.containsKey("stroke")) {
            stroke = strokeExists(defaults, stroke);
        }
        CreateBlock tempBlock = new CreateBlock(height, width, hitPoints, fill, fillKMap, stroke);
        return tempBlock;
    }
    /**
     * @param map - the map with the block info
     * @param fill - to update the fill type
     * @param category - searching if the string "fill"- exists in the map
     * @return the fill type
     */
    public static BlockDrawer fillExists(Map<String, String> map, BlockDrawer fill, String category) {
        int firstOccurencse = (map.get(category)).indexOf('(');
        String categoryOfFill = (map.get(category)).substring(0, firstOccurencse);
        switch (categoryOfFill) {
        case ("color"):
            String fillColor = (map.get(category)).substring(firstOccurencse + 1, (map.get(category)).length() - 1);
        fill = new PutColorInBlock(new ColorsParser().colorFromString(fillColor));
            break;
        case ("image"):
            String fillImage = (map.get(category)).substring(firstOccurencse + 1, (map.get(category)).length() - 1);
            Image img = null;
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fillImage);
                img = ImageIO.read(is);
            } catch (IOException e) {
                e.getMessage();
            }
            fill = new PutImageInBlock(img);
            break;
            default:
                System.out.println("there isn't fill type as written");
                break;
        }
        return fill;
    }
    /**
     * @param map - the map with the block info
     * @param stroke - stroke of block
     * @return the stroke if exists
     */
    public static java.awt.Color strokeExists(Map<String, String> map, java.awt.Color stroke) {
        int firstOccurencse = (map.get("stroke")).indexOf('(');
        String drawStroke = (map.get("stroke")).substring(firstOccurencse + 1, (map.get("stroke")).length() - 1);
        stroke = new ColorsParser().colorFromString(drawStroke);
        return stroke;
    }
    /**
     * @param map - the map with the block info
     * @param fillK - the fills list.
     * @return the list with the fills
     */
    public static List<BlockDrawer> fillKExists(Map<String, String> map, List<BlockDrawer> fillK) {
        for (int i = 0; i < fillK.size(); i++) {
            String tempFill = "fill-" + Integer.toString(i + 1);
            if (map.containsKey(tempFill)) {
                BlockDrawer fill = null;
                fill = fillExists(map, fill, tempFill);
                fillK.set(i, fill);
            }
        }
        return fillK;
    }
    /**
     * the CreateBlock class to convert the definitions to create block.
     */
    public static class CreateBlock implements BlockCreator {
        //members
        private int height;
        private int width;
        private int hitPoints;
        private BlockDrawer fill;
        private Map<Integer, BlockDrawer> fillKMap;
        private java.awt.Color stroke;
        //constructor
        /**
         * @param height - height of block
         * @param width - width of block
         * @param hitPoints - hitPoints of block
         * @param fill - fill of block
         * @param fillKMap - the different fills for each hit points
         * @param stroke - the stroke of block
         */
        public CreateBlock(int height, int width, int hitPoints, BlockDrawer fill, Map<Integer,
                BlockDrawer> fillKMap, java.awt.Color stroke) {
            this.height = height;
            this.width = width;
            this.hitPoints = hitPoints;
            this.fill = fill;
            this.fillKMap = fillKMap;
            this.stroke = stroke;
        }
        /**
      * @param xpos - the x coordinate of drawing the block
      * @param ypos - the y coordinate of drawing the block
      * @return created a block at the specified location.
      */
        @Override
        public Block create(int xpos, int ypos) {
            Rectangle rect = new Rectangle(new Point(xpos, ypos), this.width, this.height);
            Block block = new Block(rect, this.fill, this.fillKMap, this.stroke, this.hitPoints);
            return block;
        }
    }
}