package com.example.paraglidingquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO check if already logged in
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, UpdateService.class);
        startActivity(intent);
        finish();
    }
}
