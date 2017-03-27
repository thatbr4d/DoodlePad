package com.bradleywilcox.doodlepad;

import android.graphics.Canvas;
import android.graphics.Paint;


public class CircleTool implements ITool {

    private float startX;
    private float startY;
    private float radiusX;
    private float radiusY;

    @Override
    public void setStart(float x, float y) {
        startX = x;
        startY = y;
    }

    @Override
    public void setEnd(float x, float y) {
        radiusX = Math.abs(x - startX);
        radiusY = Math.abs(y - startY);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(startX, startY, radiusX > radiusY ? radiusX : radiusY, paint);
    }

    @Override
    public void reset() {
        radiusX = 0;
        radiusY = 0;
    }
}
