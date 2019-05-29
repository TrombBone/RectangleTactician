package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.NumOfSquareInHeight;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.NumOfSquareInWidth;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.exceptionTV_bad_LocationException_text_func;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.exceptionTV_bad_OutOfBoundsException_text_func;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.exceptionTV_good_text_func;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.playerFlag;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.playerOneRectCoord;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.playerTwoRectCoord;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.rectSide1;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.rectSide2;


public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public MainGameActivity mainGameActivity;
    TextView exceptionTV;

    public TestSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
    }

    static float touchX;
    static float touchY;
    static int partCanvasWidth;
    static float width;
    static float height;
    static boolean draw = false;
    public static ArrayList<ArrayList<Integer>> oneRectCoord;
    static ArrayList<Integer> onePointCoord;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchX = event.getX();
        touchY = event.getY();
        exceptionTV = MainGameActivity.exceptionTV;

        if (playerFlag.equals("Player_1")) {
            playerOneChecker();
        } else if (playerFlag.equals("Player_2")) {
            playerTwoChecker();
        }
        //вся проверка - здесь!
        return true;
    }

    public static void playerOneChecker() { // проверка расположеня прямоугольника 1 игрока
        for (int x = 0; x < NumOfSquareInWidth*partCanvasWidth; x=x+partCanvasWidth) {
            for (int y = 0; y < NumOfSquareInHeight *partCanvasWidth; y=y+partCanvasWidth) {
                if ((touchX >= x && touchX <= x + partCanvasWidth)) {
                    if (touchY >= y && touchY <= y + partCanvasWidth) {
                        if ((x - partCanvasWidth*(rectSide1-1) >= 0 && y - partCanvasWidth*(rectSide2-1) >= 0)) { // проверка выхода за границы сетки (здесь: и экрана тоже)
                            exceptionTV_good_text_func();
                            draw = true;
                            { // запоминание координат всех точек:
                                oneRectCoord = new ArrayList<>();
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x - partCanvasWidth * (rectSide1 - 1));
                                onePointCoord.add(y - partCanvasWidth * (rectSide2 - 1));
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x + partCanvasWidth);
                                onePointCoord.add(y - partCanvasWidth * (rectSide2 - 1));
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x - partCanvasWidth * (rectSide1 - 1));
                                onePointCoord.add(y + partCanvasWidth);
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x + partCanvasWidth);
                                onePointCoord.add(y + partCanvasWidth);
                                oneRectCoord.add(onePointCoord);
                            }
                            //Log.d("my", "прямоуольник внутри поля");
                        } else {
                            exceptionTV_bad_OutOfBoundsException_text_func();
                            draw = false;
                        }
                        Log.d("my", "playerOneRectCoord.size: " + playerOneRectCoord.size());
                        if (playerOneRectCoord.size() > 0 && draw) {// если есть прямоугольники рядом
                            boolean pointFlag = false;
                            int rightLengthEmpty = (y + partCanvasWidth) - (y - partCanvasWidth*(rectSide2-1));
                            int leftLengthEmpty = rightLengthEmpty;
                            int upLengthEmpty = (x + partCanvasWidth) - (x - partCanvasWidth*(rectSide1-1));
                            int downLengthEmpty = upLengthEmpty;

                            Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                            Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);

                            checkAll: for (int numRect = 0; numRect < playerOneRectCoord.size(); numRect++) {
                                for (int numPointFirstRect = 0; numPointFirstRect < 4; numPointFirstRect++) {
                                    for (int numPointSecondRect = 0; numPointSecondRect < 4; numPointSecondRect++) {
                                        boolean good = false;
                                        //Log.d("my", "old_X: " + playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0));
                                        //Log.d("my", "new_X: " + oneRectCoord.get(numPointSecondRect).get(0));
                                        Log.d("my", "numRect: " + numRect);
                                        //проверка прямоугольников на наезды друг на друга: (не работает)
                                        if (((oneRectCoord.get(0).get(0) <= playerOneRectCoord.get(numRect).get(0).get(0) && oneRectCoord.get(0).get(1) <= playerOneRectCoord.get(numRect).get(0).get(1)) && (oneRectCoord.get(3).get(0) >= playerOneRectCoord.get(numRect).get(3).get(0) && oneRectCoord.get(3).get(1) >= playerOneRectCoord.get(numRect).get(3).get(1))) ||
                                                ((oneRectCoord.get(0).get(0) >= playerOneRectCoord.get(numRect).get(0).get(0) && oneRectCoord.get(0).get(1) >= playerOneRectCoord.get(numRect).get(0).get(1)) && (oneRectCoord.get(3).get(0) <= playerOneRectCoord.get(numRect).get(3).get(0) && oneRectCoord.get(3).get(1) <= playerOneRectCoord.get(numRect).get(3).get(1))) ){
                                            exceptionTV_bad_LocationException_text_func();
                                            Log.d("my", "прямоугольники внутри друг друга. стоп, всё плохо");
                                            pointFlag = false;
                                            break checkAll;
                                        }
                                        if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0).equals(oneRectCoord.get(numPointSecondRect).get(0))) {//одинаковые Х, проверяем по Y
                                            if (numPointFirstRect > 1 || numPointSecondRect > 1) {
                                                Log.d("my", "не допустимые точки");
                                                //continue checkAll;
                                            } else {
                                                Log.d("my", "одинаковые Х, проверяем по У");
                                                pointFlag = true;
                                                 if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) <= oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) >= oneRectCoord.get(numPointSecondRect + 2).get(1)) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    good = true;
                                                    Log.d("my", "старый прямоугольник включает новый сбоку. стоп, всё хорошо");
                                                    break checkAll;
                                                } else if ((playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) >= oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) <= oneRectCoord.get(numPointSecondRect + 2).get(1))) {
                                                    if (numRect == playerOneRectCoord.size() - 1) {
                                                        exceptionTV_good_text_func();
                                                        draw = true;
                                                        good = true;
                                                        Log.d("my", "новый прямоугольник включает старый сбоку, но больше прямоугольников нет. стоп, всё хорошо");
                                                        break checkAll;
                                                    }
                                                    Log.d("my", "rightLengthEmpty = " + rightLengthEmpty + " leftLengthEmpty = " + leftLengthEmpty);
                                                    int lengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                    Log.d("my", "lengthNow = " + lengthNow);
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        rightLengthEmpty = rightLengthEmpty - lengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        leftLengthEmpty = leftLengthEmpty - lengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + rightLengthEmpty);
                                                    }
                                                    Log.d("my", "новый прямоугольник включает старый сбоку. работаем дальше");
                                                    //continue checkAll;
                                                } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) > oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) > oneRectCoord.get(numPointSecondRect + 2).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) <= oneRectCoord.get(numPointSecondRect + 2).get(1)) {
                                                    Log.d("my", "rightLengthEmpty = " + rightLengthEmpty + " leftLengthEmpty = " + leftLengthEmpty);
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        int rightLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                        Log.d("my", "rightLengthNow = " + rightLengthNow);
                                                        rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        int leftLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                        Log.d("my", "leftLengthNow = " + leftLengthNow);
                                                        leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + leftLengthEmpty);
                                                    }
                                                    Log.d("my", "новый торчит сверху");
                                                    //continue checkAll;
                                                } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) < oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) < oneRectCoord.get(numPointSecondRect + 2).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) >= oneRectCoord.get(numPointSecondRect).get(1)) {
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        int rightLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                        Log.d("my", "rightLengthNow = " + rightLengthNow);
                                                        rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        int leftLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                        Log.d("my", "leftLengthNow = " + leftLengthNow);
                                                        leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + leftLengthEmpty);
                                                    }
                                                    Log.d("my", "новый торчит снизу");
                                                    //continue checkAll;
                                                }
                                                Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                                                if (rightLengthEmpty <= 0 || leftLengthEmpty <= 0) {
                                                    good = true;
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    Log.d("my", "левые/правые отрицательные или = 0. стоп, всё хорошо");
                                                    break checkAll;
                                                } else {
                                                    good = false;
                                                    exceptionTV_bad_LocationException_text_func();
                                                    Log.d("my", "левые/правые не отрицательные. всё плохо");
                                                    pointFlag = false;
                                                    //continue checkAll;
                                                }
                                            }
                                        }

                                        if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1).equals(oneRectCoord.get(numPointSecondRect).get(1))) {// одинаковые У, проверяем по X
                                            //Log.d("my", "numPointFirstRect = " + numPointFirstRect + "numPointSecondRect = " + numPointSecondRect);
                                            if (numPointFirstRect % 2 != 0 || numPointSecondRect % 2 != 0) {
                                                Log.d("my", "не допустимые точки");
                                                //continue checkAll;
                                            } else {
                                                Log.d("my", "одинаковые У, проверяем по Х");
                                                pointFlag = true;
                                                if ((playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) >= oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) <= oneRectCoord.get(numPointSecondRect + 1).get(0))) {
                                                    if (numRect == playerOneRectCoord.size() - 1) {
                                                        exceptionTV_good_text_func();
                                                        draw = true;
                                                        good = true;
                                                        Log.d("my", "новый прямоугольник включает старый сверху/снизу, но больше прямоугольников нет, стоп, всё хорошо");
                                                        break checkAll;
                                                    }
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    int upDownLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                    Log.d("my", "upLengthNow = " + upDownLengthNow);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        upLengthEmpty = upLengthEmpty - upDownLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        downLengthEmpty = downLengthEmpty - upDownLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "новый прямоугольник включает старый сверху/снизу. работаем дальше");
                                                    //continue checkAll;
                                                } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) <= oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) >= oneRectCoord.get(numPointSecondRect + 1).get(0)) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    good = true;
                                                    Log.d("my", "старый прямоугольник включает новый сверху/снизу. стоп, всё хорошо");
                                                    break checkAll;
                                                } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) > oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) > oneRectCoord.get(numPointSecondRect + 1).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) <= oneRectCoord.get(numPointSecondRect + 1).get(0)) {
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        int upLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                        Log.d("my", "upLengthNow = " + upLengthNow);
                                                        upLengthEmpty = upLengthEmpty - upLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        int downLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                        Log.d("my", "downLengthNow = " + downLengthNow);
                                                        downLengthEmpty = downLengthEmpty - downLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "торчит слева");
                                                    //continue checkAll;
                                                } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) < oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) < oneRectCoord.get(numPointSecondRect + 1).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) >= oneRectCoord.get(numPointSecondRect).get(0)) {
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        int upLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                        Log.d("my", "upLengthNow = " + upLengthNow);
                                                        upLengthEmpty = upLengthEmpty - upLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        int downLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                        Log.d("my", "downLengthNow = " + downLengthNow);
                                                        downLengthEmpty = downLengthEmpty - downLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "торчит справа");
                                                    //continue checkAll;
                                                }
                                                Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);
                                                if (upLengthEmpty <= 0 || downLengthEmpty <= 0) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    Log.d("my", "верхние/нижние отрицательны или = 0. стоп, всё хорошо");
                                                    break checkAll;
                                                } else {
                                                    if (!good) {
                                                        exceptionTV_bad_LocationException_text_func();
                                                        Log.d("my", "верхние/нижние неотрицательны. стоп, всё плохо");
                                                        pointFlag = false;
                                                    }
                                                    //continue checkAll;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (pointFlag) {
                                Log.d("my", "Х = У и всё хорошо");
                                exceptionTV_good_text_func();
                                draw = true;
                            } else {
                                Log.d("my", "Х != У");
                                exceptionTV_bad_LocationException_text_func();
                            }
                        } else {//проверка граней старта
                            Log.d("my", "проверены грани старта:");
                            if (x + partCanvasWidth == NumOfSquareInWidth*partCanvasWidth && y + partCanvasWidth == NumOfSquareInHeight *partCanvasWidth) {
                                exceptionTV_good_text_func();
                                Log.d("my", "праильное расположение");
                                draw = true;
                            } else {
                                exceptionTV_bad_LocationException_text_func();
                                Log.d("my", "неправильное расположение");
                            }

                        }
                        //
                    }
                }
                if (touchX > x + partCanvasWidth) { // проверка выхода за границы сетки (если за границами клеточек)
                    if (touchY > y + partCanvasWidth) {
                        exceptionTV_bad_OutOfBoundsException_text_func();
                        draw = false;
                    }
                }
            }
        }
    }

    public static void playerTwoChecker() { //проверка расположения прямоугольника 2 игрока (не работает)
        for (int x = 0; x < NumOfSquareInWidth * partCanvasWidth; x=x+partCanvasWidth) {
            for (int y = 0; y < NumOfSquareInHeight * partCanvasWidth; y = y + partCanvasWidth) {
                if ((touchX >= x && touchX <= x + partCanvasWidth)) {
                    if (touchY >= y && touchY <= y + partCanvasWidth) {
                        if (x + partCanvasWidth*rectSide1 <= width && y + partCanvasWidth*rectSide2 <= height) { //проверка выхода за границы сетки (здесь: и экрана тоже)
                            exceptionTV_good_text_func();
                            draw = true;
                            { // запоминание координат всех точек:
                                oneRectCoord = new ArrayList<>();
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x);
                                onePointCoord.add(y);
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x + partCanvasWidth);
                                onePointCoord.add(y);
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x);
                                onePointCoord.add(y + partCanvasWidth);
                                oneRectCoord.add(onePointCoord);
                                onePointCoord = new ArrayList<>();
                                onePointCoord.add(x + partCanvasWidth*rectSide1);
                                onePointCoord.add(y + partCanvasWidth*rectSide2);
                                oneRectCoord.add(onePointCoord);
                            }
                        } else {
                            exceptionTV_bad_OutOfBoundsException_text_func();
                            draw = false;
                        }
                        if (playerTwoRectCoord.size() > 0 && draw) {
                            boolean pointFlag = false;
                            int rightLengthEmpty = partCanvasWidth*rectSide2;
                            int leftLengthEmpty = rightLengthEmpty;
                            int upLengthEmpty = partCanvasWidth*rectSide1;
                            int downLengthEmpty = upLengthEmpty;

                            Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                            Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);

                            checkAll: for (int numRect = 0; numRect < playerTwoRectCoord.size(); numRect++) {
                                for (int numPointFirstRect = 0; numPointFirstRect < 4; numPointFirstRect++) {
                                    for (int numPointSecondRect = 0; numPointSecondRect < 4; numPointSecondRect++) {
                                        boolean good = false;
                                        //Log.d("my", "old_X: " + playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0));
                                        //Log.d("my", "new_X: " + oneRectCoord.get(numPointSecondRect).get(0));
                                        Log.d("my", "numRect: " + numRect);
                                        //проверка прямоугольников на наезды друг на друга: (не работает)
                                        if (((oneRectCoord.get(0).get(0) <= playerTwoRectCoord.get(numRect).get(0).get(0) && oneRectCoord.get(0).get(1) <= playerTwoRectCoord.get(numRect).get(0).get(1)) && (oneRectCoord.get(3).get(0) >= playerTwoRectCoord.get(numRect).get(3).get(0) && oneRectCoord.get(3).get(1) >= playerTwoRectCoord.get(numRect).get(3).get(1))) ||
                                                ((oneRectCoord.get(0).get(0) >= playerTwoRectCoord.get(numRect).get(0).get(0) && oneRectCoord.get(0).get(1) >= playerTwoRectCoord.get(numRect).get(0).get(1)) && (oneRectCoord.get(3).get(0) <= playerTwoRectCoord.get(numRect).get(3).get(0) && oneRectCoord.get(3).get(1) <= playerTwoRectCoord.get(numRect).get(3).get(1))) ){
                                            exceptionTV_bad_LocationException_text_func();
                                            Log.d("my", "прямоугольники внутри друг друга. стоп, всё плохо");
                                            pointFlag = false;
                                            break checkAll;
                                        }
                                        if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0).equals(oneRectCoord.get(numPointSecondRect).get(0))) {//одинаковые Х, проверяем по Y
                                            Log.d("my", "одинаковые Х найдены, далее идёт: ");
                                            if (numPointFirstRect > 1 || numPointSecondRect > 1) {
                                                Log.d("my", "не допустимые точки");
                                                //continue checkAll;
                                            } else {
                                                Log.d("my", "одинаковые Х, проверяем по У");
                                                pointFlag = true;
                                                if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1) <= oneRectCoord.get(numPointSecondRect).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) >= oneRectCoord.get(numPointSecondRect + 2).get(1)) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    good = true;
                                                    Log.d("my", "старый прямоугольник включает новый сбоку. стоп, всё хорошо");
                                                    break checkAll;
                                                } else if ((playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1) >= oneRectCoord.get(numPointSecondRect).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) <= oneRectCoord.get(numPointSecondRect + 2).get(1))) {
                                                    if (numRect == playerTwoRectCoord.size() - 1) {
                                                        exceptionTV_good_text_func();
                                                        draw = true;
                                                        good = true;
                                                        Log.d("my", "новый прямоугольник включает старый сбоку, но больше прямоугольников нет. стоп, всё хорошо");
                                                        break checkAll;
                                                    }
                                                    Log.d("my", "rightLengthEmpty = " + rightLengthEmpty + " leftLengthEmpty = " + leftLengthEmpty);
                                                    int lengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                    Log.d("my", "lengthNow = " + lengthNow);
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        rightLengthEmpty = rightLengthEmpty - lengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        leftLengthEmpty = leftLengthEmpty - lengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + rightLengthEmpty);
                                                    }
                                                    Log.d("my", "новый прямоугольник включает старый сбоку. работаем дальше");
                                                    //continue checkAll;
                                                } else if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1) > oneRectCoord.get(numPointSecondRect).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) > oneRectCoord.get(numPointSecondRect + 2).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1) <= oneRectCoord.get(numPointSecondRect + 2).get(1)) {
                                                    Log.d("my", "rightLengthEmpty = " + rightLengthEmpty + " leftLengthEmpty = " + leftLengthEmpty);
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        int rightLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                        Log.d("my", "rightLengthNow = " + rightLengthNow);
                                                        rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        int leftLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                        Log.d("my", "leftLengthNow = " + leftLengthNow);
                                                        leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + leftLengthEmpty);
                                                    }
                                                    Log.d("my", "новый торчит сверху");
                                                    //continue checkAll;
                                                } else if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1) < oneRectCoord.get(numPointSecondRect).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) < oneRectCoord.get(numPointSecondRect + 2).get(1) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) >= oneRectCoord.get(numPointSecondRect).get(1)) {
                                                    if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                        int rightLengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                        Log.d("my", "rightLengthNow = " + rightLengthNow);
                                                        rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                        Log.d("my", "rightLengthEmpty = " + rightLengthEmpty);
                                                    } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                        int leftLengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                        Log.d("my", "leftLengthNow = " + leftLengthNow);
                                                        leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                        Log.d("my", "leftLengthEmpty = " + leftLengthEmpty);
                                                    }
                                                    Log.d("my", "новый торчит снизу");
                                                    //continue checkAll;
                                                }
                                                Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                                                if (rightLengthEmpty <= 0 || leftLengthEmpty <= 0) {
                                                    good = true;
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    Log.d("my", "левые/правые отрицательные или = 0. стоп, всё хорошо");
                                                    break checkAll;
                                                } else {
                                                    good = false;
                                                    exceptionTV_bad_LocationException_text_func();
                                                    Log.d("my", "левые/правые не отрицательные. всё плохо");
                                                    pointFlag = false;
                                                    //continue checkAll;
                                                }
                                            }
                                        }
                                        if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(1).equals(oneRectCoord.get(numPointSecondRect).get(1))) {// одинаковые У, проверяем по X
                                            Log.d("my", "одинаковые У найдены, далее идёт: ");
                                            if (numPointFirstRect % 2 != 0 || numPointSecondRect % 2 != 0) {
                                                Log.d("my", "не допустимые точки");
                                                //continue checkAll;
                                            } else {
                                                Log.d("my", "одинаковые У, проверяем по Х");
                                                pointFlag = true;
                                                if ((playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0) >= oneRectCoord.get(numPointSecondRect).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) <= oneRectCoord.get(numPointSecondRect + 1).get(0))) {
                                                    if (numRect == playerTwoRectCoord.size() - 1) {
                                                        exceptionTV_good_text_func();
                                                        draw = true;
                                                        good = true;
                                                        Log.d("my", "новый прямоугольник включает старый сверху/снизу, но больше прямоугольников нет, стоп, всё хорошо");
                                                        break checkAll;
                                                    }
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    int upDownLengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                    Log.d("my", "upLengthNow = " + upDownLengthNow);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        upLengthEmpty = upLengthEmpty - upDownLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        downLengthEmpty = downLengthEmpty - upDownLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "новый прямоугольник включает старый сверху/снизу. работаем дальше");
                                                    //continue checkAll;
                                                } else if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0) <= oneRectCoord.get(numPointSecondRect).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) >= oneRectCoord.get(numPointSecondRect + 1).get(0)) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    good = true;
                                                    Log.d("my", "старый прямоугольник включает новый сверху/снизу. стоп, всё хорошо");
                                                    break checkAll;
                                                } else if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0) > oneRectCoord.get(numPointSecondRect).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) > oneRectCoord.get(numPointSecondRect + 1).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0) <= oneRectCoord.get(numPointSecondRect + 1).get(0)) {
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        int upLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                        Log.d("my", "upLengthNow = " + upLengthNow);
                                                        upLengthEmpty = upLengthEmpty - upLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        int downLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                        Log.d("my", "downLengthNow = " + downLengthNow);
                                                        downLengthEmpty = downLengthEmpty - downLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "торчит слева");
                                                    //continue checkAll;
                                                } else if (playerTwoRectCoord.get(numRect).get(numPointFirstRect).get(0) < oneRectCoord.get(numPointSecondRect).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) < oneRectCoord.get(numPointSecondRect + 1).get(0) && playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) >= oneRectCoord.get(numPointSecondRect).get(0)) {
                                                    Log.d("my", "upLengthEmpty = " + upLengthEmpty + " downLengthEmpty = " + downLengthEmpty);
                                                    if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                        int upLengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                        Log.d("my", "upLengthNow = " + upLengthNow);
                                                        upLengthEmpty = upLengthEmpty - upLengthNow;
                                                        Log.d("my", "upLengthEmpty = " + upLengthEmpty);
                                                    } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                        int downLengthNow = playerTwoRectCoord.get(numRect).get(numPointFirstRect + 1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                        Log.d("my", "downLengthNow = " + downLengthNow);
                                                        downLengthEmpty = downLengthEmpty - downLengthNow;
                                                        Log.d("my", "downLengthEmpty = " + downLengthEmpty);
                                                    }
                                                    Log.d("my", "торчит справа");
                                                    //continue checkAll;
                                                }
                                                Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);
                                                if (upLengthEmpty <= 0 || downLengthEmpty <= 0) {
                                                    exceptionTV_good_text_func();
                                                    draw = true;
                                                    Log.d("my", "верхние/нижние отрицательны или = 0. стоп, всё хорошо");
                                                    break checkAll;
                                                } else {
                                                    if (!good) {
                                                        exceptionTV_bad_LocationException_text_func();
                                                        Log.d("my", "верхние/нижние неотрицательны. стоп, всё плохо");
                                                        pointFlag = false;
                                                    }
                                                    //continue checkAll;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (pointFlag) {
                                Log.d("my", "Х = У и всё хорошо");
                                exceptionTV_good_text_func();
                                draw = true;
                            } else {
                                Log.d("my", "Х != У");
                                exceptionTV_bad_LocationException_text_func();
                            }
                        } else {//проверка граней старта
                            if (x == 0 && y == 0) {
                                exceptionTV_good_text_func();
                                draw = true;
                            } else {
                                exceptionTV_bad_LocationException_text_func();
                            }
                        }
                    }
                }
                /*
                if (touchX < x) { // проверка выхода за границы сетки (если за границами клеточек)
                    if (touchY < y) {
                        exceptionTV_bad_OutOfBoundsException_text_func();
                        draw = false;
                    }
                }
                */
            }
        }
    } // не работает


    DrawThread dt;

    class DrawThread extends Thread {
        boolean runFlag = true;
        SurfaceHolder surfaceHolder;
        Paint p = new Paint();

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (runFlag) {
                Canvas canvas = surfaceHolder.lockCanvas(null);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);

                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);

                    width = canvas.getWidth();//ширина экрана (правильнее сказать, канвы)
                    height = canvas.getHeight();//высота экрана (правильнее сказать, канвы)

                    partCanvasWidth = (int) (width/20);//размер квадрата сетки, равный 1/20 ширины экрана (канвы)

                    for (int x = 0; x < 20*partCanvasWidth; x=x+partCanvasWidth) {
                        for (int y = 0; y < 28 * partCanvasWidth; y = y + partCanvasWidth) {
                            canvas.drawRect(x, y, x + partCanvasWidth, y + partCanvasWidth, p);// сетка
                        }
                    }

                    drawStartLines(canvas);//рисую линии старта

                    try {
                        drawPlayerOneOldRectangle(canvas);// рисую старые прямоугольники 1 игрока
                        drawPlayerTwoOldRectangle(canvas);// рисую старые прямоугольники 2 игрока
                    } catch (Exception ignored) {}

                    if (playerFlag.equals("Player_1")) {
                        drawPlayerOneRectangleNow(partCanvasWidth, canvas);//рисую прямоугольник 1 игрока сейчас
                    } else if (playerFlag.equals("Player_2")) {
                        drawPlayerTwoRectangleNow(partCanvasWidth, canvas);//рисую прямоугольник 2 игрока сейчас
                    }

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        void drawStartLines(Canvas canvas) {
            p.setColor(Color.RED);
            canvas.drawLine(0, 0, 0, partCanvasWidth, p);
            canvas.drawLine(1, 0, 1, partCanvasWidth, p);
            canvas.drawLine(2, 0, 2, partCanvasWidth, p);
            canvas.drawLine(3, 0, 3, partCanvasWidth, p);
            canvas.drawLine(0, 0, partCanvasWidth, 0, p);
            canvas.drawLine(0, 1, partCanvasWidth, 1, p);
            canvas.drawLine(0, 2, partCanvasWidth, 2, p);
            canvas.drawLine(0, 3, partCanvasWidth, 3, p);
            p.setColor(Color.BLUE);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth, NumOfSquareInWidth*partCanvasWidth - partCanvasWidth, NumOfSquareInHeight *partCanvasWidth, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-1, NumOfSquareInWidth*partCanvasWidth - partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-1, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-2, NumOfSquareInWidth*partCanvasWidth - partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-2, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-3, NumOfSquareInWidth*partCanvasWidth - partCanvasWidth, NumOfSquareInHeight *partCanvasWidth-3, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth, NumOfSquareInWidth*partCanvasWidth, NumOfSquareInHeight *partCanvasWidth - partCanvasWidth, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth-1, NumOfSquareInHeight *partCanvasWidth, NumOfSquareInWidth*partCanvasWidth-1, NumOfSquareInHeight *partCanvasWidth - partCanvasWidth, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth-2, NumOfSquareInHeight *partCanvasWidth, NumOfSquareInWidth*partCanvasWidth-2, NumOfSquareInHeight *partCanvasWidth - partCanvasWidth, p);
            canvas.drawLine(NumOfSquareInWidth*partCanvasWidth-3, NumOfSquareInHeight *partCanvasWidth, NumOfSquareInWidth*partCanvasWidth-3, NumOfSquareInHeight *partCanvasWidth - partCanvasWidth, p);
            p.setColor(Color.BLACK);
        }

        void drawPlayerOneOldRectangle(Canvas canvas) {
            for (int i = 0; i < playerOneRectCoord.size(); i++) {
                int startX = playerOneRectCoord.get(i).get(0).get(0);
                int startY = playerOneRectCoord.get(i).get(0).get(1);
                int stopX = playerOneRectCoord.get(i).get(3).get(0);
                int stopY = playerOneRectCoord.get(i).get(3).get(1);
                p.setColor(Color.BLUE);
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(startX, startY, stopX, stopY, p);
                p.setColor(Color.parseColor("#FF0303A8"));
                p.setStyle(Paint.Style.STROKE);
                canvas.drawRect(startX, startY, stopX, stopY, p);
                canvas.drawRect(startX + 1, startY + 1, stopX - 1, stopY - 1, p);
                canvas.drawRect(startX + 2, startY + 2, stopX - 2, stopY - 2, p);
                canvas.drawRect(startX + 3, startY + 3, stopX - 3, stopY - 3, p);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);
            }
        }

        void drawPlayerTwoOldRectangle(Canvas canvas) {
            for (int i = 0; i < playerTwoRectCoord.size(); i++) {
                int startX = playerTwoRectCoord.get(i).get(0).get(0);
                int startY = playerTwoRectCoord.get(i).get(0).get(1);
                int stopX = playerTwoRectCoord.get(i).get(3).get(0);
                int stopY = playerTwoRectCoord.get(i).get(3).get(1);
                p.setColor(Color.RED);
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(startX, startY, stopX, stopY, p);
                p.setColor(Color.parseColor("#FF810202"));
                p.setStyle(Paint.Style.STROKE);
                canvas.drawRect(startX, startY, stopX, stopY, p);
                canvas.drawRect(startX + 1, startY + 1, stopX - 1, stopY - 1, p);
                canvas.drawRect(startX + 2, startY + 2, stopX - 2, stopY - 2, p);
                canvas.drawRect(startX + 3, startY + 3, stopX - 3, stopY - 3, p);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);
            }
        }

        void drawPlayerOneRectangleNow(int partCanvasWidth, Canvas canvas) {
            if (draw) {
                for (int x = 0; x < NumOfSquareInWidth*partCanvasWidth; x=x+partCanvasWidth) {
                    for (int y = 0; y < NumOfSquareInHeight *partCanvasWidth; y=y+partCanvasWidth) {
                        if ((touchX >= x && touchX <= x + partCanvasWidth)) {
                            if (touchY >= y && touchY <= y + partCanvasWidth) {
                                p.setColor(Color.BLUE);
                                p.setStyle(Paint.Style.FILL);
                                canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1), y - partCanvasWidth*(rectSide2 - 1), x + partCanvasWidth, y + partCanvasWidth, p);
                                p.setColor(Color.parseColor("#FF0303A8"));
                                p.setStyle(Paint.Style.STROKE);
                                canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1), y - partCanvasWidth*(rectSide2 - 1), x + partCanvasWidth, y + partCanvasWidth, p);
                                canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1) + 1, y - partCanvasWidth*(rectSide2 - 1) + 1, x + partCanvasWidth - 1, y + partCanvasWidth - 1, p);
                                canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1) + 2, y - partCanvasWidth*(rectSide2 - 1) + 2, x + partCanvasWidth - 2, y + partCanvasWidth - 2, p);
                                canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1) + 3, y - partCanvasWidth*(rectSide2 - 1) + 3, x + partCanvasWidth - 3, y + partCanvasWidth - 3, p);
                                p.setColor(Color.BLACK);
                                p.setStyle(Paint.Style.STROKE);
                            }
                        }
                    }
                }
            }
        }

        void drawPlayerTwoRectangleNow(int partCanvasWidth, Canvas canvas) {
            if (draw) {
                for (int x = 0; x < NumOfSquareInWidth*partCanvasWidth; x=x+partCanvasWidth) {
                    for (int y = 0; y < NumOfSquareInHeight *partCanvasWidth; y=y+partCanvasWidth) {
                        if ((touchX >= x && touchX <= x + partCanvasWidth)) {
                            if (touchY >= y && touchY <= y + partCanvasWidth) {
                                p.setColor(Color.RED);
                                p.setStyle(Paint.Style.FILL);
                                canvas.drawRect(x, y, x + partCanvasWidth*rectSide1, y + partCanvasWidth*rectSide2, p);
                                p.setColor(Color.parseColor("#FF810202"));
                                p.setStyle(Paint.Style.STROKE);
                                canvas.drawRect(x, y, x + partCanvasWidth*rectSide1, y + partCanvasWidth*rectSide2, p);
                                canvas.drawRect(x + 1, y + 1, x + partCanvasWidth*rectSide1 - 1, y + partCanvasWidth*rectSide2 - 1, p);
                                canvas.drawRect(x + 2, y + 2, x + partCanvasWidth*rectSide1 - 2, y + partCanvasWidth*rectSide2 - 2, p);
                                canvas.drawRect(x + 3, y + 3, x + partCanvasWidth*rectSide1 - 3, y + partCanvasWidth*rectSide2 - 3, p);
                                p.setColor(Color.BLACK);
                                p.setStyle(Paint.Style.STROKE);
                            }
                        }
                    }
                }
            }
        }

    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        dt = new DrawThread(surfaceHolder);
        dt.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }

    /*
    public class ExceptionRectangle extends Thread {
        int partCanvasWidth;
        MainGameActivity mainGameActivity;
        public ExceptionRectangle(MainGameActivity mainGameActivity, int partCanvasWidth) {
            super();
            this.mainGameActivity = mainGameActivity;
            this.partCanvasWidth = partCanvasWidth;
        }

        Runnable exceptionTV_good_text_func = new Runnable() {
            @Override
            public void run() {
                exceptionFlag = true;
                mainGameActivity.exceptionTV_good_text_func();
            }
        };

        Runnable exceptionTV_bad_OutOfBoundsException_text_func = new Runnable() {
            @Override
            public void run() {
                exceptionFlag = false;
                mainGameActivity.exceptionTV_bad_OutOfBoundsException_text_func();
            }
        };

        @Override
        public void run() {
            if (exceptionFlag) {
                mainGameActivity.runOnUiThread(exceptionTV_good_text_func);
            } else { // else if (выход за границы)
                mainGameActivity.runOnUiThread(exceptionTV_bad_OutOfBoundsException_text_func);
            }
        }
    }
    */
}