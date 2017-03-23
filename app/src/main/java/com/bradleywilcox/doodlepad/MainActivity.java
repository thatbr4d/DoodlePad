package com.bradleywilcox.doodlepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 * <p>
 * Additional Features
 * <p>
 * 1.  Brush Tool, an extra tool that acts like a paint brush, allowing you to draw
 * more than just a straight line.  Found in the class 'BrushTool'
 * <p>
 * 2.
 * <p>
 * <p>
 * 3.
 * <p>
 * <p>
 * 4.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnLineTool, btnRectTool, btnBrushTool;
    private SeekBar sbStrokeWidth;
    private Drawing drawingView;
    private Button btnPop, btnSubmit;
    private TextView txtViewColor;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;

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

        btnPop = (Button) findViewById(R.id.buttonColor2);
        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
        btnBrushTool.setOnClickListener(this);
        btnPop.setOnClickListener(this);


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
                drawingView.setStrokeWidth((float) progress);
            }
        });

        //set initial value
        drawingView.setStrokeWidth((float) sbStrokeWidth.getProgress());

    }

    @Override
    public void onClick(View view) {
        if (view == btnLineTool) {
            drawingView.setTool(Drawing.Tools.line);
        } else if (view == btnRectTool) {
            drawingView.setTool(Drawing.Tools.rectangle);
        } else if (view == btnBrushTool) {
            drawingView.setTool(Drawing.Tools.brush);
        } else if (view == btnPop) {
            runPopup();
        }
    }


    public void runPopup() {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popupcolor, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        setimageBtns(popupView, popupWindow);

        popupWindow.showAsDropDown(btnPop, 50, -30);

    }


    public void setimageBtns(View pop, final PopupWindow popw) {

        btn1 = (ImageButton) pop.findViewById(R.id.iButtonC);
        btn2 = (ImageButton) pop.findViewById(R.id.iButtonC2);
        btn3 = (ImageButton) pop.findViewById(R.id.iButtonC3);
        btn4 = (ImageButton) pop.findViewById(R.id.iButtonC4);
        btn5 = (ImageButton) pop.findViewById(R.id.iButtonC5);
        btn6 = (ImageButton) pop.findViewById(R.id.iButtonC6);
        btn7 = (ImageButton) pop.findViewById(R.id.iButtonC7);
        btn8 = (ImageButton) pop.findViewById(R.id.iButtonC8);
        btn9 = (ImageButton) pop.findViewById(R.id.iButtonC9);
        btn10 = (ImageButton) pop.findViewById(R.id.iButtonC10);
        btn11 = (ImageButton) pop.findViewById(R.id.iButtonC11);
        btn12 = (ImageButton) pop.findViewById(R.id.iButtonC12);


        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);

        txtViewColor = (TextView) pop.findViewById(R.id.txtViewpop2);
        btnSubmit = (Button) pop.findViewById(R.id.submitColor);

        btn1.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Black");}});
        btn2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: White");}});
        btn3.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Blue");}});
        btn4.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Cyan");}});
        btn5.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Dark Gray");}});
        btn6.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Gray");}});
        btn7.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Light Gray");}});
        btn8.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Green");}});
        btn9.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Magenta");}});
        btn10.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Red");}});
        btn11.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Yellow");}});
        btn12.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Orange");}});
        btnSubmit.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {popw.dismiss();}});
    }


}






