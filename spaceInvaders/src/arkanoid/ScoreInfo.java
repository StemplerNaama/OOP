package arkanoid;

/**
 * the scoreInfo class.
 */
public class ScoreInfo {
    //members
    private String name;
    private int score;
    //constructor
    /**
     * @param name - the name of the player.
     * @param score - the score of the player.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * @return the player's name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * @return the player's score.
     */
    public int getScore() {
        return this.score;
    }
}
