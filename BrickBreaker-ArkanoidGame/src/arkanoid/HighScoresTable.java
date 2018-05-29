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

/**
 * this id the high scores table class.
 */
public class HighScoresTable {
    // members
    private int size;
    private List<ScoreInfo> highScores;

    /**
     * Create an empty high-scores table with the specified size.
     * @param size - means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<ScoreInfo>();
    }

    /**
     * @param score - added to the high scores table
     */
    public void add(ScoreInfo score) {
        int index = this.getRank(score.getScore()) - 1;
        if (index >= 0) {
            this.highScores.add(index, score);
            if (this.getHighScores().size() > this.size()) {
                this.getHighScores().remove(this.getHighScores().size() - 1);
            }
        }
    }

    /**
     * @return table size.
     */
    public int size() {
        return this.size;
    }

    /**
     * @return the current high scores, the list is sorted such that the highest scores come first
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * @param score the scores that earned
     * @return the rank of the current score - the place it will be on the list if added
     */
    public int getRank(int score) {
        int rank = -1;
        // Rank 1 means the score will be highest on the list.
        if (this.highScores.size() == 0) {
            return 1;
            //if high scores table is full
        } else if (this.highScores.size() < this.size) {
            for (int i = 0; i < this.highScores.size(); i++) {
                if (score > this.highScores.get(i).getScore()) {
                    return i + 1;
                }
            }
            return this.highScores.size() + 1;
          //if high scores table is not full
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

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file.
     * @param filename - the path of the file.
     * @throws IOException - if failed
     */
    public void load(File filename) throws IOException {
        BufferedReader br = null;
        try {
            //open the file, with the decoration of bufferedReader
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            // string separator
            String highScore;
            while ((highScore = br.readLine()) != null) {
                //verify that its a legal name
                String[] highScoreSplit = highScore.split(";");
                ScoreInfo score = new ScoreInfo(highScoreSplit[0], Integer.parseInt(highScoreSplit[1]));
                this.add(score);
            }
        } catch (IOException e) {
            //handle exception
            System.out.println("something happened while reading the file");
        } finally {
            try {
                // close the file
                if (br != null) {
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the file");
            }
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename - the path of the file.
     * @throws IOException - if failed
     */
    public void save(File filename) throws IOException {
        PrintWriter writer = null;
        try {
            // create output stream with writer wrappers
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename)));
            for (int i = 0; i < this.highScores.size(); i++) {
                writer.println(this.highScores.get(i).getName() + ";"
            + Integer.toString(this.highScores.get(i).getScore()));
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

    /**
     * Read a table from file and return it.
     * @param fileName - the path of the file.
     * @return a high scored table
     */
    public static HighScoresTable loadFromFile(File fileName) {
        // If the file does not exist an empty table is returned
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
            // string separator
            String highScore;
            while ((highScore = br.readLine()) != null) {
                //verify that its a legal name
                String[] highScoreSplit = highScore.split(";");
                ScoreInfo score = new ScoreInfo(highScoreSplit[0], Integer.parseInt(highScoreSplit[1]));
                scoresList.add(score);
            }
        } catch (IOException e) {
            //handle exception
            System.out.println("something happened while reading the file");
        } finally {
            try {
                if (br != null) {
                    // close the file
                br.close();
            }
            } catch (IOException e) {
                System.out.println("could not close the file");
            }
        }
        HighScoresTable hst = new HighScoresTable(5);
        for (ScoreInfo score : scoresList) {
            hst.add(score);
        }
        return hst;
    }
}