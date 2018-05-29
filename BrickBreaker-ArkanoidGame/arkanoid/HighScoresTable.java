package arkanoid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HighScoresTable {
    // members
    private int size;
    private List<ScoreInfo> highScores;
    //private File tableFile;

    // Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<ScoreInfo>();
        // מה לעשות?
        //this.tableFile = null;
    }

    // Add a high-score.
    public void add(ScoreInfo score) {
        int index = this.getRank(score.getScore()) - 1;
        // לבדוק אם התנאי הכרחי
        if (index >= 0) {
            this.highScores.add(index, score);
            if (this.getHighScores().size() > this.size()) {
                this.getHighScores().remove(this.getHighScores().size() - 1);
            }
        }
    }

    // Return table size.
    public int size() {
        return this.size;
    }

    // Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    // return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    // be added to the list.
    public int getRank(int score) {
        int rank = -1;
        if (this.highScores.size() == 0) {
            return 1;
        } else if (this.highScores.size() < this.size) {
            for (int i = 0; i < this.highScores.size(); i++) {
                if (score > this.highScores.get(i).getScore()) {
                    return i + 1;
                } 
            } 
            return this.highScores.size() + 1;
            } else {
                for (int i = 0; i < this.highScores.size(); i++) {
                    if (score > this.highScores.get(i).getScore()) {
                        rank = i + 1;
                        break;
                        }
                    }
                return rank;
                }
        }

    // Clears the table
    public void clear() {
        this.highScores.clear();
    }

    // Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        BufferedReader br = null;
        try {
            //open the file, with the decoration of bufferedReader
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            // string seperator
            String highScore;
            while((highScore = br.readLine()) != null) {
                //TODO: verify that its a legal name
                String[] highScoreSplit = highScore.split(";");
                ScoreInfo score = new ScoreInfo(highScoreSplit[0], Integer.parseInt(highScoreSplit[1]));
                this.add(score);
            }
            
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("something happened while reading the file");
        } finally {
            try {
                if(br != null){
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the file");
            }
        }
        /*HighScoresTable hst = new HighScoresTable(scoresList.size());
        for(ScoreInfo score : scoresList){
            hst.add(score);*/
        
    }

    // Save table data to the specified file.
    public void save(File filename) throws IOException {
        PrintWriter writer = null ;
        try {
            // create output stream with writer wrappers
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename)));
            for (int i = 0; i < this.highScores.size(); i++) {
                writer.println(this.highScores.get(i).getName()+";"+Integer.toString(this.highScores.get(i).getScore()));
            }

        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
            } finally {
                // Exception might have happened at constructor
                if (writer != null) {
                    // closes fileOutputStream too
                    writer.close();
                    }
                }  
        }

    // Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File fileName) {
        if (!fileName.exists()) {
            return new HighScoresTable(5);
        }
        List<ScoreInfo> scoresList = new ArrayList<ScoreInfo>();
        
        BufferedReader br = null;
        try {
            //open the file, with the decoration of bufferedReader
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName)));
            // string seperator
            String highScore;
            while((highScore = br.readLine()) != null){
                //TODO: verify that its a legal name
                String[] highScoreSplit = highScore.split(";");
                ScoreInfo score = new ScoreInfo(highScoreSplit[0], Integer.parseInt(highScoreSplit[1]));
                scoresList.add(score);
            }
            
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("something happened while reading the file");
        } finally {
            try {
                if(br != null){
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the file");
            }
        }
        HighScoresTable hst = new HighScoresTable(5);
        for(ScoreInfo score : scoresList){
            hst.add(score);
        }
        return hst;
        
    }
    /*public static void main(String[] args) throws IOException {
        //HighScoresTable hst = HighScoresTable.loadFromFile(new File("/Users/Naama/workspace/ass3e/highscores.txt"));
        HighScoresTable hst = new HighScoresTable(5);
        hst.add(new ScoreInfo("efsg", 100));
        hst.save(new File("/Users/Naama/workspace/ass3e/highscores.txt"));
        //hst.load(new File("/Users/Naama/workspace/ass3e/highscores.txt"));
        hst.add(new ScoreInfo("b", 40));
        hst.add(new ScoreInfo("e", 100));
        hst.add(new ScoreInfo("d", 50));
        hst.add(new ScoreInfo("c", 110));
        hst.add(new ScoreInfo("a", 90));
        hst.add(new ScoreInfo("da", 100));
        hst.save(new File("/Users/Naama/workspace/ass3e/highscores.txt"));
           
        for (int i = 0; i < hst.getHighScores().size(); i++) {
            System.out.println(hst.getHighScores().get(i).getName() + " ;   " + hst.getHighScores().get(i).getScore());
        }
        //hst.clear();
        */

        
    
}

//[a-zA-Z0-9][ a-zA-Z0-9]*,\d+