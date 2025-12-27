package view;

public class ConsoleColors {

    //Bold and Reset
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    
    //Text Colors
    public static final String WHITE = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;5;208m";
    public static final String PURPLE = "\u001B[38;5;177m";
    public static final String SKY_BLUE = "\u001B[38;5;45m";
    public static final String RED = "\u001B[38;5;196m";
    public static final String YELLOW = "\u001B[93m";
    public static final String GREEN = "\u001B[38;5;46m";
    
    //Player Colors
    public static final String FLAMINGO_PINK = "\u001B[38;5;212m";
    public static final String GOLDEN_YELLOW = "\u001B[38;5;220m";
    public static final String LIGHT_BLUE = "\u001B[38;5;117m";
    public static final String CORAL_ORANGE = "\u001B[38;5;202m";
    public static final String MINT_GREEN = "\u001B[38;5;120m";
    
    //Block Colors
    public static final String BLACK_HOLE = "\u001B[1;37;40m";
    public static final String WHITE_ON_WATERMELON_RED = "\u001B[37;48;5;203m";
    public static final String BLACK_ON_SOFT_YELLOW = "\u001B[30;103m";
    public static final String BLACK_ON_ORANGE = "\u001B[30;48;5;208m";
    public static final String BLACK_ON_LAVENDER_PASTEL = "\u001B[30;48;2;184;168;241m";
    public static final String BLACK_ON_GREEN = "\u001B[38;2;0;0;0m\u001B[48;2;80;200;120m";
    public static final String WHITE_ON_RED = "\u001B[37;41m";
;



    private static final String[] PLAYER_COLORS = {FLAMINGO_PINK, GOLDEN_YELLOW, LIGHT_BLUE, CORAL_ORANGE, MINT_GREEN};


    public static String getPlayerColor(int index) {
        return PLAYER_COLORS[index % PLAYER_COLORS.length];
    }
}