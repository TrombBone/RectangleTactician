package com.konstbone.finishitschoolproject_rectangletactician;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.exceptionFlag;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.rectSide1;
import static com.konstbone.finishitschoolproject_rectangletactician.MainGameActivity.rectSide2;


public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    public TestSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
    }

    float touchX, touchY;
    boolean v = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        v = true;
        return true;
    }

    DrawThread dt;

    class DrawThread extends Thread {
        boolean runFlag = true;
        SurfaceHolder surfaceHolder;
        Paint p = new Paint();

        MainGameActivity mainGameActivity = new MainGameActivity();

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            //((Activity)getContext()).runOnUiThread(exceptionTV_good_text_func);
            while (runFlag) {
                Canvas canvas = surfaceHolder.lockCanvas(null);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);

                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);

                    float width = canvas.getWidth();
                    float height = canvas.getHeight();// сетка

                    int a = (int) (width/20);

                    drawPlayerOneRectangle(a, canvas);

                    for (int x = 0; x < 20*a; x=x+a) {
                        for (int y = 0; y < 28 * a; y = y + a) {
                            canvas.drawRect(x, y, x + a, y + a, p);
                        }
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        public void drawPlayerOneRectangle(int partCanvasWidth, Canvas canvas) {
            if (v) {
                for (int x = 0; x < 20*partCanvasWidth; x=x+partCanvasWidth) {
                    for (int y = 0; y < 28*partCanvasWidth; y=y+partCanvasWidth) {
                        if ((touchX >= x && touchX <= x + partCanvasWidth)) {
                            if (touchY >= y && touchY <= y + partCanvasWidth) {
                                p.setColor(Color.BLUE);
                                p.setStyle(Paint.Style.FILL);
                                if ((x - partCanvasWidth*rectSide1 >= 0 && y - partCanvasWidth*rectSide2 >= 0)||(x == 0 && y == 0)) {
                                    canvas.drawRect(x - partCanvasWidth*(rectSide1 - 1), y - partCanvasWidth*(rectSide2 - 1), x + partCanvasWidth, y + partCanvasWidth, p);
                                    //писать в TextView о том, что всё хорошо и красить его в зелёный
                                    //
                                    ExceptionRectangle exceptionRectangle = new ExceptionRectangle(mainGameActivity);
                                    exceptionFlag = true;
                                    exceptionRectangle.start();
                                    try {
                                        exceptionRectangle.join();
                                    } catch (InterruptedException e) { }
                                } else {
                                    exceptionFlag = false;
                                    //писать в TextView об ошибке и красить его в красный
                                }
                                //
                                //
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

    public class ExceptionRectangle extends Thread {
        MainGameActivity mainGameActivity;
        public ExceptionRectangle(MainGameActivity mainGameActivity) {
            super();
            this.mainGameActivity = mainGameActivity;
        }

        public void exceptionTV_good_text_func() {
            exceptionFlag = true;
            mainGameActivity.exceptionTV_good_text_func();
        }

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
                exceptionTV_good_text_func();
            }
        }
    }
}