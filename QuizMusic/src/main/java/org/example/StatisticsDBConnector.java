package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class StatisticsDBConnector {
    public void saveWinLog(String playerName, int points, double time){
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority")).build()
        )) {
            MongoDatabase database = mongoClient.getDatabase("statistics");
            try {
                MongoCollection<Document> carDocs = database.getCollection("playerStatistics");
                Document doc = new Document();
                doc.append("name", playerName);
                doc.append("points", points);
                doc.append("time", time);
                carDocs.insertOne(doc);
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }
}
