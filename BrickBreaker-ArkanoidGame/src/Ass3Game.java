/**
 * the main of the program- calls the Game class to initialize a game.
 */
public class Ass3Game {
    /**
     * The program creates new game, initalizes it and runs it.
     * @param args - user's input if there is
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
