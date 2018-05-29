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
import geometry.Point;
import geometry.Rectangle;

public class BlocksDefinitionReader {
    // members
    
    // constructor
    public BlocksDefinitionReader() {
    }
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        /*if (! reader.ready()) {
            return new HighScoresTable(5);
        }*/
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

            while(line != null) {

                int i;
                if (!line.startsWith("#")) {
                    String[] lineSplit = line.split(" ");  
                    switch (lineSplit[0]) {
                    case "default":
                        for(i = 1; i < lineSplit.length; i++) {
                            String[] indexSplit = lineSplit[i].split(":");
                            blockCreatorsDefault.put(indexSplit[0], indexSplit[1]);
                        }
                        break;
                    case "bdef":
                        Map<String, String> blockCreatorsBdef = new HashMap<String, String>();

                        for(i = 2; i < lineSplit.length; i++) {
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
            // TODO: handle exception
            System.out.println("something happened while reading the stream");
        } finally {
            try {
                if(br != null){
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the stream");
            }
        }
        BlocksFromSymbolsFactory blocksTextMaps = new BlocksFromSymbolsFactory(symbolBlockCreators, blockCreatorsSdef);
        return blocksTextMaps;
        
    }
    
    public static CreateBlock buildingBlock (Map<String, String> bdef, Map<String, String> defaults) {
        int height = -1;
        int width = -1;
        int hit_points = -1;
        BlockDrawer fill = null;
        List<BlockDrawer> fillK = new ArrayList<BlockDrawer>();
        java.awt.Color stroke = null;
        if (bdef.containsKey("height")) {
            height = Integer.parseInt(bdef.get("height"));
        } else if (defaults.containsKey("height")) {
            height = Integer.parseInt(defaults.get("height"));
        } // לבדוק מה קורה אם גם אין בדיפולט
        if (bdef.containsKey("width")) {
            width = Integer.parseInt(bdef.get("width"));
        } else if (defaults.containsKey("width")) {
            width = Integer.parseInt(defaults.get("width"));
        }
        if (bdef.containsKey("hit_points")) {
            hit_points = Integer.parseInt(bdef.get("hit_points"));
        } else if (defaults.containsKey("hit_points")) {
            hit_points = Integer.parseInt(defaults.get("hit_points"));
        }
        if (bdef.containsKey("fill")) {
            fill = fillExists(bdef, fill, "fill");
            // if doesnt exist in bdef map - get info from the default map.
            } else if (defaults.containsKey("fill")) {
                fill = fillExists(defaults, fill, "fill");
            }
        for (int i = 0; i < hit_points; i++) {
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
        CreateBlock tempBlock = new CreateBlock(height, width, hit_points, fill, fillKMap, stroke);
        return tempBlock;
    }
    
    public static BlockDrawer fillExists (Map<String, String> map, BlockDrawer fill, String category) {
        int firstOccurencse = (map.get(category)).indexOf( '(' );
        String categoryOfFill = (map.get(category)).substring(0, firstOccurencse);
        switch (categoryOfFill) {
        case ("color"):
            String fillColor = (map.get(category)).substring(firstOccurencse + 1, (map.get(category)).length() -1);
        fill = new PutColorInBlock(new ColorsParser().colorFromString(fillColor));
            break;
        case ("image"):
            String fillImage = (map.get(category)).substring(firstOccurencse + 1, (map.get(category)).length() -1);
            Image img = null;
            try {
                img = ImageIO.read(new File(fillImage));
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
    public static java.awt.Color strokeExists (Map<String, String> map, java.awt.Color stroke) {
        int firstOccurencse = (map.get("stroke")).indexOf('(');
        String drawStroke = (map.get("stroke")).substring(firstOccurencse + 1, (map.get("stroke")).length() -1);
        stroke = new ColorsParser().colorFromString(drawStroke);
        return stroke;
    }
    public static List<BlockDrawer> fillKExists (Map<String, String> map, List<BlockDrawer> fillK) {
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
    
    public static class CreateBlock implements BlockCreator {
        //members
        private int height;
        private int width;
        private int hit_points;
        private BlockDrawer fill;
        private Map<Integer, BlockDrawer> fillKMap;
        private java.awt.Color stroke;
        //constructor
        public CreateBlock(int height, int width, int hit_points, BlockDrawer fill, Map<Integer, BlockDrawer> fillKMap, java.awt.Color stroke) {
            this.height = height;
            this.width = width;
            this.hit_points = hit_points;
            this.fill = fill;
            this.fillKMap = fillKMap;
            this.stroke = stroke;
        }
        @Override
        public Block create(int xpos, int ypos) {
            Rectangle rect = new Rectangle(new Point(xpos, ypos), this.width, this.height);
            Block block = new Block(rect, this.fill, this.fillKMap, this.stroke, this.hit_points);
            return block;
        }
        
    }
    public static void main(String[] args) {
        try {
            BlocksFromSymbolsFactory b = BlocksDefinitionReader.fromReader(new FileReader("/Users/Naama/workspace/ass3e/moon_block_definitions.txt"));
            if (b.isSpaceSymbol("*")) {
                System.out.println(b.getSpaceWidth("*"));
            }
            if (b.isSpaceSymbol("-")) {
                System.out.println(b.getSpaceWidth("-")); 
            }
            if (!b.isBlockSymbol("t")) {
                System.out.println("doesnt exist");
            }
            if (b.isBlockSymbol("G")) {
                System.out.println("exists");
            }
            if (b.isBlockSymbol("g")) {
                Block block = b.getBlock("g", 500, 200);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
 }