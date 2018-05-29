package io;

import java.util.Map;

import arkanoid.Block;
import arkanoid.BlockCreator;

public class BlocksFromSymbolsFactory {
    private Map<String, BlockCreator> symbolBlockCreators;
    private Map<String, String> blockCreatorsSdef;
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> symbolBlockCreators, Map<String, String> blockCreatorsSdef) {
        this.symbolBlockCreators = symbolBlockCreators;
        this.blockCreatorsSdef = blockCreatorsSdef;
    }
 // returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        if (this.blockCreatorsSdef.containsKey(s)) {
            return true;
        } else {
            return false;
        }
    }
    // returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        if (this.symbolBlockCreators.containsKey(s)) {
            return true;
        } else {
            return false;
        }        
    }

    // Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int xpos, int ypos) {
        BlockCreator block = symbolBlockCreators.get(s);
        return block.create(xpos, ypos);
    }

    // Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        String width = this.blockCreatorsSdef.get(s);
        return Integer.parseInt(width);
    }

}
