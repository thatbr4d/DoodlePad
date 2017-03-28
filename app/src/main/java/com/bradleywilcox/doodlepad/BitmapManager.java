package com.bradleywilcox.doodlepad;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class BitmapManager {

    private static String STORAGE = "tmpBitmap.png";
    public static int MAX = 10;
    private ArrayList<Bitmap> Bitmaps;
    private Bitmap bitmap;

    public BitmapManager(int w, int h, Context context, boolean isReset){
        Bitmaps = new ArrayList<>();
        Bitmap tmp = loadNewest(context);
        Bitmaps.add(0, tmp == null || isReset ? bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) : tmp.copy(tmp.getConfig(), true));
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

    public void recycle(){
        for(Bitmap b : Bitmaps){
            b.recycle();
        }
        Bitmaps = null;
    }

    public static void saveNewest(Bitmap b, Context context){
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(STORAGE, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {

        } finally {
            try {
                fos.close();
            } catch (IOException e) {

            }
        }
    }

    public Bitmap loadNewest(Context context){
        try {
            FileInputStream fis = context.openFileInput(STORAGE);
            Bitmap b = BitmapFactory.decodeStream(fis);

            return b;
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
    }
}
