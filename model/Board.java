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
    
    // Scoring logic
    Map<String, List<Integer>> mapAbsorbedValuesToPlayers();
    
    // Methods needed for the View to display the board
    String getCell(int r, int c);
    Set<String> getAbsorbedCells();
    int getBhRow();
    
    // Validation methods needed for Human input
    void validateRow(int r) throws InvalidMoveException;
    boolean isRowFull(int r);
    
    //Computer's move helper methods
    List<int[]> getAvailableMoves();
    boolean isEdge(int r, int c);
}
