package com.example.paraglidingquiz.Tools;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.paraglidingquiz.R;

public class AnswerButton extends ConstraintLayout {

    TextView buttonText;

    public AnswerButton(Context context) {
        super(context);
        init();
    }

    public AnswerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnswerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.answer_button, this);
        buttonText = findViewById(R.id.buttonText);
    }

    public void load(String answer) {
        buttonText.setText(answer);
    }

    public String getAnswerText() { return buttonText.getText().toString(); }
}
