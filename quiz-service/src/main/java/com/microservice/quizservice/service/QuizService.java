package com.microservice.quizservice.service;

import com.microservice.quizservice.dao.QuizDao;
import com.microservice.quizservice.feign.QuizInterface;
import com.microservice.quizservice.model.QuestionWrapper;
import com.microservice.quizservice.model.Quiz;
import com.microservice.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsId(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionsId = quiz.getQuestionsId();
        ResponseEntity<List<QuestionWrapper>> question = quizInterface.getQuestionFromId(questionsId);
        return question;

      }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return score;
    }
}
