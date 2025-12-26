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

			// Ask user if they want to play another round (returns true/false based on
			// input)
			if (!view.askToPlayAgain()) {
				view.displayExitMessage();
				break;
			}

			view.displayResetMessage();
			System.out.println();
		}
	}
}