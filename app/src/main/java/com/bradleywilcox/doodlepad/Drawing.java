package com.bradleywilcox.doodlepad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.telephony.CellIdentityCdma;
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
        eraser,
        round_rectangle,
        circle
    }

    private int currentWidth;
    private int currentHeight;

    private Paint paint, backgroundPaint;
    private Canvas drawingCanvas;

    private BitmapManager bitmaps;

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

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);

        tools[Tools.line.ordinal()] = new LineTool();
        tools[Tools.rectangle.ordinal()] = new RectangleTool();
        tools[Tools.brush.ordinal()] = new BrushTool();
        tools[Tools.eraser.ordinal()] = new EraserTool();
        tools[Tools.round_rectangle.ordinal()] = new RoundedRectangleTool(dpiPixel);
        tools[Tools.circle.ordinal()] = new CircleTool();

        setTool(Tools.line);
    }

    public void setupPaint(int kolor)
    {
        paint.setColor(kolor);
    }

    public void setBackg(int kolor)
    {
        backgroundPaint.setColor(kolor);
        invalidate();
    }

    public void setEraserColor(int color){
        EraserTool eraser = (EraserTool)tools[Tools.eraser.ordinal()];
        eraser.setEraserColor(color);
    }

    public void reset(){
        setTool(Tools.line);
        setupPaint(Color.RED);
        bitmaps.recycle();
        bitmaps = new BitmapManager(currentWidth, currentHeight, getContext(), true);
        setBackg(Color.WHITE);
    }

    /**
     * Draws the top bitmap to the screen along with any inprogress actions by the user
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawRect(0, 0, currentWidth, currentHeight, backgroundPaint);
        canvas.drawBitmap(bitmaps.getNewest(), 0, 0, paint);
        tools[currentTool.ordinal()].draw(canvas, paint);
    }

    /**
     * Handles all actions performed from the user on the drawing canvas
     * @param event
     * @return
     */
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

                bitmaps.copyAndAdd();
                drawingCanvas.setBitmap(bitmaps.getNewest());

                tools[currentTool.ordinal()].draw(drawingCanvas, paint);
                tools[currentTool.ordinal()].reset();
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    /**
     * Receives the stroke width to be used on all tools
     * @param width
     */
    public void setStrokeWidth(float width){
        paint.setStrokeWidth(width * dpiPixel);
        EraserTool et = (EraserTool)tools[Tools.eraser.ordinal()];
        et.setEraserStroke(width * dpiPixel);
    }

    /**
     * Receives the current tool to draw with
     * @param tool
     */
    public void setTool(Tools tool){
        this.currentTool = tool;

        if(this.currentTool == Tools.rectangle || this.currentTool == Tools.round_rectangle || this.currentTool == Tools.circle){
            paint.setStyle(Paint.Style.FILL);
        }else if(this.currentTool == Tools.line){
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setStrokeJoin(Paint.Join.MITER);
        }else if(this.currentTool == Tools.brush){
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
    }

    public Tools getTool(){
        return this.currentTool;
    }

    /**
     * Deletes the newest bitmap and sets the canvas to previous
     */
    public void performUndo(){
        if(bitmaps != null && bitmaps.size() > 1) {

            bitmaps.removeNewest();
            drawingCanvas.setBitmap(bitmaps.getNewest());

            invalidate();
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

        bitmaps = new BitmapManager(w, h, getContext(), false);
        drawingCanvas = new Canvas(bitmaps.getNewest());
    }

    public Bitmap getNewestBitmap(){
        return bitmaps.getNewest();
    }
}
