package model;

import java.util.List;

public class GameState {
	private List<Player> players;
	private Board board;
	private int maxRange;

	private int round = 1;
	private int turn = 0;
	private int moveNumber = 1;

	public GameState(List<Player> players, Board board, int maxRange) {
		this.players = players;
		this.board = board;
		this.maxRange = maxRange;
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
		this.moveNumber=moveNumber;
	}
	public void nextMoveCycle() {
		if (turn == players.size() - 1) {
			moveNumber++;
		}
	}
}
