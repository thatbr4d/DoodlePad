package com.bradleywilcox.doodlepad;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class EraserTool implements ITool {
    private Path path;
    private Paint paint;

    public EraserTool(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.WHITE);
        path = new Path();
    }

    public void setStart(float x, float y){
        path.moveTo(x, y);
    }

    public void setEnd(float x, float y){
        path.lineTo(x, y);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawPath(path, this.paint);
    }

    public void reset(){
        path.reset();
    }

    public void setEraserStroke(float stroke){
        paint.setStrokeWidth(stroke);
    }

}
