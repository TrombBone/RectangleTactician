package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import java.util.Objects;

public class GreetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.regulationsButton:
                Intent FromGreetingToRegulationsIntent = new Intent(GreetingActivity.this, RegulationsActivity.class);
                startActivity(FromGreetingToRegulationsIntent);
                break;
            case R.id.settingsButton:
                //Intent FromGreetingToSettingsIntent = new Intent(GreetingActivity.this, SettingsActivity.class);
                //startActivity(intent);
                //finish();
                break;
            case R.id.startButton:
                Intent FromGreetingToPlayer1StartIntent = new Intent(GreetingActivity.this, Player1StartActivity.class);
                startActivity(FromGreetingToPlayer1StartIntent);
                finish();
                break;
        }

    }
}
