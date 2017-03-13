package com.bradleywilcox.doodlepad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.Vector;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class Drawing extends View {

    public enum Tools{
        line,
        rectangle
    }

    private int currentWidth;
    private int currentHeight;

    private Paint paint;
    private Canvas drawingCanvas;
    private Bitmap bitmap;

    private float dpiPixel;
    private float x_down, y_down;

    private RectF rect;
    private Vector<Float> line;

    private Tools tool;

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
        paint.setStyle(Paint.Style.STROKE);

        rect = new RectF();
        line = new Vector();
        line.add(0, 1.0f);
        line.add(1, 1.0f);
        line.add(2, 1.0f);
        line.add(3, 1.0f);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);

        paint.setStrokeWidth(dpiPixel);

        setTool(Tools.line);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);


        canvas.drawBitmap(bitmap, 0, 0, paint);

        if(tool == Tools.rectangle)
            canvas.drawRect(rect, paint);
        else if(tool == Tools.line)
            canvas.drawLine(line.get(0), line.get(1), line.get(2), line.get(3), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                x_down = touchX;
                y_down = touchY;
                line.set(0, x_down);
                line.set(1, y_down);
                line.set(2, x_down);
                line.set(3, y_down);

                break;

            case MotionEvent.ACTION_MOVE:

                if(tool == Tools.rectangle)
                    //allow rectangle to be drawn in all directions
                    rect.set(x_down > touchX ? touchX : x_down,
                             y_down > touchY ? touchY : y_down,
                             touchX < x_down ? x_down : touchX,
                             touchY < y_down ? y_down : touchY);

                else if(tool == Tools.line) {
                    line.set(2, touchX);
                    line.set(3, touchY);
                }

                break;

            case MotionEvent.ACTION_UP:

                if(tool == Tools.rectangle)
                    drawingCanvas.drawRect(rect, paint);
                else if(tool == Tools.line) {
                    drawingCanvas.drawLine(line.get(0), line.get(1), line.get(2), line.get(3), paint);
                    line.set(0, -1.0f);
                    line.set(1, -1.0f);
                    line.set(2, -1.0f);
                    line.set(3, -1.0f);
                }

                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setTool(Tools tool){
        this.tool = tool;

        if(this.tool == Tools.rectangle){
            paint.setStyle(Paint.Style.FILL);
        }else if(this.tool == Tools.line){
            paint.setStyle(Paint.Style.STROKE);
        }
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
