package com.example.paraglidingquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import com.example.paraglidingquiz.Model.QuestionCatalog;
import com.example.paraglidingquiz.Preferences.PreferencesActivity;
import com.example.paraglidingquiz.Tools.PieChart;

import java.util.Set;

public class QuizActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    ConstraintLayout statisticsLayout;
    PieChart pieChartNeverAnswered;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        pieChartNeverAnswered = findViewById(R.id.pieChartStatistics);
        if (QuestionCatalog.getStatisticsProgress(this) != null)
            pieChartNeverAnswered.load(QuestionCatalog.getStatisticsProgress(this));

        statisticsLayout = findViewById(R.id.statisticsLayout);
        statisticsLayout.setMaxHeight(sharedPreferences.getBoolean("statistics", true) ? 600 : 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settingsMenuItem) {
            Intent intent = new Intent(QuizActivity.this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void neverAnswered(View view) {
        // Set QuestionSet to never answered
        QuestionCatalog.setCurrentQuestionSet(QuestionCatalog.NEVER_ANSWERED, Integer.MAX_VALUE);

        if (QuestionCatalog.hasNext()) {
            Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.toastallquestionanswered, Toast.LENGTH_SHORT).show();
    }

    public void answeredWrong(View view) {
        // Set QuestionSet to never answered
        QuestionCatalog.setCurrentQuestionSet(QuestionCatalog.ANSWERED_WRONG, Integer.MAX_VALUE);
        Toast.makeText(this, "" + QuestionCatalog.getCurrentQuestionSet().length, Toast.LENGTH_SHORT).show();

        if (QuestionCatalog.hasNext()) {
            Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.toastallquestionsansweredcorrect, Toast.LENGTH_SHORT).show();
    }

    public void randomQuestions(View view) {
        QuestionCatalog.setCurrentQuestionSet(QuestionCatalog.RANDOM_QUESTIONS, Integer.MAX_VALUE);

        if (QuestionCatalog.hasNext()) {
            Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.toasterror, Toast.LENGTH_SHORT).show();
    }

    public void boxQuestions(View view) {
        QuestionCatalog.setCurrentQuestionSet(QuestionCatalog.BOX_QUESTIONS, Integer.valueOf(sharedPreferences.getString("boxselection", "1000")));

        if (QuestionCatalog.hasNext()) {
            Intent intent = new Intent(QuizActivity.this, QuestionActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, R.string.toasterror, Toast.LENGTH_SHORT).show();
    }

    public void statistics(View view) {
        Intent intent = new Intent(QuizActivity.this, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "chapterselection":
                System.out.println(sharedPreferences.getStringSet("chapterselection", null));
                Set<String> chapters = sharedPreferences.getStringSet("chapterselection", null);
                QuestionCatalog.setQuestions(QuestionCatalog.getQuestions(), chapters);
                break;
            case "statistics":
                statisticsLayout.setMaxHeight(sharedPreferences.getBoolean("statistics", true) ? 600 : 0);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
