package io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * the class that gets the text file of the levels sets.
 */
public class LevelSet {
    //members
    private List<LevelSetInfo> levelsSet;
    //constructor
    /**
     * constructor.
     */
    public LevelSet() {
        this.levelsSet = new ArrayList<LevelSetInfo>();
    }
    /**
     * @return the list of the levels sets.
     */
    public List<LevelSetInfo> getListOfSet() {
        return this.levelsSet;
    }
    /**
     * @param reader - the text file of the levels sets and their path to file.
     * @return the levels set.
     */
    public LevelSet fromReader(java.io.Reader reader) {
        LevelSet newLevelSet = new LevelSet();
        LineNumberReader lineNumReader = null;
        try {
            //open the file, with the decoration of bufferedReader
            lineNumReader =  new LineNumberReader(reader);
            // echo each line the user inputs with upper case
            String line = lineNumReader.readLine();
            LevelSetInfo level = new LevelSetInfo();
            while (line != null) {
                if (lineNumReader.getLineNumber() % 2 == 1) {
                    String[] splitInfo = line.split(":");
                    level.setKey(splitInfo[0]);
                    level.setDescription(splitInfo[1]);
                } else if (lineNumReader.getLineNumber() % 2 == 0) {
                    level.setPathToLevel(line);
                    newLevelSet.getListOfSet().add(level);
                    level = new LevelSetInfo();
                }
                line = lineNumReader.readLine();
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (lineNumReader != null) {
                    lineNumReader.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the stream");
            }
        }
        return newLevelSet;
    }
    /**
     * mini class that holds the info of each level set.
     */
    public static class LevelSetInfo {
        // members
        private String keySet;
        private String description;
        private String pathToLevel;
        // constructor
        /**
         * constructor- initializing to null.
         */
        public LevelSetInfo() {
            this.keySet = null;
            this.description = null;
            this.pathToLevel = null;
        }
        /**
         * @return the key of level set.
         */
        public String getKey() {
            return this.keySet;
        }
        /**
         * @return the description of level set.
         */
        public String getDescription() {
            return this.description;
        }
        /**
         * @return the file to path to level set.
         */
        public String getPathToLevel() {
            return this.pathToLevel;
        }
        /**
         * @param s - the key to set.
         */
        public void setKey(String s) {
            this.keySet = s;
        }
        /**
         * @param s - the description to set.
         */
        public void setDescription(String s) {
            this.description = s;
        }
        /**
         * @param s - the path to level to set.
         */
        public void setPathToLevel(String s) {
            this.pathToLevel = s;
        }
    }
}
