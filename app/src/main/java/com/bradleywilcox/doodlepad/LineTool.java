package com.bradleywilcox.doodlepad;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Vector;

public class LineTool implements ITool {

    private Vector<Float> line;

    public LineTool(){
        line = new Vector<>();
        line.add(0, -1.0f);
        line.add(1, -1.0f);
        line.add(2, -1.0f);
        line.add(3, -1.0f);
    }

    public void reset(){
        line.set(0, -1.0f);
        line.set(1, -1.0f);
        line.set(2, -1.0f);
        line.set(3, -1.0f);
    }

    public void setStart(float x, float y){
        line.set(0, x);
        line.set(1, y);
        line.set(2, x);
        line.set(3, y);
    }

    public void setEnd(float x, float y){
        line.set(2, x);
        line.set(3, y);
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawLine(line.get(0), line.get(1), line.get(2), line.get(3), paint);
    }
}
