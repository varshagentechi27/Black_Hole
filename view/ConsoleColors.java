package view;

public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    
    public static final String BLUE = "\u001B[94m";
    public static final String SKY_BLUE = "\u001B[38;5;45m";
    public static final String WHITE = "\u001B[37m";
    public static final String PINK = "\u001B[38;5;213m";
    public static final String BRIGHT_PINK = "\u001B[38;5;201m";
    public static final String GRAY = "\u001B[38;5;244m";
    // public static final String WHITE_ON_FLAMINGO_PINK = "\u001B[48;5;212m";
    
    //First Page Color
    public static final String BLACK_HOLE = "\u001B[1;37;40m";
    public static final String WHITE_ON_WATERMELON_RED = "\u001B[37;48;5;203m";
    public static final String WHITE_ON_SOFT_YELLOW = "\u001B[30;103m";

    public static final String PURPLE = "\u001B[38;5;177m";
    public static final String RED = "\u001B[38;5;196m";
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String YELLOW = "\u001B[93m";
    public static final String GREEN = "\u001B[38;5;46m";
    
    //Player Colors
    public static final String FLAMINGO_PINK = "\u001B[38;5;212m";
    public static final String GOLDEN_YELLOW = "\u001B[38;5;220m";
    public static final String LIGHT_BLUE = "\u001B[38;5;117m";
    public static final String CORAL_ORANGE = "\u001B[38;5;202m";
    public static final String MINT_GREEN = "\u001B[38;5;120m";
    
    //Block Colors
    public static final String WHITE_ON_ORANGE = "\u001B[37;48;5;208m";
    public static final String WHITE_ON_LAVENDER_PASTEL = "\u001B[1;97;48;2;184;168;241m";
    public static final String BLACK_ON_PALE_BLUE = "\u001B[30;48;5;153m";

    //GameController me
    public static final String WHITE_ON_BABY_PINK = "\u001B[37;48;5;218m";
    
    //start game
    public static final String PASTEL_GREEN = "\u001B[92m";
    public static final String BLACK_ON_GREEN = "\u001B[38;2;0;0;0m\u001B[48;2;80;200;120m";





    


    private static final String[] PLAYER_COLORS = {FLAMINGO_PINK, GOLDEN_YELLOW, LIGHT_BLUE, CORAL_ORANGE, MINT_GREEN, GREEN, RED};



    //private static final String[] PLAYER_COLORS = {ORANGE, YELLOW, BRIGHT_PINK,CYAN, BLUE, GREEN, RED};

    public static String getPlayerColor(int index) {
        return PLAYER_COLORS[index % PLAYER_COLORS.length];
    }
}