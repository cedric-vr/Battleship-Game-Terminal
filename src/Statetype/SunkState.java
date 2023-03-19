package Statetype;

public class SunkState extends StateType {
    public SunkState(StateType state) {
        // an occupied state means that there is a ship on the square that is sunk
        super(state.get_ship(), state.get_ship().get_ship_label(), state.get_ship().get_hit_label(),state.get_ship().get_ship_label());
    }
}
