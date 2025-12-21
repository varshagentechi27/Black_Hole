package model;

import java.util.List;

public class GameState {
    public List<Player> players;
    public CenteredTriangleBoard board;
    public int maxRange;

    public int round = 1;
    public int turn = 0;
    public int moveNumber = 1;

    public GameState(List<Player> players,
                     CenteredTriangleBoard board,
                     int maxRange) {
        this.players = players;
        this.board = board;
        this.maxRange = maxRange;
    }

    public void nextMoveCycle() {
        if (turn == players.size() - 1) {
            moveNumber++;
        }
    }
}
