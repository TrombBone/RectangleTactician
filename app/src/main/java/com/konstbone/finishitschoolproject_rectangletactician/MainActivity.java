package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent StartIntent = new Intent(MainActivity.this, GreetingActivity.class);
        startActivity(StartIntent);

        //
    }

}
