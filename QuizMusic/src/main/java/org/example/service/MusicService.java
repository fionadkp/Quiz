package org.example.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.example.object.Artist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicService {

    public List<Artist> getAllArtists(int value, String category) {

        List<Artist> artists = new ArrayList<>(); // All artists in the list

        String connectionString = "mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority"; // Connection String

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("statistics");
            MongoCollection<Document> collection = database.getCollection("artists");

            for (int x = 0; x < 3;) { // loops 3 times

                var aggregation = Arrays.asList(Aggregates.sample(1)); // One random artist
                var cursor = collection.aggregate(aggregation).iterator();

                try {
                    while (cursor.hasNext() && x < 3) { // Check x < 3 here

                        Document artistDocument = cursor.next();
                        Artist currentArtist = documentToArtist(artistDocument); // Convert BSON to java object

                        currentArtist.setWinningCondition(Math.abs(value - getArtistValue(currentArtist, category))); // Sets winning condition to artist object

//                        boolean isUnique = artists.stream().noneMatch(p -> p.getWinningCondition() == currentArtist.getWinningCondition()); // Check if artists have the same winning condition

//                        if (isUnique) {
//                            artists.add(currentArtist);
//                            x++;
//                        }
                        artists.add(currentArtist);
                        x++;
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Handle or log the exception
                } finally {
                    cursor.close(); // Close the cursor in the finally block to ensure proper resource cleanup
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception
        }

        return artists;
    }


//    @Override
//    public Artist documentToArtist(Document document) {
//        return null;
//    }
//
//    @Override
//    public int getArtistValue(Artist currentArtist, String category) {
//        return 0;
//    }

    public Artist documentToArtist(Document document) {
        Artist artist = new Artist();
        artist.setName(document.getString("name"));
        artist.setGrammys(document.getInteger("grammys"));
        artist.setMtv(document.getInteger("mtv"));
        artist.setBillboard(document.getInteger("billboard"));
        artist.setAmas(document.getInteger("amas"));
        artist.setVmas(document.getInteger("vmas"));
        artist.setEmas(document.getInteger("emas"));
        artist.setBrits(document.getInteger("brits"));
        return artist;
    }

    public int getArtistValue(Artist currentArtist, String category) {
        if ("Grammys".equals(category)) {
            return currentArtist.getGrammys();
        } else if ("MTV".equals(category)) {
            return currentArtist.getMtv();
        } else if ("Billboard".equals(category)) {
            return currentArtist.getBillboard();
        } else if ("AMAs".equals(category)) {
            return currentArtist.getAmas();
        } else if ("VMAs".equals(category)) {
            return currentArtist.getVmas();
        } else if ("EMAs".equals(category)) {
            return currentArtist.getEmas();
        } else if ("Brits".equals(category)) {
            return currentArtist.getBrits();
        } else {
            // Handle unknown category
            return 0; // You can change this according to your logic
        }
    }

}