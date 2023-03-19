package Statetype;

public class MissState extends StateType {
    public MissState(StateType state) {
        // an empty state means that the square does not have a ship placed on it and was already ht
        super(state.get_ship(), "O", "O", " ");
    }
}
