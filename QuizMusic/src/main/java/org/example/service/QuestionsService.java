package org.example.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.object.Artist;
import org.example.object.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.sample;

public class QuestionsService {

    private final MongoCollection<Document> questionsCollection;

    public QuestionsService(MongoDatabase database) {
        this.questionsCollection = database.getCollection("questions");
    }

    public void makeQuestion() {
        System.out.println("Choose a Category:");
        System.out.println("- Billboard");
        System.out.println("- Grammy");
    }

    public List<Artist> getQuizQuestion(Question randomQuestion, MusicService musicService) {
        System.out.println(randomQuestion.getQuestion());

        List<Artist> artists = musicService.getAllArtists(randomQuestion.getResult(), randomQuestion.getCategory());

        for (Artist artist : artists) {
            System.out.println(artist.getName());
        }
        return artists;
    }

    public List<Question> getMusicQuestion(String category) {
        category = category.toLowerCase(Locale.ROOT);
        return getQuestionsByCategory(category);
    }

    private List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = new ArrayList<>();

        // MongoDB aggregation pipeline to retrieve questions for a given category
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(match(Filters.eq("category", category)));  // Assuming Filters.eq is used for equality
        pipeline.add(sample(2));


        AggregateIterable<Document> result = questionsCollection.aggregate(pipeline);

        // Convert MongoDB Documents to Question objects
        for (Document document : result) {
            String questionText = document.getString("question");
            int resultValue = document.getInteger("result");
            String questionCategory = document.getString("category");

            questions.add(new Question(questionText, resultValue, questionCategory));
        }

        return questions;
    }
}
