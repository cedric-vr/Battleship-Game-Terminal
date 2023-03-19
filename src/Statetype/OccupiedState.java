package Statetype;

public class OccupiedState extends StateType {
    // an occupied state means that there is a ship on the square that is not hit yet
    public OccupiedState(Ships.Ship ship) {
        super(ship, " ", ship.get_ship_label(), ship.get_ship_label());
    }
}
