package com.example.paraglidingquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.paraglidingquiz.Model.Question;
import com.example.paraglidingquiz.Model.QuestionCatalog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UpdateService extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateservice);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        fetchData();
    }

    public void fetchData(){

        class FetchDataClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UpdateService.this, "Checking for Updates", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

//                if (httpResponseMsg.equalsIgnoreCase("Data Matched")) {
                finish();

                // TODO: Remove mocked questions
                // Source: https://www.edinburghnews.scotsman.com/whats-on/arts-and-entertainment/25-funny-pub-quiz-questions-2020-hilarious-and-quirky-trivia-ask-your-online-quiz-plus-answers-2540427
                Question q1 = new Question(0, "If you dug a hole through the centre of the earth starting from Wellington in New Zealand, which European country would you end up in?", new String[]{"Germany", "Italy", "Austria", "Spain"},    3, 1, 0, 0);
                Question q2 = new Question(1, "What is the most common colour of toilet paper in France?", new String[]{"white", "blue", "pink", "black"},    2, 1, 1, 3);
                Question q3 = new Question(2, "Coprastastaphobia is the fear of what?", new String[]{"Constipation", "Long words", "The color white", "Copras"},0, 1, 2, 0);
                Question q4 = new Question(3, "What is Scooby Doo’s full name?", new String[]{"Scoobert Doodle", "No full name", "Scoobert Doo", "Scooby Doo"},2, 1, 3, 0);
                Question q5 = new Question(4, "It's illegal in Texas to put what on your neighbour’s Cow?", new String[]{"Water", "Grafitti", "Flowers", "Noodles"},      1, 1, 0, 0);

                Question[] questions = new Question[]{q1, q2, q3, q4, q5};
                Set<String> chapters = sharedPreferences.getStringSet(getString(R.string.chapterselection), null);
                if (chapters == null) {
                    chapters = new HashSet<String>();
                    for (Question question : questions) {
                        chapters.add(String.valueOf(question.getChapter()));
                    }
                }

                QuestionCatalog.setQuestions(questions, chapters);

                Intent intent = new Intent(UpdateService.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("email", params[0]);
                hashMap.put("password", params[1]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // TODO: Replace mocked return value
                //finalResult = httpParse.postRequest(hashMap, HttpURL);
                finalResult = "Data Matched";
                return finalResult;
            }
        }

        FetchDataClass userLoginClass = new FetchDataClass();

        userLoginClass.execute("","");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) { }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
