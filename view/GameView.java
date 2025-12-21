package view;

import java.util.Scanner;

public class GameView {

    private final Scanner sc = new Scanner(System.in);

    public void welcome() {
        System.out.println("=================================");
        System.out.println("     WELCOME TO BLACK HOLE GAME   ");
        System.out.println("=================================");
        System.out.println("• 3 Rounds");
        System.out.println("• Lowest total score wins\n");
        howToPlay();
    }

    private void howToPlay() {
        System.out.println("HOW TO PLAY:");
        System.out.println("1. The matrix is a centred triangle.");
        System.out.println("2. Players play in order: A1, B1, C1, A2...");
        System.out.println("3. Enter ROW and POSITION (single digit only).");
        System.out.println("4. One empty cell becomes a BLACK HOLE.");
        System.out.println("5. Adjacent values around BH give score.");
        System.out.println("6. Lowest score after 3 rounds wins.\n");
    }

    public int getSingleDigit(String msg) {
        System.out.print(msg);
        String input = sc.next();
        if (!input.matches("[1-9]")) {
            System.out.println("❌ Enter a single digit (1–9 only)");
            return -1;
        }
        return Integer.parseInt(input);
    }

    public int getInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter valid number: ");
        }
        return sc.nextInt();
    }
}
