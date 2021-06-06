package com.example.trivia_app.data;

import com.example.trivia_app.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);

}
