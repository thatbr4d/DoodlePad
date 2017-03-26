package com.bradleywilcox.doodlepad;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class BitmapManager {

    public static int MAX = 10;
    private ArrayList<Bitmap> Bitmaps;
    private Bitmap bitmap;

    public BitmapManager(int w, int h){
        Bitmaps = new ArrayList<>();
        Bitmaps.add(0, bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888));
    }

    public Bitmap getNewest(){
        return Bitmaps.get(Bitmaps.size()-1);
    }

    public void copyAndAdd(){
        Bitmap curBitmap = getNewest();
        Bitmaps.add(Bitmaps.size(), curBitmap.copy(curBitmap.getConfig(), true));

        checkForMax();
    }

    private void checkForMax(){
        if(Bitmaps.size() > MAX){
            //get oldest bitmap
            Bitmap b = Bitmaps.get(0);
            //remove by index, automatically shifts subsequent elements to the left
            Bitmaps.remove(0);
            //recycle the bitmap
            b.recycle();
        }
    }

    public void removeNewest(){
        Bitmap del = getNewest();
        Bitmaps.remove(del);
        del.recycle();
    }

    public int size(){
        return Bitmaps.size();
    }
}
