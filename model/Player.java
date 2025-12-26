package model;

public class Player {
	private final String name;
	private final int id;
	private int totalScore = 0;

	public Player(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getId() {
		return id;
	}

	public void addScore(int score) {
		totalScore += score;
	}
}