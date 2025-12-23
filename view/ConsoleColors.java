package view;

public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK_HOLE = "\u001B[1;37;40m"; // White on Black background
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m"; // to orange
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    private static final String[] PLAYER_COLORS = {BLUE,  YELLOW, PURPLE, CYAN, GREEN,RED};

    public static String getPlayerColor(int index) {
        return PLAYER_COLORS[index % PLAYER_COLORS.length];
    }
}