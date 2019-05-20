package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Player1StartActivity extends AppCompatActivity {

    //Timer timer;
    //TimerTask task;

    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player1_start);
        Objects.requireNonNull(getSupportActionBar()).hide();
        /*
        while (flag) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Нажмите на экран", Toast.LENGTH_SHORT).show();

                }
            };
            timer.schedule(task, 3000);
        }
        */
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent FromPlayer1StartToRandomSidesIntent = new Intent(Player1StartActivity.this, RandomSidesActivity.class);
        startActivity(FromPlayer1StartToRandomSidesIntent);
        return true;
    }


}
