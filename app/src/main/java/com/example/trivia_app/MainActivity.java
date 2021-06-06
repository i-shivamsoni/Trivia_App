package com.example.trivia_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trivia_app.data.AnswerListAsyncResponse;
import com.example.trivia_app.data.QuestionBank;
import com.example.trivia_app.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private int CurrentQuestionIndex = 0;
    private int QuestionIndex;
    private List<Question> mQuestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextview = findViewById(R.id.textview_question);
        questionCounterTextview = findViewById(R.id.textview_counter);
        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);
        prevButton = findViewById(R.id.Button_prev);
        nextButton = findViewById(R.id.Button_next);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        mQuestionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
//                Log.d("inside", "onCreate: " + questionArrayList);
                String formatted = getString(R.string.counter, CurrentQuestionIndex, questionArrayList.size());
                questionCounterTextview.setText(formatted);
//                questionCounterTextview.setText(CurrentQuestionIndex + " / " + questionArrayList.size());
                questionTextview.setText(questionArrayList.get(CurrentQuestionIndex).getAnswer());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_prev:
                if (CurrentQuestionIndex > 0) {
                    CurrentQuestionIndex = CurrentQuestionIndex - 1 % mQuestionList.size();
                    updateQuestion();
                    updateQuestionCounter();
                }
                break;
            case R.id.Button_next:
                CurrentQuestionIndex = CurrentQuestionIndex + 1 % mQuestionList.size();
                Log.d("index", "onClick: " + CurrentQuestionIndex);
                updateQuestion();
                updateQuestionCounter();
                break;
            case R.id.button_false:
//                if (mQuestionList.get(CurrentQuestionIndex).isAnswerTrue())
////                    Log.d("ans", "onClick: " + "InCorrect");
//                    Toast.makeText(this, "InCorrect", Toast.LENGTH_SHORT).show();
//
//                else
////                    Log.d("ans", "onClick: " + "Correct");
//                    Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                checkAnswer(false);
                updateQuestion();
                break;
            case R.id.button_true:
//                if (mQuestionList.get(CurrentQuestionIndex).isAnswerTrue())
////                    Log.d("ans", "onClick: " + "Correct");
//                    Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
//                else
////                    Log.d("ans", "onClick: " + "InCorrect");
//                    Toast.makeText(this, "InCorrect", Toast.LENGTH_SHORT).show();
                checkAnswer(true);
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean userchoice) {
        boolean answerIsTrue = mQuestionList.get(CurrentQuestionIndex).isAnswerTrue();
        int toast_message_id = 0;
        if (userchoice == answerIsTrue)
            toast_message_id = R.string.true_txt;
        else { shake_animation();
            toast_message_id = R.string.false_txt;
        }
            Toast.makeText(this, toast_message_id, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestionCounter() {
        //questionCounterTextview.setText(String.valueOf(CurrentQuestionIndex) + R.id.textview_counter);
        String formatted = getString(R.string.counter, CurrentQuestionIndex, mQuestionList.size());
        questionCounterTextview.setText(formatted);
    }

    private void updateQuestion() {
        questionTextview.setText(mQuestionList.get(CurrentQuestionIndex).getAnswer());
    }
    private void shake_animation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);
    }
}