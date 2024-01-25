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
import java.util.Locale;

public class MusicService {

    public List<Artist> getAllArtists(int value, String category) {

        List<Artist> artists = new ArrayList<>(); // All artists in the list

        String connectionString = "mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority"; // Connection String

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("statistics");
            MongoCollection<Document> collection = database.getCollection("artists");

            for (int x = 0; x < 3;) { // loops 3 times
                Document sampleStage = new Document("$sample", new Document("size", 1));
                var aggregation = List.of(sampleStage); // One random artist
                var cursor = collection.aggregate(aggregation).iterator();

                try {
                    while (cursor.hasNext() && x < 3) { // Check x < 3 here
                        Document artistDocument = cursor.next();
                        Artist currentArtist = documentToArtist(artistDocument); // Convert Document to java object

                        // Check if artist has non-null and non-zero values
                        if (isValidArtist(currentArtist, category)) {
                            currentArtist.setChosen(Math.abs(value - getArtistValue(currentArtist, category))); // Sets winning condition to artist object

                            // Check if artists list already contains the chosen artist
                            if (artists.stream().noneMatch(p -> p.getChosen() == currentArtist.getChosen())) {
                                artists.add(currentArtist);
                                x++;
                            }
                        }
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

    private boolean isValidArtist(Artist artist, String category) {
        if (artist == null) {
            return false;
        }

        switch (category.toLowerCase(Locale.ROOT)) {
            case "grammys":
                return artist.getGrammys() != 0;
            case "mtv":
                return artist.getMtv() != 0;
            case "billboard":
                return artist.getBillboard() != 0;
            case "amas":
                return artist.getAmas() != 0;
            case "vmas":
                return artist.getVmas() != 0;
            case "emas":
                return artist.getEmas() != 0;
            case "brits":
                return artist.getBrits() != 0;
            default:
                return false;
        }
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