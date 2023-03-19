package Util;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import Util.Input;

//the Coordinate class handles all the calculations and interactions with coordinates
public class Coordinate {
    private static final int min_x = 0;
    private static final int max_x = 9;
    private static final char min_y = 'A';
    private static final char max_y = 'J';
    private final int x;
    private final char c;
    private final int y;

    public Coordinate(char letter, int number) {
        // note Coordinates are constructed in the format a = new Coordinate(y,x)
        // with y as the horizontal component and x as the vertical component
       assert (is_valid_coordinate(letter, number));

       this.x = number;
       this.c = letter;
       this.y = Character.getNumericValue(letter) - 10;
    }

    private Coordinate() {
        // constructs a null coordinate (placeholder for null)

        this.x = 0;
        this.y = 0;
        this.c = 0;
    }

    // the following methods implement getter methods for the private instance variables.
    public int get_x() {
        return this.x;
    }
    public int get_y(){
        return this.y;
    }
    public static int get_min_x(){
        return min_x;
    }
    public static int get_min_y(){
        return min_y;
    }
    public static int get_max_x(){
        return max_x;
    }
    public static int get_max_y(){
        return max_y;
    }
    public Coordinate get_copy(){
        return new Coordinate(this.c, this.x);
    }

    public static boolean is_valid_coordinate(char letter, int number) {
        // validates whether a potential coordinate is valid or not
        try {
            return (min_x <= number && max_x >= number) && (min_y <= letter && max_y >= letter);
        }
        catch (Exception e) {
            return false;
        }
    }

    public static char to_char(int n) {
        // converts an integer to the corresponding char (ex: 0 becomes A, 1 becomes B, ...)
        assert is_valid_coordinate(min_y, n);

        n = n + 65;
        return (char)n;
    }


    // the following methods implement simple boolean comparisons for coordinates
    // note: "equal" and "equals" (last method in the class) are two different methods with varying functionality and implementation
    public static boolean is_in_line(Coordinate a, Coordinate b){
        return equal_x(a,b) || equal_y(a,b);
    }
    public static boolean equal_x(Coordinate a, Coordinate b){
        return a.x == b.x;
    }
    public static boolean equal_y(Coordinate a, Coordinate b){
        return a.y == b.y;
    }
    public static boolean equal(Coordinate a, Coordinate b){
        return equal_x(a,b) && equal_y(a,b);
    }
    public static boolean smaller_x(Coordinate a, Coordinate b){
        return a.x < b.x;
    }
    public static boolean smaller_y(Coordinate a, Coordinate b){
        return (a.y < b.y);
    }
    private boolean is_null() {
        return this.x == 0 && this.y == 0 && this.c == 0;
    }

    public static int distance(Coordinate a, Coordinate b) {
        // calculates the horizontal or vertical distance between two coordinates (but not diagonally!)
        // Example: Distance from A0 to A4 is 3 (and not 4). Distance from A0 to C1 does not exist.
        assert is_in_line(a,b);

        if (equal_y(a,b))
            return Math.abs(a.x - b.x);
        else
            return Math.abs(a.y - b.y);
    }

    // the following four boolean methods evaluate whether a certain coordinate can be incremented in a direction n-times or not
    // Example: A0 cannot be incremented up at all (A-1 is not a valid coordinate). A0 incremented down by 1 would become A1.
    // Example: B4 incremented to the right by 3 would become E4
    private boolean can_increment_up_by(int n) {
        return is_valid_coordinate(this.c, this.x - n);
    }
    private boolean can_increment_down_by(int n){
        return is_valid_coordinate(this.c, this.x + n);
    }
    private boolean can_increment_left_by(int n) {
        char temp  = this.c;
        temp-=n;
        return is_valid_coordinate(temp, this.x);
    }
    private boolean can_increment_right_by(int n) {
        char temp  = this.c;
        temp += n;
        return is_valid_coordinate(temp, this.x);
    }

    // the following four methods return a new Coordinated that is incremented n-times in the corresponding direction
    // example: A0 incremented 4 times to the right becomes E0.
    private Coordinate increment_up_by(int n){
        return new Coordinate(this.c, this.x-n);
    }
    private Coordinate increment_down_by(int n){
        return new Coordinate(this.c, this.x+n);
    }
    private Coordinate increment_left_by(int n) {
        char temp  = this.c;
        temp -= n;
        return new Coordinate(temp, this.x);
    }
    private Coordinate increment_right_by(int n) {
        char temp  = this.c;
        temp += n;
        return new Coordinate(temp, this.x);
    }

