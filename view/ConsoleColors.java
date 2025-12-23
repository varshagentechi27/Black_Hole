package view;

public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK_HOLE = "\u001B[1;37;40m"; // White on Black background
//    public static final String RED = "\u001B[31m";
//    public static final String GREEN = "\u001B[32m";
//    public static final String YELLOW = "\u001B[33m";
//    public static final String BLUE = "\u001B[34m"; // to orange
    public static final String ORANGE = "\u001B[38;5;208m";
//    public static final String PURPLE = "\u001B[35m";
//    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";
    
    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String PURPLE = "\u001B[95m";
    public static final String CYAN = "\u001B[96m";
    public static final String WHITE = "\u001B[37m";
    public static final String PINK = "\u001B[38;5;213m";
    public static final String BRIGHT_PINK = "\u001B[38;5;201m";
    public static final String GRAY = "\u001B[38;5;244m";

    private static final String[] PLAYER_COLORS = {ORANGE, YELLOW, BRIGHT_PINK,CYAN, BLUE, GREEN, RED};

    public static String getPlayerColor(int index) {
        return PLAYER_COLORS[index % PLAYER_COLORS.length];
    }
}