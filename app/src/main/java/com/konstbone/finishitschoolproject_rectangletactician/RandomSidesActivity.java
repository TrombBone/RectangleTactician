package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RandomSidesActivity extends AppCompatActivity {

    TextView randomTV1;
    TextView randomTV2;
    Button generateRandomButton;
    Timer timer;
    TimerTask task;

    int side1, side2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomsides);
        Objects.requireNonNull(getSupportActionBar()).hide();

        randomTV1 = findViewById(R.id.randomTV1);
        randomTV2 = findViewById(R.id.randomTV2);
        generateRandomButton = findViewById(R.id.generateRandomButton);
    }

    public void randomNumbers() {
        side1 = (int) (Math.random()*6  + 1);
        side2 = (int) (Math.random()*6  + 1);
        randomTV1.setText(String.valueOf(side1));
        randomTV2.setText(String.valueOf(side2));
    }

    public void onClick(View view) {
        randomNumbers();
        generateRandomButton.setEnabled(false);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Intent FromRandomSidesToMainGameIntent = new Intent(RandomSidesActivity.this, MainGameActivity.class);
                FromRandomSidesToMainGameIntent.putExtra("side1", String.valueOf(side1));
                FromRandomSidesToMainGameIntent.putExtra("side2", String.valueOf(side2));
                startActivity(FromRandomSidesToMainGameIntent);
            }
        };
        timer.schedule(task, 1500);
    }
}
