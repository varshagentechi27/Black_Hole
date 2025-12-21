package controller;

import model.*;
import view.GameView;
import exception.InvalidPlayerCountException;

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

        int rows, range;
        if (actual == 2) { rows = 6; range = 10; }
        else if (actual == 3) { rows = 7; range = 9; }
        else if (actual == 4) { rows = 9; range = 11; }
        else { rows = 11; range = 13; }

        System.out.println("\nFill numbers sequentially from 1 to " + range);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < actual; i++)
            players.add(new Player("" + (char)('A' + i), users == 1 && i == 1));

        GameState state = new GameState(players, new CenteredTriangleBoard(rows), range);
        state.board.printGuide();

        play(state);
    }

    private void play(GameState state) {

        while (state.round <= 3) {

            System.out.println("\n===== ROUND " + state.round + " =====");
            state.turn = 0;
            state.moveNumber = 1;

            while (!state.board.hasOneEmptyLeft()) {

                state.board.print();
                Player p = state.players.get(state.turn);

                String label = p.getName() + state.moveNumber;
                System.out.println("Player " + p.getName() + " placing: " + label);

                boolean placed = p.isComputer()
                        ? autoMove(state, p)
                        : userMove(state, p);

                if (placed) {
                    state.nextMoveCycle();
                    state.turn = (state.turn + 1) % state.players.size();
                }
            }

            state.board.placeBlackHole();
            state.board.print();

            Map<String, List<Integer>> scoreMap = state.board.calculateScores();

            System.out.println("\nCurrent Round Score:");
            for (Player p : state.players) {
                List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                p.addScore(sum);

                System.out.println("Player " + p.getName() +
                        " : " + list + " = " + sum +
                        " | Total = " + p.getTotalScore());
            }

            state.board = new CenteredTriangleBoard(state.board.getRows());
            state.round++;
        }

        state.players.sort(Comparator.comparingInt(Player::getTotalScore));
        System.out.println("\n🏆 WINNER: Player " + state.players.get(0).getName());
    }

    private boolean userMove(GameState state, Player p) {
        int r = view.getInt("Row: ") - 1;
        int c = view.getInt("Position: ") - 1;
        return state.board.place(r, c, p.getName() + state.moveNumber);
    }

    private boolean autoMove(GameState state, Player p) {
        int r, c;
        do {
            r = rand.nextInt(state.board.getRows());
            c = rand.nextInt(r + 1);
        } while (!state.board.place(r, c, p.getName() + state.moveNumber));
        return true;
    }
}
