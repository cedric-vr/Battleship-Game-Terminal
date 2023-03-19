package Logic;
import Attack.attack_result;
import Attack.attack_interface;
import Ships.Fleet;
import Board.Sea;

import Util.Coordinate;

abstract class Player implements attack_interface {
    // implement a generic player
    final Sea sea;
    final Fleet fleet;

    Player() {
        this.sea = new Sea();
        this.fleet = new Fleet();
    }

    abstract void move(Player opponent);
    abstract void place_ships(Player opponent);
    boolean is_alive() {
        // returns whether the fleet of a player is still alive (not sunk) nor not (sunk)
        return this.fleet.is_alive();
    }

    public attack_result attack(Coordinate a) {
        // executes the attack on the player
        return this.sea.attack(a);
    }
    public void update(Coordinate a) {
        // updates the coordinate a on the sea
        // is not used in our game
        this.sea.update(a);
    }
    Coordinate select_coordinate_manually() {
        // manually selects and returns a valid coordinate for an attack onto the player
        return this.sea.select_target_manually();
    }
    Coordinate select_coordinate_automatically() {
        // automatically selects and returns a valid coordinate for an attack onto the player
        return this.sea.select_target_automatically();
    }
    void draw_target() {
        // draws the target grid of the player when requested
        this.sea.draw_target();
    }
    void draw_final(Player opponent) {
        // draws the final grid of a player when the requesting opponent is no longer alive
        if (!opponent.is_alive() && opponent != this)
            this.sea.draw_final();
    }

}

