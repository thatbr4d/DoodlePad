package com.bradleywilcox.doodlepad;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RectangleTool implements ITool {

    protected RectF rect;
    protected float startX;
    protected float startY;

    public RectangleTool() {
        rect = new RectF();
    }

    public void setStart(float x, float y){
        startX = x;
        startY = y;
    }

    public void setEnd(float x, float y){
        //allow rectangle to be drawn in all directions
        rect.set(startX > x ? x : startX,
                startY > y ? y : startY,
                x < startX ? startX : x,
                y < startY ? startY : y);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawRect(rect, paint);
    }

    public void reset() {
        rect.set(-1.0f, -1.0f, -1.0f, -1.0f);
    }


}
