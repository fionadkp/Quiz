package org.example.object;

public class Player {
    String playerName;
    int points;
    double time;

    public Player(String name, int points, double time) {
        this.playerName = name;
        this.points = points;
        this.time = time;
    }

    public Player() {}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + playerName + '\'' +
                ", points=" + points +
                ", time=" + time +
                '}';
    }
}