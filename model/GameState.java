package model;

import java.util.List;

// like a container for the active game session state.
public class GameState {
	private List<Player> players;
	private Board board;

	// index of the player whose turn it currently is (0 to players.size()-1)
	private int turn = 0;

	// represents the number that appears on the token
	private int moveNumber = 1;

	public GameState(List<Player> players, Board board) {
		this.players = players;
		this.board = board;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Board getBoard() {
		return board;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getMoveNumber() {
		return moveNumber;
	}

	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}

	// move number only increments after ALL players have taken a turn
	public void nextMoveCycle() {
		if (turn == players.size() - 1) {
			moveNumber++;
		}
	}
}
