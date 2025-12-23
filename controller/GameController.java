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
        if (users < 1 || users > 5) throw new InvalidPlayerCountException("Users must be 1-5");

        int actual = (users == 1) ? 2 : users;
        int rows = (actual == 2) ? 6 : (actual == 3) ? 7 : (actual == 4) ? 9 : 11;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < actual; i++)
            players.add(new Player("" + (char)('A' + i), users == 1 && i == 1, i));

        GameState state = new GameState(players, new CenteredTriangleBoard(rows), 10);
        
        view.displayLegend();
        view.displayGuide(rows);

//        view.displayMessage("PARTICIPATING PLAYERS:", ConsoleColors.BOLD);
//        for (Player p : state.players) {
//            String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
//            String type = p.isComputer() ? " (Computer)" : " (Human)";
//            view.displayMessage("- Player " + color + p.getName() + ConsoleColors.RESET + type, "");
//        }
        view.displayPlayerList(state.players);
        
        play(state);
    }

    private void play(GameState state) {
//        view.displayMessage("\n●  ●  ●  THE ROUND BEGINS  ●  ●  ●\n", ConsoleColors.BOLD + ConsoleColors.YELLOW);
        view.displayRoundStart();
        state.turn = 0; 
        state.moveNumber = 1;

        while (!state.board.hasOneEmptyLeft()) {
            view.displayBoard(state.board);
            Player p = state.players.get(state.turn);
//            String color = ConsoleColors.BOLD + ConsoleColors.getPlayerColor(p.getId());
            String label = p.getName() + state.moveNumber;

            view.displayTurn(p, label);
            

            if (p.isComputer()) {
                try { Thread.sleep(700); } catch (Exception ignored) {}
                autoMove(state.board, label);
            } else {
                handleUserTurn(state.board, label);
            }
            
            state.nextMoveCycle();
            state.turn = (state.turn + 1) % state.players.size();
        }

        state.board.placeBlackHole();
        view.displayBoard(state.board);
        view.displayMessage("\n●  ●  ●  THE BLACK HOLE HAS ARRIVED  ●  ●  ●");

        Map<String, List<Integer>> scoreMap = state.board.calculateScores();
        for (Player p : state.players) {
            List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
            p.addScore(list.stream().mapToInt(Integer::intValue).sum());
        }
        view.displayScoreboard(state.players, scoreMap);
        
        // Find winners
        state.players.sort(Comparator.comparingInt(Player::getTotalScore));
        int minScore = state.players.get(0).getTotalScore();
        List<Player> winners = new ArrayList<>();
        for (Player p : state.players) if (p.getTotalScore() == minScore) winners.add(p);
        
        view.displayWinner(winners);
    }

    private void handleUserTurn(CenteredTriangleBoard board, String label) {
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
                // e.getMessage() returns the raw player ID (e.g. "B")
                view.displayError("Occupied by Player " + e.getMessage());
            }
        }
    }

    private void autoMove(CenteredTriangleBoard board, String label) {
        while (true) {
            int r = rand.nextInt(board.getRows());
            int c = rand.nextInt(r + 1);
            try {
                board.place(r, c, label);
                view.display("Computer chose: (" + (r + 1) + "," + (c + 1) + ")");
                break;
            } catch (Exception ignored) {}
        }
    }
}