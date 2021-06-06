package com.example.trivia_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.trivia_app.controller.AppController;
import com.example.trivia_app.data.AnswerListAsyncResponse;
import com.example.trivia_app.data.QuestionBank;
import com.example.trivia_app.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Question> List= new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("inside", "onCreate: "+questionArrayList);

            }
        });
    }
}