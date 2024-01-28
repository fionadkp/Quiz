package org.example;

import org.example.object.Player;

import javax.swing.*;
import java.util.List;

public class GameGUI {
    private JFrame frame;
    private JTextArea outputArea;

    public GameGUI() {
        frame = new JFrame("Highscores");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);
    }

    public void displayHighscore(String name, double time, int points, List<Player> topPlayers) {
        StringBuilder message = new StringBuilder("Congratulations " + name + "! You got " + points + " points in " + time + " seconds.\n\n");
        message.append("Highscores:\n");

        for (Player player : topPlayers) {
            message.append(player.getPlayerName()).append(": ").append(player.getPoints()).append(" Points. Time:").append(player.getTime()).append(" s\n");
        }

        outputArea.setText(message.toString());
    }
}
