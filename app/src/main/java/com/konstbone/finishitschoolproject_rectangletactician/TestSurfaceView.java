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
                        if (playerOneRectCoord.size() > 0) {// если есть прямоугольники рядом
                            boolean pointFlag = false;
                            int rightLengthEmpty = (y + partCanvasWidth) - (y - partCanvasWidth*(rectSide2-1));
                            int leftLengthEmpty = rightLengthEmpty;
                            int upLengthEmpty = (x + partCanvasWidth) - (x - partCanvasWidth*(rectSide1-1));
                            int downLengthEmpty = upLengthEmpty;

                            Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                            Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);

                            check: for (int numRect = 0; numRect < playerOneRectCoord.size(); numRect++) {
                                for (int numPointFirstRect = 0; numPointFirstRect < 4; numPointFirstRect++) {
                                    for (int numPointSecondRect = 0; numPointSecondRect < 4; numPointSecondRect++) {
                                        //Log.d("my", "in check");
                                        //Log.d("my", "old_X: " + playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0));
                                        //Log.d("my", "new_X: " + oneRectCoord.get(numPointSecondRect).get(0));
                                        if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0).equals(oneRectCoord.get(numPointSecondRect).get(0))) {// проверяем по Y
                                            Log.d("my", "good1");
                                            pointFlag = true;
                                            if ((playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) >= oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) <= oneRectCoord.get(numPointSecondRect+2).get(1)) ) {
                                                int lengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                    rightLengthEmpty = rightLengthEmpty - lengthNow;
                                                } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                    leftLengthEmpty = leftLengthEmpty - lengthNow;
                                                }
                                                Log.d("my", "я был в 1 if");
                                                continue check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) <= oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) >= oneRectCoord.get(numPointSecondRect+2).get(1)) {
                                                exceptionTV_good_text_func();
                                                draw = true;
                                                break check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) > oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) > oneRectCoord.get(numPointSecondRect+2).get(1)) {
                                                if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                    int rightLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                    rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                    int leftLengthNow = oneRectCoord.get(numPointSecondRect + 2).get(1) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1);
                                                    leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                }
                                                Log.d("my", "я был в 2 if");
                                                continue check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) < oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) < oneRectCoord.get(numPointSecondRect+2).get(1)) {
                                                if (numPointFirstRect == 0 && numPointSecondRect == 1) {
                                                    int rightLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                    rightLengthEmpty = rightLengthEmpty - rightLengthNow;
                                                } else if (numPointFirstRect == 1 && numPointSecondRect == 0) {
                                                    int leftLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) - oneRectCoord.get(numPointSecondRect).get(1);
                                                    leftLengthEmpty = leftLengthEmpty - leftLengthNow;
                                                }
                                                Log.d("my", "я был в 3 if");
                                                continue check;
                                            }
                                            Log.d("my", "rightLengthEmpty: " + rightLengthEmpty + "; leftLengthEmpty: " + leftLengthEmpty);
                                            if (rightLengthEmpty <= 0 || leftLengthEmpty <= 0) {
                                                exceptionTV_good_text_func();
                                                draw = true;
                                                Log.d("my", "я был в result if with good");
                                                break check;
                                            } else {
                                                exceptionTV_bad_LocationException_text_func();
                                                Log.d("my", "я был в result if with bad");
                                                break check;
                                            }
                                        } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1).equals(oneRectCoord.get(numPointSecondRect).get(1))) { // проверяем по X
                                            Log.d("my", "good2");
                                            pointFlag = true;
                                            if ((playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) >= oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) <= oneRectCoord.get(numPointSecondRect+1).get(0)) ) {
                                                int lengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                    upLengthEmpty = upLengthEmpty - lengthNow;
                                                } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                    downLengthEmpty = downLengthEmpty - lengthNow;
                                                }
                                                continue check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) <= oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) >= oneRectCoord.get(numPointSecondRect+1).get(0)) {
                                                exceptionTV_good_text_func();
                                                draw = true;
                                                break check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) > oneRectCoord.get(numPointSecondRect).get(0) && playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) > oneRectCoord.get(numPointSecondRect+1).get(0)) {
                                                if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                    int upLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                    upLengthEmpty = upLengthEmpty - upLengthNow;
                                                } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                    int downLengthNow = oneRectCoord.get(numPointSecondRect + 1).get(0) - playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0);
                                                    downLengthEmpty = downLengthEmpty - downLengthNow;
                                                }
                                                continue check;
                                            } else if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(1) < oneRectCoord.get(numPointSecondRect).get(1) && playerOneRectCoord.get(numRect).get(numPointFirstRect+2).get(1) < oneRectCoord.get(numPointSecondRect+2).get(1)) {
                                                if (numPointFirstRect == 2 && numPointSecondRect == 0) {
                                                    int upLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                    upLengthEmpty = upLengthEmpty - upLengthNow;
                                                } else if (numPointFirstRect == 0 && numPointSecondRect == 2) {
                                                    int downLengthNow = playerOneRectCoord.get(numRect).get(numPointFirstRect+1).get(0) - oneRectCoord.get(numPointSecondRect).get(0);
                                                    downLengthEmpty = downLengthEmpty - downLengthNow;
                                                }
                                                continue check;
                                            }
                                            Log.d("my", "upLengthEmpty: " + upLengthEmpty + "; downLengthEmpty: " + downLengthEmpty);
                                            if (upLengthEmpty <= 0 || downLengthEmpty <= 0) {
                                                exceptionTV_good_text_func();
                                                draw = true;
                                                break check;
                                            } else {
                                                exceptionTV_bad_LocationException_text_func();
                                                break check;
                                            }
                                        }
                                    }
                                }
                            }
                            if (pointFlag) {
                                Log.d("my", "я был в pointFlag good if");
                                exceptionTV_good_text_func();
                                draw = true;
                            } else {
                                Log.d("my", "я был в pointFlag bad if");
                                exceptionTV_bad_LocationException_text_func();
                            }
                        } else {//проверка граней старта
                            if (x + partCanvasWidth == NumOfSquareInWidth*partCanvasWidth && y + partCanvasWidth == NumOfSquareInHeight *partCanvasWidth) {
                                exceptionTV_good_text_func();
                                draw = true;
                            } else {
                                exceptionTV_bad_LocationException_text_func();
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

    public static void playerTwoChecker() { //проверка расположения прямоугольника 2 игрока
        for (int x = 0; x < NumOfSquareInWidth*partCanvasWidth; x=x+partCanvasWidth) {
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
                        if (playerTwoRectCoord.size() > 0) {
                            check: for (int numRect = 0; numRect < playerTwoRectCoord.size(); numRect++) {
                                for (int numPointFirstRect = 0; numPointFirstRect < 4; numPointFirstRect++) {
                                    for (int numPointSecondRect = 0; numPointSecondRect < 4; numPointSecondRect++) {
                                        if (playerOneRectCoord.get(numRect).get(numPointFirstRect).get(0) == oneRectCoord.get(numPointSecondRect).get(0)) {
                                            //
                                        }
                                    }
                                }
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
            }
        }
    }


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