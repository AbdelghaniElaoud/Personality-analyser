package com.telusko.services;

import com.telusko.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();
    Question getQuestionById(Long id);
    void deleteQuestion(Long id);
    void deleteQuestion(Question question);
}
