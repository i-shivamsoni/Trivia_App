package com.example.trivia_app.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia_app.controller.AppController;
import com.example.trivia_app.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Question> mQuestionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                Log.d("JSON value", "onResponse: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Question question = new Question();
                        question.setAnswer(response.getJSONArray(i).get(0).toString());
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
//                        Log.d("json", ""+i+" " + response.getJSONArray(i).get(0)+" "
//                                + response.getJSONArray(i).getBoolean(1));
                        mQuestionArrayList.add(question);
//                        Log.d("str", "onResponse: "+question);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (callBack!=null) callBack.processFinished(mQuestionArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return mQuestionArrayList;
    }
}
