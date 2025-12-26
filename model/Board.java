package model;
import java.util.List;
import java.util.Map;
import java.util.Set;
import exception.InvalidMoveException;
import exception.OccupiedCellException;

public interface Board {
	int getRows();
    boolean hasOneEmptyLeft();
    void place(int r, int c, String val) throws InvalidMoveException, OccupiedCellException;
    void placeBlackHole();
    Map<String, List<Integer>> calculateScores();
    
    // Methods needed for the View to display the board
    //String[][] getBoardArray();
    String getCell(int r, int c);
    Set<String> getAbsorbedCells();
    int getBhRow();
    
    // Validation methods needed for Human input
    void validateRow(int r) throws InvalidMoveException;
    boolean isRowFull(int r);
    
    List<int[]> getAvailableMoves();
    boolean isEdge(int r, int c);
}
