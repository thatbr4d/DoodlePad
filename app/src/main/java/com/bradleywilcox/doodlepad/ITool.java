package com.bradleywilcox.doodlepad;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public interface ITool {
    public void setStart(float x, float y);
    public void setEnd(float x, float y);
    public void draw(Canvas canvas, Paint paint);
    public void reset();
}
