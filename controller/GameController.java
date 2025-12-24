package controller;

import model.*;
import view.GameView;
import exception.*;
import java.util.*;

public class GameController {
	private final GameView view = new GameView();
	private final Random rand = new Random();

	public void start() throws Exception {
		view.welcome();
		int users = view.getInt("Enter number of users (1-5): ");
		if (users < 1 || users > 5)
			throw new InvalidPlayerCountException("Users must be 1-5");

		int actual = (users == 1) ? 2 : users;
		int rows = (actual == 2) ? 6 : (actual == 3) ? 7 : (actual == 4) ? 9 : 11;

		List<Player> players = new ArrayList<>();
		for (int i = 0; i < actual; i++)
			players.add(new Player("" + (char) ('A' + i), users == 1 && i == 1, i));

		Board board = new CenteredTriangleBoard(rows);
		GameState state = new GameState(players, new CenteredTriangleBoard(rows), 10);

		view.displayLegend();
		view.displayGuide(rows);
		view.displayPlayerList(state.getPlayers());

		play(state);
	}

	private void play(GameState state) {
		view.displayRoundStart();
		state.setTurn(0);
		state.setMoveNumber(1);

		while (!state.getBoard().hasOneEmptyLeft()) {
			view.displayBoard((CenteredTriangleBoard) state.getBoard());
			Player p = state.getPlayers().get(state.getTurn());
			String label = p.getName() + state.getMoveNumber();

			view.displayTurn(p, label);

			if (p.isComputer()) {
				try {
					Thread.sleep(700);
				} catch (Exception e) {
					view.displayError(e.getMessage());
				}
				autoMove(state.getBoard(), label);
			} else {
				handleUserTurn(state.getBoard(), label);
			}

			state.nextMoveCycle();
			state.setTurn((state.getTurn() + 1) % state.getPlayers().size());
		}

		state.getBoard().placeBlackHole();
		view.displayBoard(state.getBoard());
		view.displayBlackHoleMessage("\n●  ●  ●  THE BLACK HOLE HAS FORMED ●  ●  ●");

		// Find winners
		processFinalResults(state);
	}

	private void handleUserTurn(Board board, String label) {
		boolean success = false;
		while (!success) {
			try {
				int r = view.getInt("Row: ") - 1;
				board.validateRow(r);
				if (board.isRowFull(r)) {
					view.displayError("Row " + (r + 1) + " is full! Please enter another Row number.");
					continue;
				}
				int c = view.getInt("Position: ") - 1;
				board.place(r, c, label);
				success = true;
			} catch (InvalidMoveException e) {
				view.displayError(e.getMessage());
				view.display("Please enter the Row and Coloumn again.");
			} catch (OccupiedCellException e) {
				view.displayError(e.getMessage());
				view.display("Please enter the Row and Coloumn again.");
			}
		}
	}

	private void autoMove(Board board, String label) {
		List<int[]> availableMoves = board.getAvailableMoves();
		if (availableMoves.isEmpty()) return;

		// Strategy: Favor edges/corners to reduce the chance of being absorbed by BH
		List<int[]> strategicMoves = new ArrayList<>();
		for (int[] move : availableMoves) {
			if (board.isEdge(move[0], move[1])) {
				strategicMoves.add(move);
			}
		}

		// Select from strategy if possible, otherwise any valid move
		int[] choice = strategicMoves.isEmpty() ? 
					   availableMoves.get(rand.nextInt(availableMoves.size())) : 
					   strategicMoves.get(rand.nextInt(strategicMoves.size()));

		try {
			board.place(choice[0], choice[1], label);
			view.display("Computer chose: (" + (choice[0] + 1) + "," + (choice[1] + 1) + ")");
		} catch (Exception e) {
			view.displayError(e.getMessage());
		}
	}
	
	private void processFinalResults(GameState state) {
		Map<String, List<Integer>> scoreMap = state.getBoard().calculateScores();
		for (Player p : state.getPlayers()) {
			List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
			int sum = 0;
			for (int score : list) {
				sum += score;
			}
			p.addScore(sum);
		}
		view.displayScoreboard(state.getPlayers(), scoreMap);

		List<Player> tempPlayers = new ArrayList<>(state.getPlayers());
		tempPlayers.sort(Comparator.comparingInt(Player::getTotalScore));
		int minScore = tempPlayers.get(0).getTotalScore();
		
		List<Player> winners = new ArrayList<>();
		for (Player p : state.getPlayers()) {
			if (p.getTotalScore() == minScore) {
				winners.add(p);
			}
		}

		view.displayWinner(winners);
	}
}