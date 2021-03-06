package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import static com.konstbone.finishitschoolproject_rectangletactician.TestSurfaceView.draw;
import static com.konstbone.finishitschoolproject_rectangletactician.TestSurfaceView.oneRectCoord;
import static com.konstbone.finishitschoolproject_rectangletactician.TestSurfaceView.playerOneChecker;

public class MainGameActivity extends AppCompatActivity {

    public static int rectSide1;
    public static int rectSide2;

    public static int NumOfSquareInWidth = 20;
    public static int NumOfSquareInHeight = 28;

    public static ArrayList<ArrayList<ArrayList<Integer>>> playerOneRectCoord = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<Integer>>> playerTwoRectCoord = new ArrayList<>();

    public static TextView exceptionTV;
    public static boolean exceptionFlag = false;

    public static String playerFlag;

    TestSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Objects.requireNonNull(getSupportActionBar()).hide();

        surfaceView = findViewById(R.id.testSurfaceView_ClassElement);
        surfaceView.mainGameActivity = this;
        exceptionTV = findViewById(R.id.exceptionTV);
        //

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        surfaceView.getLayoutParams().height = NumOfSquareInHeight *surfaceView.getLayoutParams().width/20;

        exceptionTV.setTextSize(exceptionTV.getLayoutParams().height);

        String rectSide1String = getIntent().getStringExtra("side1");
        String rectSide2String = getIntent().getStringExtra("side2");
        rectSide1 = Integer.parseInt(rectSide1String);
        rectSide2 = Integer.parseInt(rectSide2String);

        exceptionTV_bad_NoRectangle_text_func();
    }

    public static void exceptionTV_good_text_func() {
        exceptionFlag = true;
        exceptionTV.setBackgroundColor(Color.GREEN);
        exceptionTV.setText(R.string.exceptionTV_good_text);
    }

    public static void exceptionTV_bad_NoRectangle_text_func() {
        exceptionFlag = false;
        exceptionTV.setBackgroundColor(Color.RED);
        exceptionTV.setText(R.string.exceptionTV_bad_NoRectangle_text);
    }

    public static void exceptionTV_bad_OutOfBoundsException_text_func() {
        exceptionFlag = false;
        exceptionTV.setBackgroundColor(Color.RED);
        exceptionTV.setText(R.string.exceptionTV_bad_OutOfBoundsException_text);
    }

    public static void exceptionTV_bad_LocationException_text_func() {
        exceptionFlag = false;
        exceptionTV.setBackgroundColor(Color.RED);
        exceptionTV.setText(R.string.exceptionTV_bad_LocationException_text);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotationButton:
                int help = rectSide1;
                rectSide1 = rectSide2;
                rectSide2 = help;
                if (playerFlag.equals("Player_1")) {
                    playerOneChecker(playerOneRectCoord);
                } else if (playerFlag.equals("Player_2")){
                    playerOneChecker(playerTwoRectCoord);
                    //playerTwoChecker();
                }
                break;
            case R.id.endPlayerProgressButton:
                if (exceptionFlag) {
                    if (playerFlag.equals("Player_1")) {
                        playerOneRectCoord.add(oneRectCoord);// запоминание координат прямоугольников 1 игрока
                        Intent FromMainGameToPlayer2StartIntent = new Intent(MainGameActivity.this, Player2StartActivity.class);
                        startActivity(FromMainGameToPlayer2StartIntent);
                        finish();
                    } else if (playerFlag.equals("Player_2")) {
                        playerTwoRectCoord.add(oneRectCoord);// запоминание координат прямоугольников 2 игрока
                        Intent FromMainGameToPlayer1StartIntent = new Intent(MainGameActivity.this, Player1StartActivity.class);
                        startActivity(FromMainGameToPlayer1StartIntent);
                        finish();
                    }
                    draw = false;
                }
                //
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //обработчик нажатия системной кнопки "назад"
        //спросить пользователя, точно ли он хочет выйти и предупредить о несохранности всех данных игры
        //лучше с помощью AlertDialog (всплывающее окно)
    }
}
