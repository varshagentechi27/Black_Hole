package controller;

import model.*;
import view.GameView;
import view.ConsoleColors;
import exception.InvalidPlayerCountException;
import exception.InvalidMoveException;
import exception.OccupiedCellException;
import java.util.*;

public class GameController {
    private final GameView view = new GameView();
    private final Random rand = new Random();

    public void start() throws Exception {
        view.welcome();
        int users = view.getInt("Enter number of users (1-5): ");
        if (users < 1 || users > 5) throw new InvalidPlayerCountException("Users must be 1-5");

        int actual = (users == 1) ? 2 : users;
        int rows = (actual == 2) ? 6 : (actual == 3) ? 7 : (actual == 4) ? 9 : 11;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < actual; i++)
            players.add(new Player("" + (char)('A' + i), users == 1 && i == 1, i));

        GameState state = new GameState(players, new CenteredTriangleBoard(rows), 10);
        
        state.board.printLegend();
        state.board.printGuide();

        System.out.println(ConsoleColors.BOLD + "PARTICIPATING PLAYERS:" + ConsoleColors.RESET);
        for (Player p : state.players) {
            String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
            String type = p.isComputer() ? " (Computer)" : " (Human)";
            System.out.println(" - Player " + color + p.getName() + ConsoleColors.RESET + type);
        }
        
        play(state);
    }

	private void play(GameState state) {
		System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.YELLOW + "●  ●  ●  THE ROUND BEGINS  ●  ●  ●"
				+ ConsoleColors.RESET + "\n");
		state.turn = 0;
		state.moveNumber = 1;

		while (!state.board.hasOneEmptyLeft()) {
			state.board.print();
			Player p = state.players.get(state.turn);
			String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
			String label = p.getName() + state.moveNumber;

			System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + " is placing: " + color + "● ("
					+ label + ")" + ConsoleColors.RESET);

			if (p.isComputer()) {
				try {
					Thread.sleep(700);
				} catch (Exception e) {
				}
				autoMove(state, label);
			} else {
				boolean moveSuccess = false;
				while (!moveSuccess) {
					try {
						int r = view.getInt("Row: ") - 1;
						state.board.validateRow(r);

						if (state.board.isRowFull(r)) {
							System.out.println(ConsoleColors.RED + "   ❌ Row " + (r + 1)
									+ " is completely full! Please enter the another Row."
									+ ConsoleColors.RESET);
							continue;
						}

						int c = view.getInt("Position: ") - 1;
						state.board.place(r, c, label);
						moveSuccess = true;

					} catch (InvalidMoveException | OccupiedCellException e) {
						System.out.println(ConsoleColors.RED + "   ❌ " + e.getMessage() + ConsoleColors.RESET);
						System.out.println("Please enter both Row and Position again.");
					}
				}
			}

			state.nextMoveCycle();
			state.turn = (state.turn + 1) % state.players.size();
		}

		state.board.placeBlackHole();
		state.board.print();
		System.out.println(
				"\n" + "\u001B[1;37;40m" + " ●  ●  ●  THE BLACK HOLE HAS ARRIVED  ●  ●  ● " + ConsoleColors.RESET);

		Map<String, List<Integer>> scoreMap = state.board.calculateScores();
		System.out.println("\n" + ConsoleColors.BOLD + "--- FINAL SCOREBOARD ---" + ConsoleColors.RESET);

		for (Player p : state.players) {
			List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
			int sum = list.stream().mapToInt(Integer::intValue).sum();
			p.addScore(sum);
			String color = ConsoleColors.getPlayerColor(p.getId());
			System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + " : " + list + " = " + sum);
		}

		// Winner Calculation with Draw Logic
		state.players.sort(Comparator.comparingInt(Player::getTotalScore));
		int winningScore = state.players.get(0).getTotalScore();

		List<Player> winners = new ArrayList<>();
		for (Player p : state.players) {
			if (p.getTotalScore() == winningScore) {
				winners.add(p);
			}
		}

		if (winners.size() > 1) {
			// Draw Message
			System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN
					+ "╔══════════════════════════════════════════════╗");
			System.out.print("║   " + "\u001B[7m" + "  🤝 IT'S A DRAW BETWEEN: ");
			for (int i = 0; i < winners.size(); i++) {
				Player w = winners.get(i);
				System.out.print(w.getName() + (i < winners.size() - 1 ? ", " : ""));
			}
			System.out.println("  🤝  " + ConsoleColors.RESET + ConsoleColors.CYAN + "   ║\n"
					+ "╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);
		} else {

			// Display Champion Box for single winner
			Player winner = winners.get(0);
			String winColor = ConsoleColors.getPlayerColor(winner.getId());
			System.out.println("\n" + winColor + "╔══════════════════════════════════════════════╗");
			System.out.println("║                                              ║");
			System.out.println("║   " + "\u001B[7m" + " 🏆 PLAYER " + winner.getName() + " WINS THE GAME!! 🏆  "
					+ ConsoleColors.RESET + winColor + "   ║");
			System.out.println("║                                              ║");
			System.out.println("╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);
		}
	}

    private void autoMove(GameState state, String label) {
        while (true) {
            try {
                int r = rand.nextInt(state.board.getRows());
                int c = rand.nextInt(r + 1);
                state.board.place(r, c, label);
                System.out.println("Computer chose: (" + (r + 1) + "," + (c + 1) + ")");
                break;
            } catch (Exception ignored) {}
        }
    }
}