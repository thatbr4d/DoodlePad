package com.bradleywilcox.doodlepad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class Drawing extends View {

    public enum Tools{
        line,
        rectangle,
        brush,
        eraser
    }

    private int currentWidth;
    private int currentHeight;

    private Paint paint;
    private Canvas drawingCanvas;
    private Bitmap bitmap;
    private EraserTool eraserTool;

    private float dpiPixel;

    private ITool[] tools = new ITool[Tools.values().length];
    private Tools currentTool;

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

        tools[Tools.line.ordinal()] = new LineTool();
        tools[Tools.rectangle.ordinal()] = new RectangleTool();
        tools[Tools.brush.ordinal()] = new BrushTool();
        tools[Tools.eraser.ordinal()] = new EraserTool();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);

        setTool(Tools.line);
    }

    public void setupPaint(int kolor)
    {
        paint.setColor(kolor);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, paint);
        tools[currentTool.ordinal()].draw(canvas, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                tools[currentTool.ordinal()].setStart(touchX, touchY);
                break;

            case MotionEvent.ACTION_MOVE:

                tools[currentTool.ordinal()].setEnd(touchX, touchY);
                break;

            case MotionEvent.ACTION_UP:

                tools[currentTool.ordinal()].draw(drawingCanvas, paint);
                tools[currentTool.ordinal()].reset();
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setStrokeWidth(float width){
        paint.setStrokeWidth(width * dpiPixel);
        EraserTool et = (EraserTool)tools[Tools.eraser.ordinal()];
        et.setEraserStroke(width * dpiPixel);
    }

    public void setTool(Tools tool){
        this.currentTool = tool;

        if(this.currentTool == Tools.rectangle){
            paint.setStyle(Paint.Style.FILL);
        }else if(this.currentTool == Tools.line){
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setStrokeJoin(Paint.Join.MITER);
        }else if(this.currentTool == Tools.brush){
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }else if(this.currentTool == Tools.eraser) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
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
