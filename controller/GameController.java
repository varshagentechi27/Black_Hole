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
        if (users < 1 || users > 5) {
            throw new InvalidPlayerCountException("Users must be between 1 and 5");
        }

        int actualPlayerCount = (users == 1) ? 2 : users;
        
        int rows = (actualPlayerCount == 2) ? 6 : (actualPlayerCount == 3) ? 7 : (actualPlayerCount == 4) ? 9 : 11;

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < actualPlayerCount; i++) {
            boolean isAI = (users == 1 && i == 1); // Second player is AI in solo mode
            players.add(new Player("" + (char)('A' + i), isAI, i));
        }

        GameState state = new GameState(players, new CenteredTriangleBoard(rows), 10);
        
        state.board.printGuide();
        
        play(state);
    }

    private void play(GameState state) {
        while (state.round <= 1) {
            System.out.println("\n" + ConsoleColors.YELLOW + "●  ●  ●  ●  ●   THE GAME BEGINS   ●  ●  ●  ●  ●" + ConsoleColors.RESET + "\n");
            state.turn = 0; 
            state.moveNumber = 1;

            while (!state.board.hasOneEmptyLeft()) {
                state.board.print();
                Player currentPlayer = state.players.get(state.turn);
                String color = ConsoleColors.getPlayerColor(currentPlayer.getId());
                String tokenLabel = currentPlayer.getName() + state.moveNumber;

                System.out.println("Player " + color + currentPlayer.getName() + ConsoleColors.RESET + 
                                   " is placing token: " + color + "● (" + tokenLabel + ")" + ConsoleColors.RESET);

                if (currentPlayer.isComputer()) {
                    try { Thread.sleep(700); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    autoMove(state, tokenLabel);
                } else {
                    while (!userMove(state, tokenLabel));
                }
                
                state.nextMoveCycle();
                state.turn = (state.turn + 1) % state.players.size();
            }

            state.board.placeBlackHole();
            
            state.board.print();
            System.out.println("\n" + ConsoleColors.BLACK_HOLE + "  ● ● ●  THE BLACK HOLE HAS ARRIVED  ● ● ●  " + ConsoleColors.RESET);

            Map<String, List<Integer>> scoreMap = state.board.calculateScores();
            System.out.println("\n" + ConsoleColors.BOLD + "--- FINAL SCOREBOARD ---" + ConsoleColors.RESET);
            
            for (Player p : state.players) {
                List<Integer> list = scoreMap.getOrDefault(p.getName(), List.of());
                int sum = list.stream().mapToInt(Integer::intValue).sum();
                p.addScore(sum);
                String color = ConsoleColors.getPlayerColor(p.getId());
                
                System.out.println("Player " + color + p.getName() + ConsoleColors.RESET + 
                                   " : " + list + " = " + sum + " | Total = " + p.getTotalScore());
            }
            state.round++;
        }
        
        state.players.sort(Comparator.comparingInt(Player::getTotalScore));
        
        System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN + "●  ●  ●  ●  ●   FINAL STANDINGS   ●  ●  ●  ●  ●" + ConsoleColors.RESET);
        System.out.println("🏆 WINNER: Player " + ConsoleColors.getPlayerColor(state.players.get(0).getId()) + 
                           state.players.get(0).getName() + ConsoleColors.RESET + " 🏆\n");
    }

    private boolean userMove(GameState state, String label) {
        int r = view.getInt("Row: ") - 1;
        int c = view.getInt("Position: ") - 1;
        if (!state.board.place(r, c, label)) {
            System.out.println(ConsoleColors.RED + "   ❌ Cell occupied or invalid! Try again." + ConsoleColors.RESET);
            return false;
        }
        return true;
    }

    private void autoMove(GameState state, String label) {
        int r, c;
        do {
            r = rand.nextInt(state.board.getRows());
            c = rand.nextInt(r + 1);
        } while (!state.board.place(r, c, label));
        System.out.println("Computer placed token at (" + (r + 1) + ", " + (c + 1) + ")");
    }
}