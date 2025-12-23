import java.util.Scanner;
import view.ConsoleColors;
import view.GameView;

public class Main {
    public static void main(String[] args) {
    	GameView view = new GameView();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                new controller.GameController().start();
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "   ❌ Error: " + e.getMessage() + ConsoleColors.RESET);
            }

            System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.CYAN + 
                               "●  ●  ●  ●  ●   GAME FINISHED   ●  ●  ●  ●  ●" + ConsoleColors.RESET);
            
            String choice = view.getYesNo(ConsoleColors.GREEN + "Would you like to play another round? (Y/N): " + ConsoleColors.RESET);
            

            if (choice.equals("N")) {
                System.out.println("\n" + ConsoleColors.CYAN + "Thank you for playing BLACK HOLE! ● See you next time! ●" + ConsoleColors.RESET);
                break;
            }
            
            System.out.println("\n" + ConsoleColors.GREEN + "♻️  Resetting board... Getting ready for a new game!" + ConsoleColors.RESET + "\n");
        }
    }
}