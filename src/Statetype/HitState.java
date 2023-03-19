package Statetype;

public class HitState extends StateType{
    public HitState(StateType state) {
        // a hit state means that the ship on the square is hit but not yet sunk
        super(state.get_ship(),state.get_ship().get_hit_label(),state.get_ship().get_hit_label(), state.get_ship().get_ship_label());
    }

}
