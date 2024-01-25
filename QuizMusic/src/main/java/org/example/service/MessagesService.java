package org.example.service;

import org.example.object.Artist;
import org.example.object.Player;

import java.util.*;

public class MessagesService {

    public void endOfGameMsg(String name, double time, int points, PlayerService playerService) {
        System.out.println("Congratulations " + name + "! You got " + points + " points in " + time + " seconds.");

        System.out.println("Highscores:");

        for (Player player : playerService.getTopPlayers()) {
            System.out.println(player.getPlayerName() + ": " + player.getPoints() + " Points. Time:" + player.getTime() + " s");
        }
    }

    public String getAnswerToQuestion(String question, Scanner scanner) {
        System.out.println(question);

        String answer = scanner.nextLine();
        return answer;
    }

    public int getSolution(Scanner scanner, List<Artist> artists, int points) {
        String playerSolution = getPlayerSolution(scanner, artists);
        Collections.sort(artists, Comparator.comparingInt(Artist::getWinningCondition).reversed());
        Artist a = artists.get(artists.size() - 1);
        String rightArtist = a.getName();

        boolean equals = Objects.equals(playerSolution.toLowerCase(Locale.ROOT), rightArtist.toLowerCase(Locale.ROOT));
        System.out.println(equals);
        System.out.println(" - " + rightArtist);

        if (equals) {
            points++;
        }
        return points;
    }

    public String getPlayerSolution(Scanner scanner, List<Artist> artists) {
        String artist;
        boolean check;
        do {
            check = false;
            artist = scanner.nextLine().toLowerCase(Locale.ROOT);

            for (Artist a : artists) {
                if (artist.equals(a.getName().toLowerCase(Locale.ROOT))) {
                    check = true;
                    break;
                }
            }
        } while (!check);
        return artist;
    }

    public String getCategory(Scanner scanner) {
        String category;
        do {
            category = scanner.nextLine().toLowerCase(Locale.ROOT);
        } while (!(category.equals("grammy") || category.equals("billboard")));
        return category;
    }
}
