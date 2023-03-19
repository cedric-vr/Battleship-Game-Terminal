package Board;

import Attack.attack_interface;
import Attack.attack_result;
import Ships.Ship;
import Util.Coordinate;
import Util.Display;
import java.util.ArrayList;

public class Sea implements attack_interface {
    // implements and represents the physical grid consisting of many squares
    private static final int board_size = Coordinate.get_max_x() - Coordinate.get_min_x() + 1;
    private final Square[][] sea;
    private enum grid_type{
        final_target, target, ocean
    }

    public Sea() {
        // initializes a new empty sea with new empty squares.

        this.sea = new Square[board_size][board_size];

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                Coordinate a = new Coordinate(Coordinate.to_char(j), i);
                this.sea[i][j] = new Square(a);
            }
        }
    }

    public boolean is_empty(){
        // returns whether the sea is empty (no ships and no hits) or not

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (!this.sea[i][j].is_empty())
                    return false;
            }
        }
        return true;
    }
    public boolean can_ship_be_placed(Coordinate a, Coordinate b, int length) {
        // returns whether a ship of length n can be placed from Coordinate a (bow) to Coordinate b (stern)
        assert Coordinate.is_in_line(a,b) && length > 0;

        if (!Coordinate.is_in_line(a,b) || Coordinate.distance(a,b) != length - 1)
            return false;
        else {
            ArrayList<Coordinate> range = Coordinate.get_range(a, b);

            for (Coordinate c : range) {
                if (!sea[c.get_x()][c.get_y()].is_empty())
                    return false;
            }
            return true;
        }
    }

    private boolean can_ship_be_placed_in_any_direction(Coordinate a, int length) {
        // returns whether a ship of length n can be placed in any direction (right, left, up or down) at Coordinate a
        // bow (or stern respectively) would be placed at Coordinate a

        assert length > 0;

        ArrayList<Coordinate> points_with_distance = a.with_distance(length - 1);

        for (Coordinate b : points_with_distance) {
            if (this.can_ship_be_placed(a, b, length))
                return true;
        }
        return false;
    }

    public void place_ship(Ship ship, Coordinate a, Coordinate b, int length) {
        // places a ship from Coordinate a (bow) to Coordinate b (stern)
        // it must be possible to actually place a ship of length n at this location

        assert can_ship_be_placed(a, b, length);

        if (can_ship_be_placed(a,b, length)) {
            ArrayList<Coordinate> range = Coordinate.get_range(a, b);
            for (Coordinate c : range)
                sea[c.get_x()][c.get_y()].place_ship(ship);
        }
    }

    public boolean can_be_attacked(Coordinate a) {
        // returns whether a square can be attacked or not

        return sea[a.get_x()][a.get_y()].can_be_attacked();
    }

    public attack_result attack(Coordinate a) {
        // executes the attack process for square at Coordinate a

        return sea[a.get_x()][a.get_y()].attack(a);
    }

    public void update(Coordinate a) {
        // executes the update process for square at Coordinate a

        this.sea[a.get_x()][a.get_y()].update(a);
    }

    public ArrayList<Coordinate> get_random_ship_position(int length) {
        // returns a list of two random coordinates
        // the two coordinates represent a valid position for placing a ship of length n
        // the length must be greater than 0

        assert length > 0;

        ArrayList<Coordinate> points = new ArrayList<>();

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                Coordinate a = this.sea[i][j].get_coordinate();
                if (this.can_ship_be_placed_in_any_direction(a, length)) {
                    points.add(a);

                }
            }
        }

        Coordinate selected_point = Coordinate.random_coordinate(points);
        ArrayList<Coordinate> direction = selected_point.with_distance(length - 1);
        ArrayList<Coordinate> possible_direction = new ArrayList<>();

        for (Coordinate b : direction) {
            if (this.can_ship_be_placed(selected_point, b, length))
                possible_direction.add(b);
        }

        Coordinate selected_point_2 = Coordinate.random_coordinate(possible_direction);

        ArrayList<Coordinate> result = new ArrayList<>();
        result.add(selected_point);
        result.add(selected_point_2);

        return result;
    }


    public Coordinate select_target_manually() {
        // gets a user input in form of a valid coordinate
        // if the coordinate cannot be attacked a new Coordinate is requested
        // returns the coordinate

        Display.draw_enter_coordinate_to_attack();
        Coordinate target = Coordinate.input_coordinate();

        while (!this.can_be_attacked(target)) {
            Display.draw_invalid_coordinate_input();
            target = Coordinate.input_coordinate();
        }
        return target;
    }


    public Coordinate select_target_automatically() {
        // selects a random Coordinate that can be attacked

        ArrayList<Coordinate> points = new ArrayList<>();

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                Square square = this.sea[i][j];
                Coordinate a = square.get_coordinate();

                if (square.can_be_attacked())
                    points.add(a);
            }
        }
        return Coordinate.random_coordinate(points);
    }

    private String[][] generate_grid(grid_type e){
        // draws a grid of the requested grid_type;

        String[][] grid = new String[board_size][board_size];

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {

                if(e == grid_type.ocean)
                    grid[i][j] = this.sea[i][j].get_ocean_label();
                else if(e == grid_type.target)
                    grid[i][j] = this.sea[i][j].get_target_label();
                else if(e == grid_type.final_target)
                    grid[i][j] = this.sea[i][j].get_final_label();
            }
        }
        return grid;
    }

    public void draw_ocean() {
        // generates the ocean grid and calls the draw method in class Display
        Display.draw_ocean(generate_grid(grid_type.ocean));
    }
    public void draw_target() {
        // generates the target grid and calls the draw method in class Display
        Display.draw_target(generate_grid(grid_type.target));
    }
    public void draw_final() {
        // generates the final target grid and calls the draw method in class Display
        Display.draw_final(generate_grid(grid_type.final_target));
    }
}



