package com.bradleywilcox.doodlepad;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.UUID;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 */

public class Image {

    public static boolean Save(Drawing drawing, ContentResolver resolver){
        drawing.setDrawingCacheEnabled(true);

        String img = MediaStore.Images.Media.insertImage(resolver, drawing.getDrawingCache(), "doodle"+ UUID.randomUUID() +".png", "Created with DoodlePad");

        drawing.destroyDrawingCache();

        if(img == null) return false;
        return true;
    }

}
