package com.example.paraglidingquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paraglidingquiz.Model.Question;
import com.example.paraglidingquiz.Model.QuestionCatalog;
import com.example.paraglidingquiz.Tools.AnswerButton;

public class QuestionActivity extends AppCompatActivity {

    Button nextQuestionButton;
    TextView questionText;

    Question currentQuestion;

    AnswerButton[] answerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionText = findViewById(R.id.questionText);

        answerButtons = new AnswerButton[4];

        answerButtons[0] = findViewById(R.id.answer0);
        answerButtons[1] = findViewById(R.id.answer1);
        answerButtons[2] = findViewById(R.id.answer2);
        answerButtons[3] = findViewById(R.id.answer3);

        nextQuestionButton = findViewById(R.id.nextQuestionButton);

        updateQuestion();
    }


    public void validateAnswer(View view) {
        nextQuestionButton.setVisibility(View.VISIBLE);

        view.setSelected(true);
    }

    private void updateQuestion() {
        currentQuestion = QuestionCatalog.getNextQuestion();

        questionText.setText(currentQuestion.getQuestion());

        String[] answers = currentQuestion.getAnswers();
        /* Reset and set new Text */
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].load(answers[i]);
            answerButtons[i].clearFocus();
            answerButtons[i].setEnabled(true);

            answerButtons[i].setBackground(getResources().getDrawable(R.drawable.button_bg));
            answerButtons[i].setFocusableInTouchMode(true);
        }

        /* Set listeners */
        for (AnswerButton answerButton : answerButtons) {
            answerButton.setOnFocusChangeListener((v, b) -> {
                if (b)
                    v.performClick();
            });

            answerButton.setOnClickListener(v -> {
                nextQuestionButton.setVisibility(View.VISIBLE);
                for (int i = 0; i < answerButtons.length; i++) {
                    AnswerButton ab = answerButtons[i];

                    if (i == currentQuestion.getCorrectIndex()) {
                        ab.setBackgroundColor(getResources().getColor(R.color.correct));
                        if (!ab.equals(answerButton))
                            ab.setBackgroundColor(getResources().getColor(R.color.wrong));
                        else
                            ab.load(ab.getAnswerText() + " \uD83C\uDF89");
                    }
                    if (!ab.equals(answerButton))
                        ab.setEnabled(false);
                }
            });
        }

        nextQuestionButton.setVisibility(View.INVISIBLE);
    }

    public void nextQuestion(View view) {
        if (QuestionCatalog.hasNext()) {
            updateQuestion();
        } else {
            Toast.makeText(this, "Alle Fragen bereits beantwortet", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(QuestionActivity.this, QuizActivity.class);
            startActivity(intent);
        }
    }
}
