package Statetype;

import Ships.Ship;

public class EmptyState extends StateType {
    // an empty state means that the square does not have a ship placed on it and was not hit yet
    public EmptyState() {
        super(new Ship(), " ", " ", " ");
    }

}
