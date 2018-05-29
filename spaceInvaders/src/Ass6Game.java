import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import arkanoid.GameFlow;
import arkanoid.HighScoresTable;
import arkanoid.Level;
import biuoop.KeyboardSensor;
//import io.LevelSet;
//import io.LevelSpecificationReader;
//import levels.LevelInformation;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;

/**
 * the main of the program.
 */
public class Ass6Game {
    //members
    private static final int FRAMES_PER_SEC = 60;
    /**
     * The program creates new game, initializes it and runs it.
     * @param args - user's input if there is
     */
    public static void main(String[] args) {
        String hstPath = "highscores.txt";
        final HighScoresTable hst = HighScoresTable.loadFromFile(new File(hstPath));
        if (!new File(hstPath).exists()) {
            try {
             hst.save(new File(hstPath));
         } catch (IOException e) {
             e.printStackTrace();
         }
        }
        final AnimationRunner ar = new AnimationRunner(FRAMES_PER_SEC);

        //final int size = levelsSet.getListOfSet().size();
        //creating subMenu for the levels sets
       /* Menu<Task<Void>> levelsSetMenu = new MenuAnimation<Task<Void>>("levels Sets", ar,
                ar.getGui().getKeyboardSensor());*/

          Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Space Invaders", ar, ar.getGui().getKeyboardSensor());
          Task<Void> startTask = new Task<Void>() {
              public Void run() {
                 
                  GameFlow game = new GameFlow(ar, ar.getGui().getKeyboardSensor(), hst);
                  game.runLevels();

                  
                return null;
              }
          };
          menu.addSelection("s", "Start Game", startTask);
          
          
          
          Task<Void> highScoresTask = new Task<Void>() {
              public Void run() {
                  ar.run(new KeyPressStoppableAnimation(ar.getGui().getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                          new HighScoresAnimation(hst)));
                return null;
              }
          };
          menu.addSelection("h", "High scores", highScoresTask);
          
          
          
          Task<Void> quitTask = new Task<Void>()
                  {
              public Void run() {
                System.exit(0);
                return null;
              }
                  };
        menu.addSelection("q", "Quit", quitTask);
        
        while (true) {
           ar.run(menu);
           // wait for user selection
           Task<Void> task = menu.getStatus();
           task.run();
           menu.reset();
        }
    }
}
