import view.GameView;
import controller.GameController;

public class Main {
    public static void main(String[] args) {
        GameView view = new GameView();

        while (true) {
            try {
                new GameController().start();
            } catch (Exception e) {
                view.displayError("Fatal Error: " + e.getMessage());
            }

            view.displayRoundOver();
            
            String choice = view.getYesNo(" Would you like to play another round? (Y/N):");

            if (choice.equals("N")) {
                System.out.println();
                view.displayStatus("                        Thank you for playing BLACK HOLE! ● See you next time! ●                          ");
                break;
            }
            
            System.out.println();
            view.displayStatus("                           ♻️ Resetting board... Getting ready for a New Game!                             ");
            System.out.println();
        }
    }
}