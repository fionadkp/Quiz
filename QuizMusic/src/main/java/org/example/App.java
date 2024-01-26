package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.example.object.Artist;
import org.example.object.Question;
import org.example.service.*;

import java.util.*;

public class App {
    static int points;

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString("mongodb+srv://root:root@quiz.hio3wxv.mongodb.net/?retryWrites=true&w=majority"))
                        .build())) {

            MongoDatabase database = mongoClient.getDatabase("statistics");
            QuestionsService questionService = new QuestionsService(database);

            MusicService musicService = new MusicService();
            StatisticsDBConnector sdb = new StatisticsDBConnector();
            PlayerService playerService = new PlayerService();
            MessagesService messageService = new MessagesService();
            Scanner scanner = new Scanner(System.in);

            String name = messageService.getAnswerToQuestion("Type in your username:", scanner);

            long start = System.currentTimeMillis();
            run(questionService, scanner, musicService, messageService);

            long finish = System.currentTimeMillis();
            double time = (double) (finish - start) / 1000;
            sdb.saveWinLog(name, points, time);

            messageService.endOfGameMsg(name, time, points, playerService);
        }
    }

    private static void run(QuestionsService questionService, Scanner scanner, MusicService musicService, MessagesService messageService) {
        Random random = new Random();
        questionService.makeQuestion();
        String category = messageService.getCategory(scanner);
        for (int x = 0; x < 5; x++) {


            List<Question> questions = questionService.getMusicQuestion(category);
            Question randomQuestion = questions.isEmpty() ? null : questions.get(random.nextInt(questions.size()));

            List<Artist> artists = questionService.getQuizQuestion(randomQuestion, musicService);

            points = messageService.getSolution(scanner, artists, points);
        }
    }
}
