package com.telusko.services;

import com.telusko.entity.Personality;
import com.telusko.entity.Question;
import com.telusko.entity.UserResponse;
import com.telusko.entity.WorldCup;
import com.telusko.repository.QuestionRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telusko.seeder.DataInitializer.questions;

@Service
public class PromptMaster {
    private final QuestionRepository questionRepository;
    private ChatClient chatClient;

    public PromptMaster(ChatClient.Builder builder, QuestionRepository questionRepository) {
        this.chatClient = builder.build();
        this.questionRepository = questionRepository;
    }

    public WorldCup theWinningTeamOfAWorldCupTournament(String year){
        String systemMessage = "For the specified FIFA World Cup year, provide the following details: " +
                "The team that won the final match, a complete list of players who were part of the winning squad, " +
                "and the country that hosted the tournament. " +
                "Ensure all facts are historically accurate based on official FIFA records. " +
                "Do not include incorrect players in the squad. " +
                "Example response for 2010: " +
                "Winning Team: Spain. " +
                "Players: Iker Casillas, Gerard Piqué, Carles Puyol, Andrés Iniesta, Xavi, David Villa, Sergio Ramos, " +
                "Xabi Alonso, Sergio Busquets, Joan Capdevila, Pedro, Fernando Torres, Cesc Fàbregas, Víctor Valdés, Pepe Reina, " +
                "Raúl Albiol, Javi Martínez, Juan Mata, Fernando Llorente, Jesús Navas, etc. " +
                "Host Nation: South Africa. " +
                "Provide only the requested details without adding extra explanations.";
        return chatClient.prompt()
                .system(systemMessage)
                .user(year)
                .call()
                .entity(WorldCup.class);

    }

    public Personality personalityAnalysis(List<UserResponse> responses){
        String systemMessage = "You have been provided with a list of responses to the Big Five personality test. " +
                "For each of the five personality traits — Openness to Experience, Conscientiousness, Extraversion, " +
                "Agreeableness, and Neuroticism — calculate the average score based on the user's responses (1-5). " +
                "Then, based on the scores, provide a description of the user's personality using insightful and nuanced language.make the explanation long and deep with more information and set also what people would say about you " +
                "For example, a high score in Openness suggests creativity, imagination, and curiosity, while a low score in Neuroticism indicates calmness and emotional stability. " +
                "Provide a summary that explains the user's personality, such as: 'You are imaginative, open-minded, and enjoy new experiences' or 'You are organized, responsible, and value structure.' " +
                "Also, include the level of each trait out of 100%, and ensure you use 'you are' instead of 'this person'. " +
                "Do not include unnecessary details, just the personality description and levels of each trait as a percentage (like this and only like this you get me????, Openness: 85%, Neuroticism: 30%).";

        StringBuilder userResponsesString = new StringBuilder();
        for (int i = 0; i < responses.size(); i++) {
            Question question = questionRepository.findById(Long.valueOf(i+1)).get();
            UserResponse response = responses.get(i);

            userResponsesString.append("Question: ").append(question.getContent())
                    .append("\nResponse: ").append(response.getResponse()).append("\n\n");
        }

        return chatClient.prompt()
                .system(systemMessage)
                .user(userResponsesString.toString())
                .call()
                .entity(Personality.class);
    }

}
