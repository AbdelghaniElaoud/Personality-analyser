package com.telusko.seeder;

import com.telusko.entity.Question;
import com.telusko.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    public static List<String> questions = Arrays.asList(
            // Openness to Experience
            "I have a vivid imagination.",
            "I enjoy trying new activities and experiences.",
            "I often think about philosophical or abstract concepts.",
            "I like to challenge traditional ways of thinking.",
            "I am full of ideas.",
            "I am curious about many different things.",
            "I appreciate artistic and creative activities.",
            "I tend to be more creative than analytical.",
            "I get excited about learning new things.",
            "I prefer variety over routine.",

            // Conscientiousness
            "I pay attention to details and stay organized.",
            "I always complete my tasks on time.",
            "I am very responsible and reliable.",
            "I follow a schedule and like planning ahead.",
            "I work hard to achieve my goals.",
            "I often prepare for things well in advance.",
            "I am disciplined in my habits.",
            "I rarely make impulsive decisions.",
            "I can always be counted on to finish what I start.",
            "I take my responsibilities seriously.",

            // Extraversion
            "I love social gatherings and being around people.",
            "I am talkative and find it easy to start conversations.",
            "I enjoy being the center of attention.",
            "I get energized by social interactions.",
            "I feel comfortable expressing my opinions in a group.",
            "I seek excitement and adventure.",
            "I make friends easily.",
            "I prefer being active rather than sitting alone.",
            "I am outgoing and enthusiastic.",
            "I feel drained after spending too much time alone.",

            // Agreeableness
            "I feel sympathy for others who are struggling.",
            "I try to be helpful and supportive to those around me.",
            "I get along well with others and avoid conflict.",
            "I consider myself to be a kind and caring person.",
            "I am good at understanding others’ emotions.",
            "I trust people easily.",
            "I enjoy cooperating rather than competing.",
            "I forgive people easily.",
            "I am polite and respectful in most situations.",
            "I put others’ needs before my own.",

            // Neuroticism
            "I get stressed out easily.",
            "I often feel anxious or nervous.",
            "I worry about things more than most people.",
            "I experience mood swings.",
            "I am sensitive to criticism.",
            "I sometimes feel sad or depressed for no reason.",
            "I struggle to control my emotions.",
            "I overthink things and get stuck in my thoughts.",
            "I feel overwhelmed when under pressure.",
            "I take things personally, even when I shouldn’t."
    );

    public DataInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {


        // Iterate over the questions and save to the database
        if (questionRepository.findAll().isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
                Question question = new Question();
                question.setContent(questions.get(i));
                questionRepository.save(question);
            }
            System.out.println("Questions initialized successfully!");
        }


    }
}