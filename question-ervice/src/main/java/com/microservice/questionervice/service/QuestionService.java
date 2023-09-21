package com.microservice.questionervice.service;

import com.microservice.questionervice.dao.QuestionDao;
import com.microservice.questionervice.model.Question;
import com.microservice.questionervice.model.QuestionWrapper;
import com.microservice.questionervice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionForQuiz(
            String categoryName, Integer numOfQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numOfQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionForId(List<Integer> questionsId) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer i : questionsId) {
            questions.add(questionDao.findById(i).get());
        }
        for (Question question : questions) {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            questionWrappers.add(wrapper);
        }


        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> response) {
        int right = 0;
        for (Response response1 : response) {
            Question question = questionDao.findById(response1.getId()).get();
            if (response1.getResponse().equals(question.getRightAnswer())) {
                right++;
            }

        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
