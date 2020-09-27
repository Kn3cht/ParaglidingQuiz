package com.example.paraglidingquiz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paraglidingquiz.Model.QuestionCatalog;
import com.example.paraglidingquiz.Tools.PieChart;

public class StatisticsActivity extends AppCompatActivity {

    PieChart pieChartNotAnswered;
    PieChart pieChartAnswered;
    PieChart pieChartBox;

    TextView numberQuestions;
    TextView numberChapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        pieChartNotAnswered = findViewById(R.id.pieChartProgress);
        pieChartNotAnswered.load(QuestionCatalog.getStatisticsProgress(this));

        pieChartAnswered = findViewById(R.id.pieChartAnswered);
        pieChartAnswered.load(QuestionCatalog.getStatisticsAnswered(this));

        pieChartBox = findViewById(R.id.pieChartBox);
        pieChartBox.load(QuestionCatalog.getStatisticsBox(this));

        numberQuestions = findViewById(R.id.numberQuestions);
        numberQuestions.setText("Anzahl Fragen: " + QuestionCatalog.getQuestions().length);

        numberChapters = findViewById(R.id.numberChapters);
        numberChapters.setText("Anzahl Kapitel: " + QuestionCatalog.getChapters().size());
    }
}
