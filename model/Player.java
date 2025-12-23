package model;

public class Player {
    private final String name;
    private final boolean computer;
    private final int id; 
    private int totalScore = 0;

    public Player(String name, boolean computer, int id) {
        this.name = name;
        this.computer = computer;
        this.id = id;
    }

    public String getName() { return name; }
    public boolean isComputer() { return computer; }
    public int getTotalScore() { return totalScore; }
    public int getId() { return id; }

    public void addScore(int score) {
        totalScore += score;
    }
}