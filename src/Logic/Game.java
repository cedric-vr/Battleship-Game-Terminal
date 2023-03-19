package Logic;

import Util.Display;
import java.util.Random;

/**
 * The Game class handles the game flow
 * - instantiates a new HumanPlayer and ComputerPlayer
 * - checks if the game is over by checking if both players are alive
 * - starts game by having players place ships
 * - loops turns of players until game over
 * - ends game when game over
 */

public class Game {
    // implements the core game logic and flow
    private final ComputerPlayer computer_player;
    private final HumanPlayer human_player;
    private final Random object_random = new Random();
    private final int random_start = object_random.nextInt(2);
    private static Game instance = new Game();

    private Game() {
        // a game needs two players (human and computer)
        this.human_player = new HumanPlayer();
        this.computer_player = new ComputerPlayer();
    }

    public static Game getInstance() {
        // returns a singleton of Game

        if (instance == null)
            instance = new Game();
        return instance;
    }

    private boolean game_over() {
        // returns whether one player is no longer alive and therefore the game over
        return !(this.human_player.is_alive() && this.computer_player.is_alive());
    }

    public void start() {
        // starts the game
        this.setup();
        this.loop();
        this.end();
    }
    private void setup() {
        // places the ships of the two fleets
        this.human_player.place_ships(this.computer_player);
        this.computer_player.place_ships(this.human_player);
    }

    private void loop() {

        /* if random_start value is 0 the human_player will start
        - if the random_start value is 1 (-> not zero) the computer_player will start
        - before each move it will be checked whether the game is over or not
         */

        if (random_start == 0)
            this.inner_loop(this.human_player, this.computer_player);
        else if (random_start == 1)
            this.inner_loop(this.computer_player, this.human_player);
    }

    private void inner_loop(Player one, Player two) {
        assert one != two;

        while (!this.game_over()) {
            one.move(two);
            if (!this.game_over())
                two.move(one);
        }
    }

    private void end() {
        // handles the end of the game when it is over

        if (computer_player.is_alive()) {
            Display.draw_computer_win();
            computer_player.draw_final(human_player);
        }
        else if (human_player.is_alive())
            Display.draw_human_win();
    }
}
