package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import levels.LevelInformation;

public class LevelSet {
    //members
    private List<LevelSetInfo> levelsSet;
    //constructor
    public LevelSet() {
        this.levelsSet = new ArrayList<LevelSetInfo>();
    }
    public List<LevelSetInfo> getListOfSet() {
        return this.levelsSet;
    }
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if(lineNumReader != null) {
                    lineNumReader.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the stream");
            }
        }
        return newLevelSet; 
    }
    public static class LevelSetInfo {
        // members
        private String keySet;
        private String description;
        private String pathToLevel;
        // constructor
        public LevelSetInfo() {
            this.keySet = null;
            this.description = null;
            this.pathToLevel = null;
        }
        public String getKey() {
            return this.keySet;
        }
        public String getDescription() {
            return this.description;
        }
        public String getPathToLevel() {
            return this.pathToLevel;
        }
        public void setKey(String s) {
            this.keySet = s;
        }
        public void setDescription(String s) {
            this.description = s;
        }
        public void setPathToLevel(String s) {
            this.pathToLevel = s;
        }
    }
    public static void main(String[] args) {
        LevelSet test = new LevelSet();
        try {
            test = test.fromReader(new FileReader("/Users/Naama/workspace/ass3e/level_sets.txt"));
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i=0; i<test.getListOfSet().size(); i++) {
           System.out.println(test.getListOfSet().get(i).getKey());
        System.out.println(test.getListOfSet().get(i).getDescription());
        System.out.println(test.getListOfSet().get(i).getPathToLevel()); 
        }
    } 
}
