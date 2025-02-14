package com.telusko.controller;

import com.telusko.entity.Question;
import com.telusko.services.QuestionServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(origins = "*")
public class QuestionController {
    private QuestionServiceImpl questionService;

    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("/{id}")
    Question getQuestionById(@PathVariable(name = "id") Long id){
        return questionService.getQuestionById(id);
    }

}
