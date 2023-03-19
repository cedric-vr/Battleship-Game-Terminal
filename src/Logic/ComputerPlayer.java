package Logic;

import Util.Coordinate;
import Attack.attack_result;
import Util.Display;

class ComputerPlayer extends Player {
    // implements the Computer player for the game
    void move(Player opponent) {
        // implements a move (attacking) for a computer player
        assert opponent != this;

        Coordinate a = opponent.select_coordinate_automatically();
        attack_result result = opponent.attack(a);
        Display.draw_attack_message_computer(result);
    }
    void place_ships(Player opponent) {
        // implements placing the ships for a computer player
        assert this != opponent;

        this.fleet.place_ships_automatically(this.sea);
    }
}
