package com.bradleywilcox.doodlepad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class Drawing extends View {

    private int currentWidth;
    private int currentHeight;

    private Paint paint;
    private Canvas drawingCanvas;
    private Bitmap bitmap;

    private float dpiPixel;
    private float x_down, y_down;

    private RectF rect;


    public Drawing(Context context) {
        super(context);
        setup(null);
    }

    public Drawing(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public Drawing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        rect = new RectF();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x_down = touchX;
                y_down = touchY;

                break;
            case MotionEvent.ACTION_MOVE:

                //allow rectangle to be drawn in all directions
                rect.set(x_down > touchX ? touchX : x_down,
                         y_down > touchY ? touchY : y_down,
                         touchX < x_down ? x_down : touchX,
                         touchY < y_down ? y_down : touchY);

                break;
            case MotionEvent.ACTION_UP:
                drawingCanvas.drawRect(rect, paint);
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        currentWidth = w;
        currentHeight = h;

        bitmap = bitmap.createBitmap(currentWidth, currentHeight, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(bitmap);

    }
}
