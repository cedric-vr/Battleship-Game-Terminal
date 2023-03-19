package Board;
import Attack.attack_result;
import Util.Coordinate;
import Attack.attack_interface;

class Square implements attack_interface {
    // implements and represents a single physical square on the grid
    private final State current_state;
    private final Coordinate coordinate;
    Square(Coordinate a) {
        this.current_state = new State();
        this.coordinate = a;
    }

    boolean is_empty() {
        // returns whether the square is empty
        return this.current_state.is_empty();
    }
    Coordinate get_coordinate() {
        // returns the coordinate of the square
        return this.coordinate.get_copy();
    }
    boolean can_be_attacked() {
        // returns whether the square can be attacked or not
        return this.current_state.can_be_attacked();
    }
    void place_ship(Ships.Ship ship) {
        // places the ship on the square
        if (this.is_empty()) {
            this.current_state.place_ship(ship);
        }
    }

    public attack_result attack(Coordinate a) {
        //execute the attack process for a square
        if(Coordinate.equal(a,this.coordinate))
            return this.current_state.attack(a);
        else
            return attack_result.Invalid;
    }
    public void update(Coordinate a) {
        // executes the update process for a square
        if(Coordinate.equal(a,this.coordinate))
            this.current_state.update(a);
    }

    // the following getter methods return the labels for the grids
    String get_target_label(){
        return this.current_state.get_target_label();
    }
    String get_ocean_label(){
        return this.current_state.get_ocean_label();
    }
    String get_final_label(){
        return this.current_state.get_final_label();
    }
}
