package view;

import java.util.Scanner;

/**
 * Handles the visual presentation and user prompts for the game.
 * Updated to reflect the 1-round rules and color-coded mechanics.
 */
public class GameView {

    private final Scanner sc = new Scanner(System.in);

    public void welcome() {
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN + "=================================");
        System.out.println("     WELCOME TO BLACK HOLE GAME   ");
        System.out.println("=================================" + ConsoleColors.RESET);
        System.out.println("• " + ConsoleColors.YELLOW + "1 Intense Strategic Round" + ConsoleColors.RESET);
        System.out.println("• " + ConsoleColors.GREEN + "Goal: Have the LOWEST score" + ConsoleColors.RESET + "\n");
        howToPlay();
    }

    private void howToPlay() {
        System.out.println(ConsoleColors.BOLD + "HOW TO PLAY:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE + "1. The board is a " + ConsoleColors.BOLD + "Centered Triangle" + ConsoleColors.RESET + " of empty circles " + ConsoleColors.WHITE + "(◯).");
        System.out.println("2. Players take turns placing numbered tokens (e.g., " + ConsoleColors.CYAN + "A1, B1" + ConsoleColors.RESET + ").");
        System.out.println("3. Use the " + ConsoleColors.CYAN + "Row Number" + ConsoleColors.RESET + " on the left to help identify positions.");
        System.out.println("4. When only " + ConsoleColors.YELLOW + "one empty cell" + ConsoleColors.RESET + " remains, it collapses into a " + ConsoleColors.BOLD + "BLACK HOLE" + ConsoleColors.RESET + ".");
        System.out.println("5. Tokens directly " + ConsoleColors.BOLD + "touching" + ConsoleColors.RESET + " the Black Hole are " + ConsoleColors.RED + "absorbed" + ConsoleColors.RESET + " into your score.");
        System.out.println("6. Absorbed tokens stay " + ConsoleColors.BOLD + "Colored" + ConsoleColors.RESET + ", while safe tokens turn " + ConsoleColors.WHITE + "White" + ConsoleColors.RESET + ".");
        System.out.println("7. The player with the " + ConsoleColors.GREEN + "LOWEST" + ConsoleColors.RESET + " total score wins the match!\n");
    }

    /**
     * Prompts the user for a number with validation.
     * @param msg The message to display.
     * @return The integer entered by the user.
     */
    public int getInt(String msg) {
        System.out.print(ConsoleColors.BOLD + msg + ConsoleColors.RESET);
        while (!sc.hasNextInt()) {
            sc.next(); // Clear invalid input
            System.out.print(ConsoleColors.RED + "❌ Enter a valid number: " + ConsoleColors.RESET);
        }
        return sc.nextInt();
    }

    /**
     * Simple helper for any single digit requirements if needed.
     */
    public int getSingleDigit(String msg) {
        System.out.print(ConsoleColors.BOLD + msg + ConsoleColors.RESET);
        String input = sc.next();
        if (!input.matches("[0-9]+")) {
            System.out.println(ConsoleColors.RED + "❌ Please enter a numeric value." + ConsoleColors.RESET);
            return -1;
        }
        return Integer.parseInt(input);
    }
}