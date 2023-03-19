import Logic.Game;

/**
 * instantiates and starts a Game
 */
public class Main {
    public static void main(String[] args) {
        Game Game = Logic.Game.getInstance();
        Game.start();
    }
}
