package model;
import java.util.*;

import exception.InvalidMoveException;
import exception.OccupiedCellException;

public class CenteredTriangleBoard implements Board{

    private final String[][] board;
    private final int rows;
    private int bhRow = -1, bhCol = -1;
    private final Set<String> absorbedCells = new HashSet<>();

    public CenteredTriangleBoard(int rows) {
        this.rows = rows;
        this.board = new String[rows][rows];
    }

    public int getRows() { return rows; }
    public String[][] getBoardArray() { return board; }
    public int getBhRow() { return bhRow; }
    public Set<String> getAbsorbedCells() { return absorbedCells; }
    
    private boolean valid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c <= r;
    }
    
    public boolean isRowFull(int r) {
        if (r < 0 || r >= rows) return true;
        for (int j = 0; j <= r; j++) {
            if (board[r][j] == null) return false;
        }
        return true;
    }
    
    public void validateRow(int r) throws InvalidMoveException {
    	 if (r < 0 || r >= rows) {
             throw new InvalidMoveException("Invalid Row! Please enter a row from 1 to " + rows + ".");
         }
	}

    public void place(int r, int c, String val) throws InvalidMoveException, OccupiedCellException {
         if (c < 0 || c > r) {
//             throw new InvalidMoveException("Invalid Position! For Row " + (r + 1) + ", please enter a position from 1 to " + (r + 1) + ".");
        	 throw new InvalidMoveException("Invalid Position! Please refer to the Position Guide above for valid Positions.");
        	 
         }

         // Edge Case: Cell already occupied
         if (board[r][c] != null) {
             throw new OccupiedCellException("This cell is already occupied by Player " + board[r][c].charAt(0) + "! Please choose an empty circle (◯).");
         }

         board[r][c] = val;
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

  
	
}