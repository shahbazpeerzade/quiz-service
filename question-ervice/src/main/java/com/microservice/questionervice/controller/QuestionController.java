package com.microservice.questionervice.controller;

import com.microservice.questionervice.model.Question;
import com.microservice.questionervice.model.QuestionWrapper;
import com.microservice.questionervice.model.Response;
import com.microservice.questionervice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    Environment environment;



    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("question")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numOfQuestions) {
        return questionService.getQuestionForQuiz(categoryName, numOfQuestions);
    }

    @PostMapping("getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionsId) {
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionForId(questionsId);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) {
        return questionService.getScore(response);
    }


}
