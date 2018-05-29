package arkanoid;
import java.io.File;
import java.io.IOException;
import java.util.List;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import core.Counter;
//import levels.LevelInformation;

/**
 * this is the game flow class- that is in charge of fluence of the game.
 */
public class GameFlow {
    // members
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter livesCounter;
    private Counter scoreCounter;
    private HighScoresTable hst;
    //private Level level;
    // constructor
    /**
     * @param ar -this is the AnimationRunner
     * @param ks -this is the keyboard
     * @param hst - this is the high score table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable hst) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter(0);
        this.livesCounter = new Counter(3);
        this.hst = hst;
    }

    /**
     * @param levels -this is the list of the levels to run
     */
    public void runLevels() {
        int counter = 1;
        while (true) {
            Level newLevel = new Level(counter, this.keyboardSensor);
          GameLevel glevel = new GameLevel(this.keyboardSensor, this.animationRunner, this.livesCounter, this.scoreCounter, newLevel);
          glevel.initialize();
          System.out.println("end initialize");
          while (glevel.isRemainBlocks() && (this.livesCounter.getValue() > 0)) {
             glevel.playOneTurn();
          }
          if (this.livesCounter.getValue() <= 0) {
             break;
          }
          counter ++;
       }
       // if the score has the right rank to be shown in the high score table
       if ((this.hst.getRank(this.scoreCounter.getValue()) > 0)) {
           DialogManager dialog = this.animationRunner.getGui().getDialogManager();
           String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "");
           ScoreInfo newPlayer = new ScoreInfo(name, this.scoreCounter.getValue());
           this.hst.add(newPlayer);
       }
           try {
               this.hst.save(new File("highscores.txt"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        // running the end screen
           this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                   new EndScreen(this.livesCounter.getValue(), this.scoreCounter.getValue())));
           // running the high score screen
       this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
               new HighScoresAnimation(this.hst)));
    }
    /**
     * @return the high score table
     */
    public HighScoresTable getTable() {
        return this.hst;
    }
 }
