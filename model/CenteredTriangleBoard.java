package model;

import java.util.*;

public class CenteredTriangleBoard {

    private final String[][] board;
    private final int rows;
    private int bhRow, bhCol;

    public CenteredTriangleBoard(int rows) {
        this.rows = rows;
        board = new String[rows][rows];
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
        for (int i = 0; i < rows; i++)
            for (int j = 0; j <= i; j++)
                if (board[i][j] == null) {
                    board[i][j] = "BH";
                    bhRow = i;
                    bhCol = j;
                    return;
                }
    }

    /* 🔥 FINAL SCORING LOGIC */
    public Map<String, List<Integer>> calculateScores() {

        Map<String, List<Integer>> map = new HashMap<>();

        int[][] adj = {
            {bhRow, bhCol - 1},     // left
            {bhRow, bhCol + 1},     // right
            {bhRow - 1, bhCol - 1}, // up-left
            {bhRow - 1, bhCol},     // up-right
            {bhRow + 1, bhCol},     // down-left
            {bhRow + 1, bhCol + 1}  // down-right
        };

        for (int[] p : adj) {
            int r = p[0], c = p[1];
            if (!valid(r, c)) continue;

            String val = board[r][c];
            if (val == null || val.equals("BH")) continue;

            String player = val.substring(0, 1);
            int number = Integer.parseInt(val.substring(1));

            map.putIfAbsent(player, new ArrayList<>());
            map.get(player).add(number);
        }
        return map;
    }

    public void printGuide() {
        System.out.println("\nMatrix Position Guide:");
        for (int i = 0; i < rows; i++) {
            System.out.print(" ".repeat((rows - i) * 2));
            for (int j = 0; j <= i; j++)
                System.out.print("[" + (i + 1) + "," + (j + 1) + "] ");
            System.out.println();
        }
    }

    public void print() {
        System.out.println("\nMatrix:");
        for (int i = 0; i < rows; i++) {
            System.out.print(" ".repeat((rows - i) * 2));
            for (int j = 0; j <= i; j++)
                System.out.print(board[i][j] == null ? "[ ] " : "[" + board[i][j] + "] ");
            System.out.println();
        }
    }
}
