package controller;

import model.*;
import view.GameView;
import view.ConsoleColors;
import exception.InvalidPlayerCountException;
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
        
        // Show the legend and the coordinate guide before starting
        state.board.printLegend();
        state.board.printGuide();

        // Announce the players and their colors
        System.out.println(ConsoleColors.BOLD + "PARTICIPATING PLAYERS:" + ConsoleColors.RESET);
        for (Player p : state.players) {
            String color = ConsoleColors.BOLD+ConsoleColors.getPlayerColor(p.getId());
            String type = p.isComputer() ? " (Computer)" : " (Human)";
            System.out.println(" - Player " + color + p.getName() + ConsoleColors.RESET + type);
        }
        
        play(state);
    }

    private void play(GameState state) {
        // Enforce exactly one round
        System.out.println("\n" + ConsoleColors.BOLD+ConsoleColors.YELLOW + "●  ●  ●  THE ROUND BEGINS  ●  ●  ●" + ConsoleColors.RESET + "\n");
        state.turn = 0; 
        state.moveNumber = 1;

        while (!state.board.hasOneEmptyLeft()) {
            state.board.print();
            Player p = state.players.get(state.turn);
            String color = ConsoleColors.BOLD+ConsoleColors.getPlayerColor(p.getId());
            String label = p.getName() + state.moveNumber;

            System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + 
                               " is placing: " + color + "● (" + label + ")" + ConsoleColors.RESET);

            if (p.isComputer()) {
                try { Thread.sleep(700); } catch (Exception e) {}
                autoMove(state, p, label);
            } else {
                while (!userMove(state, p, label));
            }
            
            state.nextMoveCycle();
            state.turn = (state.turn + 1) % state.players.size();
        }

        // --- THE FINALE ---
        state.board.placeBlackHole();
        state.board.print();
        System.out.println("\n" + ConsoleColors.BLACK_HOLE + "  🌌 THE VOID HAS OPENED! 🌌  " + ConsoleColors.RESET);

        Map<String, List<Integer>> scoreMap = state.board.calculateScores();
        System.out.println("\n" + ConsoleColors.BOLD + "--- FINAL SCOREBOARD ---" + ConsoleColors.RESET);
        
        for (Player p : state.players) {
            List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
            int sum = list.stream().mapToInt(Integer::intValue).sum();
            p.addScore(sum);
            String color = ConsoleColors.getPlayerColor(p.getId());
            System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + " : " + list + " = " + sum);
        }

        // Highlight the Winner
        state.players.sort(Comparator.comparingInt(Player::getTotalScore));
        Player winner = state.players.get(0);
        String winColor = ConsoleColors.getPlayerColor(winner.getId());

        System.out.println("\n" + winColor + "╔══════════════════════════════════════════════╗");
        System.out.println("║                                              ║");
        System.out.println("║   " + "\u001B[7m" + "  🏆 THE CHAMPION IS PLAYER " + winner.getName() + " !! 🏆  " + ConsoleColors.RESET + winColor + "   ║");
        System.out.println("║                                              ║");
        System.out.println("╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);
    }

    private boolean userMove(GameState state, Player p, String label) {
        String color = ConsoleColors.BOLD+ConsoleColors.getPlayerColor(p.getId());
        System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + ", enter coordinates:");
        int r = view.getInt("Row: ") - 1;
        int c = view.getInt("Position: ") - 1;
        if (!state.board.place(r, c, label)) {
            System.out.println(ConsoleColors.RED + "   ❌ Cell occupied or invalid! Try again." + ConsoleColors.RESET);
            return false;
        }
        return true;
    }

    private void autoMove(GameState state, Player p, String label) {
        int r, c;
        String color = ConsoleColors.getPlayerColor(p.getId());
        do {
            r = rand.nextInt(state.board.getRows());
            c = rand.nextInt(r + 1);
        } while (!state.board.place(r, c, label));
        System.out.println("Computer " + color + p.getName() + ConsoleColors.RESET + " chose: (" + (r + 1) + "," + (c + 1) + ")");
    }
}