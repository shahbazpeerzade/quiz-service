package com.micro.quizapp.controller;

import com.micro.quizapp.model.QuestionWrapper;
import com.micro.quizapp.model.Response;
import com.micro.quizapp.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// controller for quiz
@RestController
@RequestMapping("api")
public class QuizController {

    @Autowired
    QuizService quizService;
//create a quiz with limit questions
    @PostMapping("quiz")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }
//    get particular quiz
    @GetMapping("quiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("quiz/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }


}
