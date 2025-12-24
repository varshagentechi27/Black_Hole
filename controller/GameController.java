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
		view.displayBoard((CenteredTriangleBoard) state.getBoard());
		view.displayMessage("\n●  ●  ●  THE BLACK HOLE HAS FORMED ●  ●  ●");

		Map<String, List<Integer>> scoreMap = state.getBoard().calculateScores();
		for (Player p : state.getPlayers()) {
			List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
			p.addScore(list.stream().mapToInt(Integer::intValue).sum());
		}
		view.displayScoreboard(state.getPlayers(), scoreMap);

		// Find winners
		state.getPlayers().sort(Comparator.comparingInt(Player::getTotalScore));
		int minScore = state.getPlayers().get(0).getTotalScore();
		List<Player> winners = new ArrayList<>();
		for (Player p : state.getPlayers())
			if (p.getTotalScore() == minScore)
				winners.add(p);

		view.displayWinner(winners);
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
			} catch (OccupiedCellException e) {
				view.displayError(e.getMessage());
			}
		}
	}

	private void autoMove(Board board, String label) {
		while (true) {
			int r = rand.nextInt(board.getRows());
			int c = rand.nextInt(r + 1);
			try {
				board.place(r, c, label);
				view.display("Computer chose: (" + (r + 1) + "," + (c + 1) + ")");
				break;
			} catch (Exception e) {
				view.displayError(e.getMessage());
			}
		}
	}
}