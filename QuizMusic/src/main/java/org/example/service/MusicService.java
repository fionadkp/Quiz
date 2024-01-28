package org.example.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.example.object.Artist;

import java.util.*;

public class MusicService {

    private Set<String> uniqueArtistNames = new HashSet<>();

    public List<Artist> getAllArtists(int value, String category) {

        List<Artist> artists = new ArrayList<>();

        String connectionString = "mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("statistics");
            MongoCollection<Document> collection = database.getCollection("artists");

            for (int x = 0; x < 3; ) {
                Document sampleStage = new Document("$sample", new Document("size", 1));
                var aggregation = List.of(sampleStage);
                var cursor = collection.aggregate(aggregation).iterator();
                while (cursor.hasNext() && x < 3) {
                    Document artistDocument = cursor.next();
                    Artist currentArtist = documentToArtist(artistDocument);

                    if (isValidArtist(currentArtist, category) && !uniqueArtistNames.contains(currentArtist.getName()))
                    {
                        currentArtist.setChosen(Math.abs(value - getArtistValue(currentArtist, category)));

                        artists.add(currentArtist);
                        uniqueArtistNames.add(currentArtist.getName());
                        x++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            return 0;
        }
    }

}