package com.bradleywilcox.doodlepad;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Brad on 3/22/2017.
 */

public interface ITool {
    public void setStart(float x, float y);
    public void setEnd(float x, float y);
    public void draw(Canvas canvas, Paint paint);
    public void reset();
}