    public ArrayList<Coordinate> with_distance(int d) {
        // returns all Coordinates at distance d in a list.
        // Example: all points at distance 2 from A0 are: C0 and A2
        // Note: the list can be empty. Example: all points at distance 11 from A0 in a 10x10 grid (there are none)
        assert (d > 0);

        ArrayList<Coordinate> points = new ArrayList<>();

        if (this.can_increment_up_by(d))
            points.add(this.increment_up_by(d));

        if (this.can_increment_down_by(d))
            points.add(this.increment_down_by(d));

        if (this.can_increment_left_by(d))
            points.add(this.increment_left_by(d));

        if (this.can_increment_right_by(d))
            points.add(this.increment_right_by(d));

        return points;
    }

    // the following two methods build a range of coordinates in the corresponding direction (x or y)
    // Example: The range from A0 to A4 is the list {A0,A1,A2,A3,A4}
    // Example The range from A4 to A0 is also the list {A0,A1,A2,A3,A4} because the first element is always the smallest
    private static ArrayList<Coordinate> build_range_x(Coordinate a, Coordinate b) {
        assert equal_y(a,b);

        ArrayList<Coordinate> range = new ArrayList<>();
        range.add(a);

        for (int i = a.x + 1; i < b.x; i++){
            Coordinate c = new Coordinate(a.c, i);
            range.add(c);
        }
        range.add(b);
        return range;
    }

    private static ArrayList<Coordinate> build_range_y(Coordinate a, Coordinate b) {
        assert equal_x(a,b);

        ArrayList<Coordinate> range = new ArrayList<>();
        range.add(a);
        char temp = a.c;

        for (int i = a.y+1; i < b.y; i++) {
            temp += 1;
            Coordinate c = new Coordinate(temp, a.x);
            range.add(c);
        }
        range.add(b);
        return range;
    }

    public static ArrayList<Coordinate> get_range(Coordinate a, Coordinate b) {
        // this method builds the correct range for two given coordinates
        // Example: the range from A0 to A4 is the list {A0,A1,A2,A3,A4}
        // in case of a <=> b the list containing only Coordinate a is returned
        // the coordinates a and b must be on the same x- or y-axis
        assert is_in_line(a,b);

        if (equal(a,b)) {
            ArrayList<Coordinate> range = new ArrayList<>();
            range.add(a);
            return range;
        }
        else if (equal_x(a,b)) {
            if (smaller_y(a,b)) {return build_range_y(a,b);}
            else {return build_range_y(b,a);}
        }
        else {
            if (smaller_x(a,b)) {return build_range_x(a,b);}
            else {return build_range_x(b,a);}
        }
    }
    public static Coordinate random_coordinate(ArrayList<Coordinate> list) {
        // returns a random Coordinate from a non-empty list of Coordinates
        assert list.size() > 0;

        Random rnd = new Random();
        int randomNum = rnd.nextInt(list.size());
        return list.get(randomNum);
    }

    private static Coordinate validate_input(String xy) {
        // checks whether a string represents a valid coordinate or not
        // Example: "A1" is valid, "£!" is not valid
        // String xy can be literally anything (also something like "  ?£d, ,. A_3&")

        try {
            char char_coordinate = xy.charAt(0);
            int int_coordinate = Integer.parseInt(xy.substring(1, 2));

            if (is_valid_coordinate(char_coordinate, int_coordinate) && xy.length() == 2)
                return new Coordinate(char_coordinate, int_coordinate);
            else {
                Display.draw_invalid_coordinate_input();
                return new Coordinate();
            }
        }
        catch (Exception e) {
            Display.draw_invalid_coordinate_input();
            return new Coordinate();
        }
    }

    public static Coordinate input_coordinate() {
        // returns a single Coordinate from an input like "A0"
        // other leading or trailing symbols are not allowed
        // example: "6*" is not a valid input (not a valid coordinate)
        // example: "    A0    " is not a valid coordinate input (leading and trailing symbols)

        String xy = Input.get_user_input_single();
        Coordinate a = validate_input(xy);

        if (a.is_null())
            return input_coordinate();
        else
            return a;
    }

    public static ArrayList<Coordinate> input_list_of_coordinates(int n) {
        // returns a list of n coordinates separated by commas from an input
        // n must be grater than 0
        // other leading or trailing symbols are not allowed
        // example of a valid input for n = 3: "A0,E2,B4"
        // example of an invalid input vor n = 3: "  A0,B$,?3" (invalid characters and leading spaces)

        assert(n > 0);

        String[] s = Input.get_user_input_multiple();

        if (s.length != n) {
            Display.draw_invalid_coordinate_input();
            return Coordinate.input_list_of_coordinates(n);
        }

        else {
            ArrayList<Coordinate> list = new ArrayList<>();

            for (String xy : s) {
                Coordinate a = validate_input(xy);

                if (a.is_null())
                    return input_list_of_coordinates(n);
                else
                    list.add(a);
            }
            return list;
        }
    }


    @Override
    public boolean equals(Object o) {
        // overrides the default equals method in Object
        if (o == this)
            return true;
        else if(!(o instanceof Coordinate))
            return false;
        else
            return equal(this,(Coordinate) o);
    }
}



