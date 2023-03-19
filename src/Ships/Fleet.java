package Ships;
import Util.Coordinate;
import java.util.ArrayList;
import Board.Sea;
import Util.Display;
public class Fleet {
    private final ArrayList<Ship> ships;

    public Fleet() {
        // a fleet is initialized with the correct number of ships for each type

        this.ships = new ArrayList<>();

        for (ShipType type: ShipType.values()) {
            adder(type);
        }
    }

    private void adder(ShipType type) {
        // adds the correct number of ships of a certain type to the fleet

        for (int i = 0; i < type.get_number(); i++) {
            Ship ship = new Ship(type);
            this.ships.add(ship);
        }
    }


    public void place_ships_manually(Sea sea) {
        // places all ships of the fleet manually on the provided sea
        // obtains two coordinates from an input via the Coordinate class
        // checks whether a ship of that type can be placed there, if not, new coordinates are requested from the user
        // than places the ship at this location
        // the provided sea must be empty (no ships already placed on it)

        assert sea.is_empty();

        for (Ship ship : this.ships) {
            Display.draw_enter_coordinates_for_ship(ship.get_name(), ship.get_length());
            ArrayList<Coordinate> points = Coordinate.input_list_of_coordinates(2);

            while (!sea.can_ship_be_placed(points.get(0), points.get(1), ship.get_length())) {
                Display.draw_invalid_ship_placement(ship.get_name(), ship.get_length());
                points = Coordinate.input_list_of_coordinates(2);
            }
            ship.place_ship(sea, points.get(0), points.get(1));
        }
    }


    public void place_ships_automatically(Sea sea) {
        // places all ships of the fleet automatically on the provided sea
        // obtains two random coordinates from sea (where a ship of that type can actually be placed)
        // than places the ship at this location
        // the provided sea must be empty (no ships already placed on it)

        assert sea.is_empty();

        for (Ship ship: this.ships) {
            ArrayList<Coordinate> coordinates = sea.get_random_ship_position(ship.get_length());
            ship.place_ship(sea, coordinates.get(0), coordinates.get(1));
        }
    }

    public boolean is_alive() {
        // returns whether the fleet is sunk entirely or whether at least one ship is not yet sunk

        for (Ship ship: this.ships) {
            if (ship.is_alive()) {
                return true;
            }
        }
        return false;
    }

}
