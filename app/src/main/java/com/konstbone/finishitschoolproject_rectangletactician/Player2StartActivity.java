package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.Objects;

import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.playerFlag;

public class Player2StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2_start);
        Objects.requireNonNull(getSupportActionBar()).hide();

        playerFlag = "Player_2";
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent FromPlayer2StartToRandomSidesIntent = new Intent(Player2StartActivity.this, RandomSidesActivity.class);
        startActivity(FromPlayer2StartToRandomSidesIntent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        //обработчик нажатия системной кнопки "назад"
        //спросить пользователя, точно ли он хочет выйти и предупредить о несохранности всех данных игры
        //лучше с помощью AlertDialog (всплывающее окно)
    }
}
