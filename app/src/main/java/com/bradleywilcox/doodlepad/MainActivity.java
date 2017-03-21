package com.bradleywilcox.doodlepad;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 *
 * Additional Features
 *
 * 1.  Brush Tool, an extra tool that acts like a paint brush, allowing you to draw
 *     more than just a straight line.  Found in the class 'BrushTool'
 *
 * 2.
 *
 *
 * 3.
 *
 *
 * 4.
 *
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnLineTool, btnRectTool, btnBrushTool;
    private SeekBar sbStrokeWidth;
    private Drawing drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        btnLineTool = (ImageButton) findViewById(R.id.btnLineTool);
        btnRectTool = (ImageButton) findViewById(R.id.btnRectangleTool);
        btnBrushTool = (ImageButton) findViewById(R.id.btnBrushTool);
        sbStrokeWidth = (SeekBar) findViewById(R.id.sbStrokeWidth);
        drawingView = (Drawing) findViewById(R.id.drawing_view);

        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
        btnBrushTool.setOnClickListener(this);

        sbStrokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawingView.setStrokeWidth((float)progress);
            }
        });

        //set initial value
        drawingView.setStrokeWidth((float)sbStrokeWidth.getProgress());
    }

    @Override
    public void onClick(View view) {
        if(view == btnLineTool){
            drawingView.setTool(Drawing.Tools.line);
        }else if(view == btnRectTool){
            drawingView.setTool(Drawing.Tools.rectangle);
        }else if(view == btnBrushTool){
            drawingView.setTool(Drawing.Tools.brush);
        }
    }
}
