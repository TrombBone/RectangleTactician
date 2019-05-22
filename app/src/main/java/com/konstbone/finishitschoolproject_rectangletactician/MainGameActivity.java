package com.konstbone.finishitschoolproject_rectangletactician;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MainGameActivity extends AppCompatActivity {

    public static int rectSide1;
    public static int rectSide2;
    public TextView exceptionTV;
    public static boolean exceptionFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Objects.requireNonNull(getSupportActionBar()).hide();

        exceptionTV = findViewById(R.id.exceptionTV);
        //

        String rectSide1String = getIntent().getStringExtra("side1");
        String rectSide2String = getIntent().getStringExtra("side2");
        rectSide1 = Integer.parseInt(rectSide1String);
        rectSide2 = Integer.parseInt(rectSide2String);

        exceptionTV_good_text_func();
    }
    /*
    Runnable exceptionTV_good_text_func = new Runnable() {
        @Override
        public void run() { }
    };
    */
    public void exceptionTV_good_text_func() {
        exceptionFlag = true;
        exceptionTV.setText(R.string.exceptionTV_good_text);
        exceptionTV.setBackgroundColor(Color.GREEN);
    }

    public void exceptionTV_bad_OutOfBoundsException_text_func() {
        exceptionFlag = false;
        exceptionTV.setText(R.string.exceptionTV_bad_OutOfBoundsException_text);
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
                //
                break;
        }
    }


}
