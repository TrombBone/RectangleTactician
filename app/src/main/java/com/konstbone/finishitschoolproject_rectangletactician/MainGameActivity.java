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

import static com.konstbone.finishitschoolproject_rectangletactician.TestSurfaceView.oneRectCoord;

public class MainGameActivity extends AppCompatActivity {

    public static int rectSide1;
    public static int rectSide2;

    public static int NumOfSquareInWidth = 20;
    public static int NamOfSquareInHeight = 28;

    public static ArrayList<ArrayList<ArrayList<Integer>>> rectCoord = new ArrayList<>();

    public static TextView exceptionTV;
    public static boolean exceptionFlag = true;

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
        surfaceView.getLayoutParams().height = NamOfSquareInHeight *surfaceView.getLayoutParams().width/20;

        exceptionTV.setTextSize(exceptionTV.getLayoutParams().height);

        String rectSide1String = getIntent().getStringExtra("side1");
        String rectSide2String = getIntent().getStringExtra("side2");
        rectSide1 = Integer.parseInt(rectSide1String);
        rectSide2 = Integer.parseInt(rectSide2String);

        exceptionTV_good_text_func();
    }

    public static void exceptionTV_good_text_func() {
        exceptionFlag = true;
        exceptionTV.setText(R.string.exceptionTV_good_text);
        exceptionTV.setBackgroundColor(Color.GREEN);
    }

    public static void exceptionTV_bad_OutOfBoundsException_text_func() {
        exceptionFlag = false;
        exceptionTV.setText(R.string.exceptionTV_bad_OutOfBoundsException_text);
        exceptionTV.setBackgroundColor(Color.RED);
    }

    public static void exceptionTV_bad_LocationException_text_func() {
        exceptionFlag = false;
        exceptionTV.setText(R.string.exceptionTV_bad_LocationException_text);
        exceptionTV.setBackgroundColor(Color.RED);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotationButton:
                int help = rectSide1;
                rectSide1 = rectSide2;
                rectSide2 = help;
                break;
            case R.id.endPlayerProgressButton:
                if (exceptionFlag) {
                    rectCoord.add(oneRectCoord);
                    Intent FromMainGameToPlayer2StartIntent = new Intent();
                    startActivity(FromMainGameToPlayer2StartIntent);
                }
                //
                break;
        }
    }
}
