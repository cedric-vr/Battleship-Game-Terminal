package Logic;

import Util.Coordinate;
import Util.Display;
import Attack.attack_result;

class HumanPlayer extends Player {

    void move(Player opponent) {
        // implements a move (attacking) for a human player
        assert opponent != this;

        opponent.draw_target();
        Display.draw_separator();
        this.sea.draw_ocean();
        Coordinate a = opponent.select_coordinate_manually();
        attack_result result = opponent.attack(a);
        Display.draw_attack_message_human(result);
    }

    void place_ships(Player opponent) {
        // implements placing the ships for a human player
        assert opponent != this;

        opponent.draw_target();
        Display.draw_separator();
        this.sea.draw_ocean();
        this.fleet.place_ships_manually(this.sea);
        opponent.draw_target();
        Display.draw_separator();
        this.sea.draw_ocean();
    }
}
