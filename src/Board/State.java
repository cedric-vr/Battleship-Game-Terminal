package Board;

import Attack.attack_result;
import Statetype.*;
import Attack.attack_interface;
import Util.Coordinate;

class State implements attack_interface {
    // implements the management of the states of a square
    private StateType current_state;

    State(){
        this.current_state = new EmptyState();
    }

    boolean is_empty() {
        // returns whether the state is an empty-state
        return this.current_state instanceof EmptyState;
    }
    boolean can_be_attacked() {
        // returns whether the state allows for an attack
        return this.current_state instanceof EmptyState || this.current_state instanceof OccupiedState;
    }
    void place_ship(Ships.Ship ship) {
        // changes the state when the ship is placed
        if(this.current_state instanceof EmptyState){
            this.current_state = new OccupiedState(ship);
        }
    }

    public attack_result attack(Coordinate a) {
        // executes the attack process and changes the states accordingly
        if (this.current_state instanceof OccupiedState) {
            this.current_state = new HitState(this.current_state);
            return this.current_state.attack(a);
        }
        else if (this.current_state instanceof EmptyState) {
            this.current_state = new MissState(this.current_state);
            return attack_result.Miss;
        }
        else
            return attack_result.Invalid;
    }

    public void update(Coordinate a) {
        // executes the update process (change from hit to sunk)
        if (this.current_state instanceof HitState && this.current_state.update_pending()) {
            this.current_state = new SunkState(this.current_state);
            this.current_state.update(a);
        }
    }

    // the following getter methods return the labels for the grids
    String get_ocean_label() {
        return this.current_state.get_ocean_label();
    }
    String get_target_label() {
        return this.current_state.get_target_label();
    }
    String get_final_label() {
        return this.current_state.get_final_label();
    }

}
