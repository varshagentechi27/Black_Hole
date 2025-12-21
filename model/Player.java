package model;

public class Player {
    private final String name;
    private final boolean computer;
    private int totalScore = 0;

    public Player(String name, boolean computer) {
        this.name = name;
        this.computer = computer;
    }

    public String getName() { return name; }
    public boolean isComputer() { return computer; }
    public int getTotalScore() { return totalScore; }

    public void addScore(int score) {
        totalScore += score;
    }
}
