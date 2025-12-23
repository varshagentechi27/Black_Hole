import java.util.Scanner;
import view.ConsoleColors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                // Starts the game controller
                new controller.GameController().start();
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "   ❌ Error: " + e.getMessage() + ConsoleColors.RESET);
            }

            // Game Finished Message
            System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN + 
                               "●  ●  ●  ●  ●   GAME FINISHED   ●  ●  ●  ●  ●" + ConsoleColors.RESET);
            
            System.out.print(ConsoleColors.YELLOW + "Would you like to play another round? (Y/N): " + ConsoleColors.RESET);
            String choice = sc.next().trim().toUpperCase();

            // Validation loop
            while (!choice.equals("Y") && !choice.equals("N")) {
                System.out.print(ConsoleColors.RED + "   ⚠️  Please enter only Y or N: " + ConsoleColors.RESET);
                choice = sc.next().trim().toUpperCase();
            }

            if (choice.equals("N")) {
                System.out.println("\n" + ConsoleColors.CYAN + "Thank you for playing BLACK HOLE! ● See you next time! ●" + ConsoleColors.RESET);
                break;
            }
            
            // Visual indicator for restarting
            System.out.println("\n" + ConsoleColors.GREEN + "♻️  Resetting board... Getting ready for a new game!" + ConsoleColors.RESET + "\n");
        }
    }
}