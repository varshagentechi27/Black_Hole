package view;

import java.util.Scanner;

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

    public int getInt(String msg) {
        System.out.print(ConsoleColors.BOLD + msg + ConsoleColors.RESET);
        while (!sc.hasNextInt()) {
        	String invalid = sc.next(); // invalid input
            System.out.println(ConsoleColors.RED + "   ❌ '" + invalid + "' is not a number! Please enter a valid numeric value." + ConsoleColors.RESET);
        }
        return sc.nextInt();
    }
    
    public String getYesNo(String msg) {
        while (true) {
            System.out.print(ConsoleColors.YELLOW + msg + ConsoleColors.RESET);
            String input = sc.next().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                return input;
            }
            System.out.println(ConsoleColors.RED + "   ⚠️ Invalid input! Please enter 'Y' for Yes or 'N' for No." + ConsoleColors.RESET);
        }
    }
    
}