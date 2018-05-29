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
import biuoop.KeyboardSensor;
import io.LevelSet;
import io.LevelSpecificationReader;
import levels.LevelInformation;
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
        String pathToSet;
        //check if there is an input- file path
        if (args.length > 0) {
            pathToSet = args[0];
          // use default when there is no input
        } else {
            pathToSet = "level_sets.txt";
        }
        // loading the high scores table
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
        //opening the level_set txt file
        LevelSet levelsSet = new LevelSet();
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(pathToSet);
            levelsSet = levelsSet.fromReader(new InputStreamReader(is));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final LevelSet levelsSetTemp = levelsSet;
        final int size = levelsSet.getListOfSet().size();
        //creating subMenu for the levels sets
        Menu<Task<Void>> levelsSetMenu = new MenuAnimation<Task<Void>>("levels Sets", ar,
                ar.getGui().getKeyboardSensor());
        for (int i = 0; i < size; i++) {
            final int tempindex = i;
                    levelsSetMenu.addSelection(levelsSet.getListOfSet().get(i).getKey(),
                            levelsSet.getListOfSet().get(i).getDescription(), new Task<Void>()
                            {
                        public Void run() {
                            LevelSpecificationReader levelsText = new LevelSpecificationReader();
                            List<LevelInformation> listLevelsInfo = null;
                            try {
                                String pathLevelTemp = levelsSetTemp.getListOfSet().get(tempindex).getPathToLevel();
                                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(pathLevelTemp);
                                listLevelsInfo = levelsText.fromReader(new InputStreamReader(is));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //running the game
                            GameFlow game = new GameFlow(ar, ar.getGui().getKeyboardSensor(), hst);
                            game.runLevels(listLevelsInfo);
                            return null;
                            }
                            });
                }
          Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", ar, ar.getGui().getKeyboardSensor());
          menu.addSubMenu("s", "Start Game", levelsSetMenu);
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
