import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                new controller.GameController().start();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nDo you want to play again? (Y/N): ");
            String choice = sc.next().trim().toUpperCase();

            while (!choice.equals("Y") && !choice.equals("N")) {
                System.out.print("Enter only Y or N: ");
                choice = sc.next().trim().toUpperCase();
            }

            if (choice.equals("N")) {
                System.out.println("\nThank you for playing BLACK HOLE GAME 🎮");
                break;
            }
        }
    }
}
