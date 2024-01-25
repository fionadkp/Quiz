package org.example.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.object.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerService {

    public List<Player> getTopPlayers() {
        List<Player> players = new ArrayList<>();
        String connectionString = "mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("statistics");
            MongoCollection<Document> collection = database.getCollection("playerStats");

            List<Document> aggregation = Arrays.asList(
                    new Document("$sort", new Document("points", -1).append("time", 1)),
                    new Document("$limit", 5)
            );

            for (Document playerDocument : collection.aggregate(aggregation)) {
                players.add(documentToPlayer(playerDocument));
            }
        } catch (Exception e) {
            // Handle the exception, you can log it or print the stack trace
            e.printStackTrace();
            // You might want to throw a custom exception or handle it in a way that makes sense for your application
        }

        return players;
    }

    private Player documentToPlayer(Document document) {
        Player player = new Player();
        player.setPlayerName(document.getString("name"));
        player.setPoints(document.getInteger("points"));
        player.setTime(document.getDouble("time"));
        return player;
    }
}
