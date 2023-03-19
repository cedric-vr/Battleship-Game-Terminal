package Ships;

enum ShipType {
    //defines the four types of ships and a Nullship as a placeholder

    Carrier(6,1,"C", "carrier"),
    Battleship(4,2,"B", "battleship"),
    Submarine(3,3,"S", "submarine"),
    Patrol(2,4,"P", "patrol boat"),
    Nullship(0,0," ", "");

    private final int length;
    private final int health;
    private final String label;
    private final int number;
    private final String name;
    private static final String hit_label = "X";

    ShipType(int length, int number_in_fleet, String label, String name) {
        assert label.length() > 0 && length >= 0 && number_in_fleet >= 0;

        this.length = length;
        this.label = label;
        this.health = length;
        this.number = number_in_fleet;
        this.name = name;
    }

    // the following getter methods return instance variables
    int get_length() {
        return this.length;
    }
    int get_health(){
        return this.health;
    }
    String get_label(){
        return this.label;
    }
    int get_number(){
        return this.number;
    }
    String get_name(){
        return this.name;
    }
    String get_hit_label() {
        return hit_label;
    }
}

