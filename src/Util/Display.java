package Util;
import Attack.attack_result;

import java.util.Objects;

public class Display {
    // a class that handles all the outputs to the terminal
    private static final String letters = "  A B C D E F G H I J";
    private static final String border = " +-+-+-+-+-+-+-+-+-+-+";
    private static final String endline = "=======================";
    private static final String separator = "-----------------------";

    private static void draw_grid(String[][] grid) {
        // a private helper function for drawing a grid
        System.out.println(letters);
        System.out.println(border);

        for (int i = 0; i < grid.length; i++) {
            System.out.print(i);
            System.out.print("|");
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j]);
                System.out.print("|");
            }
            System.out.print(i);
            System.out.println();
        }

        System.out.println(border);
        System.out.println(letters);
        System.out.println(endline);
    }

    public static void draw_ocean(String[][] ocean) {
        // draws the ocean grid
        System.out.println("===== OCEAN GRID =====");
        draw_grid(ocean);
    }

    public static void draw_target(String[][] target) {
        // draws the target grid
        System.out.println("===== TARGET GRID =====");
        draw_grid(target);
    }

    public static void draw_final(String[][] final_grid) {
        // draws the final target grid
        draw_target(final_grid);
    }

    public static void draw_separator() {
        System.out.println();
        System.out.println(separator);
        System.out.println();

    }

    public static void draw_computer_win() {
        // message if ComputerPlayer wins -> HumanPlayer loses
        System.out.println("You have lost!");

    }
    public static void draw_human_win() {
        // message if HumanPlayer wins
        System.out.println("You have won!");

    }
    public static void draw_attack_message_human(attack_result result) {
        // message whether the HumanPlayer hit, missed or sunk a ship
        if (result == attack_result.Sunk)
            System.out.println("You hit and sunk a ship.");

        else if (result == attack_result.Hit)
            System.out.println("You hit a ship.");

        else if (result == attack_result.Miss)
            System.out.println("You missed your shot.");

        else {
            System.out.println("You were caught cheating!!!");
            System.exit(0);
        }
    }

    public static void draw_attack_message_computer(attack_result result) {
        // message whether the ComputerPlayer hit, missed or sunk a ship
        if (result == attack_result.Sunk)
            System.out.println("Computer hit and sunk a ship.");

        else if (result == attack_result.Hit)
            System.out.println("Computer hit a ship.");

        else if (result == attack_result.Miss)
            System.out.println("Computer missed his shot.");

        else {
            System.out.println("You were caught cheating!!!");
            System.exit(0);
        }
    }

    public static void draw_enter_coordinates_for_ship(String ship, int length) {
        // message for which ship type coordinate should be entered
        String coordinates = "";

        if (Objects.equals(ship, "carrier")) {
            coordinates = "A0,F0";
        } else if (Objects.equals(ship, "battleship")) {
            coordinates = "C1,C4";
        } else if (Objects.equals(ship, "submarine")) {
            coordinates = "B9,D9";
        } else if (Objects.equals(ship, "patrol boat")) {
            coordinates = "H6,I6";
        }
        System.out.println("\nExample input format: " + coordinates);
        System.out.println("Enter the coordinates for a " + ship + " with length " + length + ":");
    }

    public static void draw_enter_coordinate_to_attack() {
        /// message for entering a coordinate for an attack
        System.out.println("Enter coordinate to attack:");
    }

    public static void draw_invalid_ship_placement(String ship, int length) {
        // message for invalid coordinates for a ship
        System.out.println("Invalid location for a " + ship + " with length " + length + ".");
    }

    public static void draw_invalid_coordinate_input() {
        // message for an invalid coordinate (like "A$")
        System.out.println("Invalid format of coordinate input.");
    }
}