package com.microservice.quizservice.feign;

import com.microservice.quizservice.model.QuestionWrapper;
import com.microservice.quizservice.model.Response;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("api/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numOfQuestions) ;

    @PostMapping("api/getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionsId);


    @PostMapping("api/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> response) ;
}
