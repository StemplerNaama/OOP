package io;

import java.util.Map;

import arkanoid.Block;
import arkanoid.BlockCreator;

/**
 * the class that holds the sdef and bdef maps.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, BlockCreator> symbolBlockCreators;
    private Map<String, String> blockCreatorsSdef;
    /**
     * @param symbolBlockCreators - the map <block symbol, block type>
     * @param blockCreatorsSdef - the map of the spaces_def <space symbol, width>
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> symbolBlockCreators, Map<String,
            String> blockCreatorsSdef) {
        this.symbolBlockCreators = symbolBlockCreators;
        this.blockCreatorsSdef = blockCreatorsSdef;
    }
    /**
     * @param s - the symbol of space to look for
     * @return - true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.blockCreatorsSdef.containsKey(s);
    }
    /**
     * @param s - the symbol of block to look for
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.symbolBlockCreators.containsKey(s);
    }
    /**
     * @param s - the symbol of block
     * @param xpos - the xVal to locate the block
     * @param ypos - the yVal to locate the block
     * @return a block according to the definitions associated
     */
    public Block getBlock(String s, int xpos, int ypos) {
        BlockCreator block = symbolBlockCreators.get(s);
        return block.create(xpos, ypos);
    }
    /**
     * @param s - the symbol of space
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        String width = this.blockCreatorsSdef.get(s);
        return Integer.parseInt(width);
    }
}