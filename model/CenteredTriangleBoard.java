package model;

import view.ConsoleColors;
import java.util.*;

public class CenteredTriangleBoard {

    private final String[][] board;
    private final int rows;
    private int bhRow = -1, bhCol = -1;
    private final Set<String> absorbedCells = new HashSet<>();

    public CenteredTriangleBoard(int rows) {
        this.rows = rows;
        this.board = new String[rows][rows];
    }

    public int getRows() { return rows; }

    private boolean valid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c <= r;
    }

    public boolean place(int r, int c, String val) {
        if (!valid(r, c) || board[r][c] != null) return false;
        board[r][c] = val;
        return true;
    }

    public boolean hasOneEmptyLeft() {
        int empty = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j <= i; j++)
                if (board[i][j] == null) empty++;
        return empty == 1;
    }

    public void placeBlackHole() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= i; j++) {
                if (board[i][j] == null) {
                    board[i][j] = "BH";
                    bhRow = i;
                    bhCol = j;
                    identifyAbsorbedNeighbors();
                    return;
                }
            }
        }
    }

    private void identifyAbsorbedNeighbors() {
        int[][] adj = {
            {bhRow, bhCol - 1}, {bhRow, bhCol + 1},
            {bhRow - 1, bhCol - 1}, {bhRow - 1, bhCol},
            {bhRow + 1, bhCol}, {bhRow + 1, bhCol + 1}
        };
        for (int[] p : adj) {
            int r = p[0], c = p[1];
            if (valid(r, c) && board[r][c] != null && !board[r][c].equals("BH")) {
                absorbedCells.add(r + "," + c);
            }
        }
    }

    public Map<String, List<Integer>> calculateScores() {
        Map<String, List<Integer>> map = new HashMap<>();
        int[][] adj = {
            {bhRow, bhCol - 1}, {bhRow, bhCol + 1},
            {bhRow - 1, bhCol - 1}, {bhRow - 1, bhCol},
            {bhRow + 1, bhCol}, {bhRow + 1, bhCol + 1}
        };
        for (int[] p : adj) {
            int r = p[0], c = p[1];
            if (!valid(r, c) || board[r][c] == null || board[r][c].equals("BH")) continue;
            String player = board[r][c].substring(0, 1);
            int number = Integer.parseInt(board[r][c].substring(1));
            map.putIfAbsent(player, new ArrayList<>());
            map.get(player).add(number);
        }
        return map;
    }

    /**
     * Explains what the colors and icons represent.
     */
    public void printLegend() {
        System.out.println(ConsoleColors.BOLD + "\n--- COLOR LEGEND ---" + ConsoleColors.RESET);
        System.out.println("  ◯      : Empty Cell (Available for placement)");
        System.out.println(ConsoleColors.CYAN + "  ◖ A1 ◗  : Player Token (Active during placement)" + ConsoleColors.RESET);
        System.out.println("\u001B[1;37;40m ◖ BH ◗ " + ConsoleColors.RESET + " : The Black Hole (Appears at the end)");
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN + " ◖A1 ◗ " + ConsoleColors.RESET + "  : Absorbed/Scoring Token (Keeps its color)");
        System.out.println("\u001B[1;37m ◖ A5 ◗ " + ConsoleColors.RESET + "  : Safe/Non-Scoring Token (Turns White)");
        System.out.println("--------------------\n");
    }

    private String formatCell(int r, int c, String content) {
        if (content == null) return "   ◯    ";
        if (content.equals("BH")) return "\u001B[1;37;40m" + " ◖ BH ◗ " + ConsoleColors.RESET;

        String color;
        char pChar = content.charAt(0);
        int pIdx = pChar - 'A';
        
        if (bhRow != -1) {
            if (absorbedCells.contains(r + "," + c)) {
                color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(pIdx);
            } else {
                color = "\u001B[1;37m"; // Turn White
            }
        } else {
            color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(pIdx);
        }
        
        String inner = content.length() == 2 ? " " + content + " " : " " + content;
        return color + " ◖" + inner + "◗ " + ConsoleColors.RESET;
    }

    public void printGuide() {
        System.out.println("\n" + ConsoleColors.BOLD + "POSITION GUIDE (Row, Position):" + ConsoleColors.RESET);
        for (int i = 0; i < rows; i++) {
            System.out.printf("Row %-2d ", i + 1);
            System.out.print(" ".repeat((rows - i - 1) * 4));
            for (int j = 0; j <= i; j++) {
                System.out.printf("[%d,%d]   ", i + 1, j + 1);
            }
            System.out.println("\n");
        }
    }

    public void print() {
        System.out.println("\n" + ConsoleColors.BOLD + "--- CURRENT BOARD ---" + ConsoleColors.RESET);
        for (int i = 0; i < rows; i++) {
            // Row number on the left
            System.out.printf("Row %-2d ", i + 1);
            System.out.print(" ".repeat((rows - i - 1) * 4));
            for (int j = 0; j <= i; j++) {
                System.out.print(formatCell(i, j, board[i][j]));
            }
            System.out.println("\n"); 
        }
    }
}