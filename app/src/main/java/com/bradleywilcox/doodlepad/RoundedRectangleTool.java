package com.bradleywilcox.doodlepad;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;



public class RoundedRectangleTool extends RectangleTool {
    private float cornerRd;

    public RoundedRectangleTool(float dpi){
        super();
        cornerRd = dpi * 10;
    }
    @Override
    public void draw(Canvas canvas, Paint paint){

        canvas.drawRoundRect(rect, cornerRd, cornerRd, paint);
    }
}
