package Ships;
import Util.Coordinate;
import Board.Sea;
import Attack.attack_interface;
import Attack.attack_result;
import java.util.ArrayList;

public class Ship implements attack_interface {
    private Sea sea;
    private int health;
    private final ShipType shiptype;
    private boolean is_placed = false;
    private ArrayList<Coordinate> coordinates = new ArrayList<>();
    private ArrayList<Coordinate> placement = new ArrayList<>();
    private boolean update = false;
    private Coordinate bow;
    private Coordinate stern;


    public Ship(ShipType type) {
        // constructs a ship if a specific type is provided

        this.shiptype = type;
        this.health = type.get_health();
    }

    public Ship() {
        // constructs a ship if no type is provided (default is Nullship)

        this.shiptype = ShipType.Nullship;
        this.health = this.shiptype.get_health();
    }
    public void place_ship(Sea sea, Coordinate a, Coordinate b) {
        // places a ship on the sea at the provided location
        // the location must be valid position for placing a ship of that type
        // a ship can be placed exactly once it its lifecycle.
        // a Nullship cannot be attacked.

        assert Coordinate.is_in_line(a, b) && (Coordinate.distance(a, b) ==
                (this.get_length() - 1)) && !this.is_placed && !this.is_nulltype();

        if (!this.is_placed) {
            this.sea = sea;
            sea.place_ship(this, a, b, this.get_length());
            this.coordinates = Coordinate.get_range(a,b);
            this.placement = Coordinate.get_range(a,b);
            this.bow = a;
            this.stern = b;
            this.is_placed = true;
        }
    }

    // the following four getter methods return instance variables
    int get_length(){
        return this.shiptype.get_length();
    }
    String get_name(){
        return this.shiptype.get_name();
    }
    public String get_ship_label(){
        return this.shiptype.get_label();
    }
    public String get_hit_label(){
        return this.shiptype.get_hit_label();
    }
    public boolean update_pending() {
        // returns whether the ship is currently in its update process or not
        // only when the update process in live the state of the squares (that the ship is located on) can be updated from hit to sunk
        return update;
    }
    boolean is_nulltype() {
        // returns whether a ship is a Nullship
        return this.shiptype == ShipType.Nullship;
    }
    boolean is_alive() {
        // returns whether a ship is still alive (not yet sunk) or not (sunk).
        return this.health > 0;
    }
    public void update(Coordinate a) {
        // takes note of the incoming update confirmations from squares
        // if square at Coordinate a is updated from hit to sunk than a is added to the coordinates list
        if (this.update_pending() && !this.coordinates.contains(a) && this.placement.contains(a))
            this.coordinates.add(a);
    }

    public attack_result attack(Coordinate a) {
        /* handles the attack of the ship
           a ship can only be attacked if:
            - it is not yet sunk,
            - it was already placed on a sea (board),
            - the provided coordinate is one of the coordinates the ship is standing on,
            - the ship is not currently updating squares and their states,
            - the ship is not a Nullship and
            - the provided coordinate a has not already been attacked previously.

           if any of those preconditions fail, the game is terminated immediately
         */

        if (this.is_alive() && this.is_placed && this.placement.contains(a) &&
                !this.update_pending() && !this.is_nulltype() && this.coordinates.contains(a)) {
            this.coordinates.remove(a);

            if (this.health == 1) {
                this.update = true;
                for (Coordinate c : this.placement)
                    sea.update(c);
                this.update = false;

                if (this.coordinates.containsAll(placement) && this.coordinates.size() == placement.size()) {
                    this.health -= 1;
                    return attack_result.Sunk;
                }
                else
                    return attack_result.Invalid;
            }
            else {
                this.health -= 1;
                return attack_result.Hit;
            }
        }
        else
            return attack_result.Invalid;
    }
}
