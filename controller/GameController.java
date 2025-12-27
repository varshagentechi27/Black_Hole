package controller;

import model.*;
import view.GameView;
import exception.*;
import java.util.*;

public class GameController {
	private final GameView view = new GameView();
	private final Random rand = new Random();

	//called via Main method to start
	public void start() throws Exception {
		view.welcome();

		// input handling
		int users = view.getNumberOfUsers();

		// if 1 player, play against the computer (Total 2 players)
		int actual = (users == 1) ? 2 : users;

		int rows = (actual == 2) ? 6 : (actual == 3) ? 7 : (actual == 4) ? 9 : 11;

		List<Player> players = new ArrayList<>();
		for (int i = 0; i < actual; i++) {
			// For single-player mode, the second player (index 1) is the Computer.
			boolean isComp = (users == 1 && i == 1);
			String name = "" + (char) ('A' + i);
			if (isComp) {
				players.add(new ComputerPlayer(name, i)); // polymorphism, Computer is treated as Player (Upcasting)
			} else {
				players.add(new HumanPlayer(name, i));
			}
		}

		Board board = new CenteredTriangleBoard(rows);
		GameState state = new GameState(players, board);

		view.displayLegend();
		view.displayGuide(rows);
		view.displayPlayerList(state.getPlayers());

		// Determine the max token number players will reach
		int playerCount = state.getPlayers().size();
		int maxNum = (playerCount == 1 || playerCount == 2) ? 10
				: (playerCount == 3) ? 9 : (playerCount == 4) ? 11 : 13;

		view.displayMaxNumberNote(maxNum); // display note handled in view

		play(state);
	}

	// Runs until only one empty spot remains, which becomes the Black Hole
	private void play(GameState state) {
		view.displayRoundStart();
		state.setTurn(0);
		state.setMoveNumber(1);

		// until one spot is left
		while (!state.getBoard().hasOneEmptyLeft()) {
			view.displayBoard(state.getBoard());
			Player p = state.getPlayers().get(state.getTurn());
			String label = p.getName() + state.getMoveNumber();

			view.displayTurn(p, label);

			// Determine if the current player is Computer or Human
			if (p instanceof ComputerPlayer) {
				try {
					Thread.sleep(700);
				} catch (Exception e) {
					view.displayError(e.getMessage());
				}
				autoMove(state.getBoard(), label);
			} else {
				handleUserTurn(state.getBoard(), label);
			}

			// Update state
			state.nextMoveCycle();
			state.setTurn((state.getTurn() + 1) % state.getPlayers().size());
		}

		// Black Hole Formation
		state.getBoard().placeBlackHole();
		view.displayBoard(state.getBoard());
		System.out.println();
		view.displayBlackHoleMessage(
				"●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  THE BLACK HOLE HAS FORMED  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ●  ● ");

		processFinalResults(state);
	}

	// Human input loop
	private void handleUserTurn(Board board, String label) {
		boolean success = false;
		while (!success) {
			try {
				int r = view.getInt("Row: ") - 1;
				board.validateRow(r);
				if (board.isRowFull(r)) {
					view.displayError("Row " + (r + 1) + " is full! Please enter another Row Number.");
					continue;
				}
				int c = view.getInt("Position: ") - 1;
				board.place(r, c, label);
				success = true;
			} catch (exception.InvalidMoveException | exception.OccupiedCellException e) {
				view.displayError(e.getMessage());
				view.display("Please enter the Row and Column again.");
			}
		}
	}

	// Computer: Prioritizes edge moves to minimize risk
	private void autoMove(Board board, String label) {
		List<int[]> availableMoves = board.getAvailableMoves();
		if (availableMoves.isEmpty())
			return;

		// Strategy: Favor edges/corners.
		List<int[]> strategicMoves = new ArrayList<>();
		for (int[] move : availableMoves) {
			if (board.isEdge(move[0], move[1]))
				strategicMoves.add(move);
		}

		// random move if no strategic moves are available
		int[] choice = strategicMoves.isEmpty() ? availableMoves.get(rand.nextInt(availableMoves.size()))
				: strategicMoves.get(rand.nextInt(strategicMoves.size()));

		try {
			board.place(choice[0], choice[1], label);
			view.display("Computer chose: (" + (choice[0] + 1) + "," + (choice[1] + 1) + ")");
		} catch (Exception e) {
			view.displayError(e.getMessage());
		}
	}

	// Scoring
	private void processFinalResults(GameState state) {

		// scores calculation based on tokens adjacent to the Black Hole
		Map<String, List<Integer>> scoreMap = state.getBoard().calculateScores();

		// Accumulate scores for each player
		for (Player p : state.getPlayers()) {
			List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
			int sum = 0;
			for (int score : list)
				sum += score;
			p.addScore(sum);
		}
		view.displayScoreboard(state.getPlayers(), scoreMap);

		// Sort players to find the lowest score (the winner)
		List<Player> tempPlayers = new ArrayList<>(state.getPlayers());
		tempPlayers.sort(Comparator.comparingInt(Player::getTotalScore));
		int minScore = tempPlayers.get(0).getTotalScore();

		// Handle ties
		List<Player> winners = new ArrayList<>();
		for (Player p : state.getPlayers()) {
			if (p.getTotalScore() == minScore)
				winners.add(p);
		}

		view.displayWinner(winners);
	}
}