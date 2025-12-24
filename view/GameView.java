package view;

import java.util.Scanner;

import model.Board;
import model.Player;
import java.util.*;

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
    
    public void displayRoundStart() {
        System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.YELLOW + "●  ●  ●  THE ROUND BEGINS  ●  ●  ●" + ConsoleColors.RESET + "\n");
    }
    
    public void displayRoundOver() {
    	 System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN+ "●  ●  ●  ●  ●   GAME FINISHED   ●  ●  ●  ●  ●"+ ConsoleColors.RESET + "\n");
    }
    
    public void displayPlayerList(List<Player> players) {
        System.out.println(ConsoleColors.BOLD + "PARTICIPATING PLAYERS:" + ConsoleColors.RESET);
        for (Player p : players) {
            String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
            String type = p.isComputer() ? " (Computer)" : " (Human)";
            System.out.println(" - Player " + color + p.getName() + ConsoleColors.RESET + type);
        }
    }
    
    public void displayError(String msg) {
        System.out.println(ConsoleColors.RED + "   ❌ " + msg + ConsoleColors.RESET);
    }
    
    public void displayTurn(Player p, String label) {
        String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
        System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + 
                           " is placing: " + color + "● (" + label + ")" + ConsoleColors.RESET);
    }
    
    public void displayStatus(String msg) {
        System.out.println(ConsoleColors.GREEN+ msg + ConsoleColors.RESET);
    }
    
    public void display(String msg) {
    	System.out.println(ConsoleColors.BOLD + msg + ConsoleColors.RESET);
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

    public void displayMessage(String msg) {
        System.out.println(ConsoleColors.BLACK_HOLE + msg + ConsoleColors.RESET);
    }

    public void displayLegend() {
        System.out.println(ConsoleColors.BOLD + "\n--- COLOR LEGEND ---" + ConsoleColors.RESET);
        System.out.println("    ◯     : Empty Cell (Available for placement)");
        System.out.println(ConsoleColors.CYAN + " ◖ A1 ◗   : Active Token (Active during placement)" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_HOLE + " ◖ BH ◗ " + ConsoleColors.RESET + "  : The Black Hole (Appears at the end)");
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN + " ◖ A1 ◗ " + ConsoleColors.RESET + "  : Absorbed Token (Keeps color)");
        System.out.println(ConsoleColors.WHITE + " ◖ A5 ◗ " + ConsoleColors.RESET + "  : Safe Token (Turns White)");
        System.out.println("--------------------\n");
    }

    public void displayGuide(int rows) {
        System.out.println(ConsoleColors.BOLD + "POSITION GUIDE (Row, Position):" + ConsoleColors.RESET);
        for (int i = 0; i < rows; i++) {
            System.out.printf("Row %-2d ", i + 1);
            System.out.print(" ".repeat((rows - i - 1) * 4));
            for (int j = 0; j <= i; j++) System.out.printf("[%d,%d]   ", i + 1, j + 1);
            System.out.println("\n");
        }
    }

    public void displayBoard(Board board) {
        String[][] data = board.getBoardArray();
        int rows = board.getRows();
        Set<String> absorbed = board.getAbsorbedCells();
        int bhRow = board.getBhRow();

        System.out.println("\n" + ConsoleColors.BOLD + "--- CURRENT BOARD ---" + ConsoleColors.RESET);
        for (int i = 0; i < rows; i++) {
            System.out.printf("Row %-2d ", i + 1);
            System.out.print(" ".repeat((rows - i - 1) * 4));
            for (int j = 0; j <= i; j++) {
                System.out.print(formatCell(i, j, data[i][j], absorbed, bhRow));
            }
            System.out.println("\n");
        }
    }

    private String formatCell(int r, int c, String content, Set<String> absorbed, int bhRow) {
        if (content == null) return "   ◯    ";
        if (content.equals("BH")) return ConsoleColors.BLACK_HOLE + " ◖ BH ◗ " + ConsoleColors.RESET;

        String color;
        int pIdx = content.charAt(0) - 'A';
        if (bhRow != -1) {
            color = absorbed.contains(r + "," + c) ? ConsoleColors.BOLD + ConsoleColors.getPlayerColor(pIdx) : ConsoleColors.BOLD + ConsoleColors.WHITE;
        } else {
            color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(pIdx);
        }
        String inner = content.length() == 2 ? " " + content + " " : " " + content;
        return color + " ◖" + inner + "◗ " + ConsoleColors.RESET;
    }

    public void displayScoreboard(List<Player> players, Map<String, List<Integer>> scoreMap) {
        System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.WHITE+ "--- FINAL SCOREBOARD ---");
        for (Player p : players) {
            List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
            int sum = list.stream().mapToInt(Integer::intValue).sum();
            String color = ConsoleColors.getPlayerColor(p.getId());
            System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + " : " + list + " = " + sum);
        }
    }

    public void displayWinner(List<Player> winners) {
        if (winners.size() > 1) {
            System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN + "╔══════════════════════════════════════════════╗");
            System.out.print("║   " + "\u001B[7m" + "  🤝 IT'S A DRAW BETWEEN: ");
            for (int i = 0; i < winners.size(); i++) {
                System.out.print(winners.get(i).getName() + (i < winners.size() - 1 ? ", " : ""));
            }
            System.out.println("  🤝  " + ConsoleColors.RESET + ConsoleColors.CYAN + "   ║\n" + "╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);
        } else {
            Player winner = winners.get(0);
            String winColor = ConsoleColors.getPlayerColor(winner.getId());
            System.out.println("\n" + winColor + "╔══════════════════════════════════════════════╗");
            System.out.println("║                                              ║");
            System.out.println("║   " + "\u001B[7m" + " 🏆 PLAYER " + winner.getName() + " WINS THE GAME!! 🏆  " + ConsoleColors.RESET + winColor + "   ║");
            System.out.println("║                                              ║");
            System.out.println("╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);
        }
    }
    
    public int getInt(String msg) {
        display(msg);
        while (!sc.hasNextInt()) {
        	String invalid = sc.next(); // invalid input
            displayError(invalid + "' is not a number! Please enter a valid numeric value.");
        }
        return sc.nextInt();
    }
    
    public String getYesNo(String msg) {
        while (true) {
            System.out.print(ConsoleColors.GREEN + msg + ConsoleColors.RESET);
            String input = sc.next().trim().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                return input;
            }
            displayError("   ⚠️ Invalid input! Please enter 'Y' for Yes or 'N' for No.");
        }
    }
}
