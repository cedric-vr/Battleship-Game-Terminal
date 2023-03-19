package Statetype;

import Util.Coordinate;
import Attack.attack_result;
import Attack.attack_interface;
public abstract class StateType implements attack_interface {
    // an abstract class to implement the five states
    private final Ships.Ship ship;
    private final String target_label;
    private final String ocean_label;
    private final String final_label;

    public StateType(Ships.Ship ship, String target_label, String ocean_label, String final_label) {
        // constructs a StateType with given parameters
        this.ship = ship;
        this.target_label = target_label;
        this.ocean_label = ocean_label;
        this.final_label = final_label;
    }

    // the following four getter methods return instance variables
    public String get_target_label(){
        return this.target_label;
    }
    public String get_ocean_label() {
        return this.ocean_label;
    }
    public String get_final_label(){
        return this.final_label;
    }
    Ships.Ship get_ship(){
        return this.ship;
    }

    public boolean update_pending() {
        // returns whether the ship on the square is currently in the update process
        return this.ship.update_pending();
    }
    public void update(Coordinate a) {
        // acknowledges the successful update to the ship
        this.ship.update(a);
    }
    public attack_result attack(Coordinate a) {
        // attacks the ship on the square
        return this.ship.attack(a);
    }
}
