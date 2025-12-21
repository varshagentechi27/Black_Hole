package model;

import java.io.Serializable;
import java.util.Random;

public class TriangularBoard implements Serializable {

    private Integer[][] board;
    private int rows;
    private int bhRow = -1, bhCol = -1;

    public TriangularBoard(int rows) {
        this.rows = rows;
        board = new Integer[rows][rows];
    }

    public int getRows() {
        return rows;
    }

    // Only allow triangular placement
    public boolean isValidCell(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c <= r;
    }

    public boolean place(int r, int c, int val) {
        if (!isValidCell(r, c)) return false;
        if (board[r][c] != null) return false;
        board[r][c] = val;
        return true;
    }

    public void printBoard() {
        System.out.println("\nMatrix View:");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j <= i; j++) {
                if (board[i][j] == null) System.out.print(". ");
                else if (board[i][j] == -1) System.out.print("BH ");
                else System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean hasEmptyCell() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j <= i; j++)
                if (board[i][j] == null)
                    return true;
        return false;
    }

    public void createBlackHole() {
        Random rand = new Random();
        while (true) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(r + 1);
            if (board[r][c] == null) {
                board[r][c] = -1;
                bhRow = r;
                bhCol = c;
                break;
            }
        }
    }

    public int calculateScore() {
        int sum = 0;
        int[][] dirs = {
            {-1,0},{1,0},{0,-1},{0,1},
            {-2,0},{2,0}
        };

        for (int[] d : dirs) {
            int nr = bhRow + d[0];
            int nc = bhCol + d[1];
            if (isValidCell(nr, nc) && board[nr][nc] != null && board[nr][nc] != -1)
                sum += board[nr][nc];
        }
        return sum;
    }
}
