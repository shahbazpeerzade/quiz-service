package com.microservice.quizservice.controller;

import com.microservice.quizservice.model.QuestionWrapper;
import com.microservice.quizservice.model.QuizDto;
import com.microservice.quizservice.model.Response;
import com.microservice.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api")
public class QuizController {

    @Autowired
    QuizService quizService;

    //create a quiz with limit questions
    @PostMapping("quiz")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    // get particular quiz
    @GetMapping("quiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
        return quizService.getQuizQuestions(id);
    }
//submit the answers and take take result
    @PostMapping("quiz/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }


}
