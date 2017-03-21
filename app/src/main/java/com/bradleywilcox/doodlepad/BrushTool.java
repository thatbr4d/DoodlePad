package com.bradleywilcox.doodlepad;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class BrushTool {
    private Path path;

    public BrushTool(){
        path = new Path();
    }

    public void setStart(float x, float y){
        path.moveTo(x, y);
    }

    public void setEnd(float x, float y){
        path.lineTo(x, y);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawPath(path, paint);
    }

    public void reset(){
        path.reset();
    }

}
