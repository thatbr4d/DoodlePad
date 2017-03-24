package com.bradleywilcox.doodlepad;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
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

    private ImageButton btnLineTool, btnRectTool, btnBrushTool, btnEraser;
    private SeekBar sbStrokeWidth;
    private Drawing drawingView;
    private Button btnPop, btnSubmit;
    private TextView txtViewColor, txtViewColor2, txtViewColor3;
    private ImageButton showColor,  btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    public int sent;


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
        btnEraser = (ImageButton) findViewById(R.id.btnEraser);
        sbStrokeWidth = (SeekBar) findViewById(R.id.sbStrokeWidth);
        drawingView = (Drawing) findViewById(R.id.drawing_view);

        txtViewColor2 = (TextView) findViewById(R.id.textViewcolor);
        txtViewColor3 = (TextView)findViewById(R.id.textViewcolor2);
        showColor = (ImageButton) findViewById(R.id.imageButtoncolor);

        btnPop = (Button) findViewById(R.id.buttonColor2);
        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
        btnBrushTool.setOnClickListener(this);
        btnEraser.setOnClickListener(this);
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
        showColor.setVisibility(View.VISIBLE);
        if (view == btnLineTool) {
            setViews(view);
        } else if (view == btnRectTool) {
            setViews(view);
        } else if (view == btnBrushTool) {
            setViews(view);
            sent = 1;
        }else if(view==btnEraser){
            drawingView.setTool(Drawing.Tools.eraser);
            setViews(view);
        }
        else if (view == btnPop) {
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

        popupWindow.showAsDropDown(btnPop, 50, -50);

    }

    public void setViews(View v)
    {
        if(v==btnLineTool)
        {
            drawingView.setTool(Drawing.Tools.line);
            txtViewColor3.setText("Line");
        }
        else if(v==btnRectTool)
        {
            drawingView.setTool(Drawing.Tools.rectangle);
            txtViewColor3.setText("Rectangle");
        }
        else if(v==btnBrushTool)
        {
            drawingView.setTool(Drawing.Tools.brush);
            txtViewColor3.setText("Brush");
        }
        else if(v==btnEraser) {
            txtViewColor3.setText("Eraser");
            showColor.setVisibility(View.INVISIBLE);
            drawingView.setupPaint(Color.WHITE);
            sbStrokeWidth.setProgress(sbStrokeWidth.getProgress());

        }

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


        txtViewColor = (TextView) pop.findViewById(R.id.txtViewpop2);
        btnSubmit = (Button) pop.findViewById(R.id.submitColor);

        btn1.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Black");
            drawingView.setupPaint(Color.BLACK);
            showColor.setImageResource(R.drawable.black);}});
        btn2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: White");
            drawingView.setupPaint(Color.WHITE);
                showColor.setImageResource(R.drawable.white);}});
        btn3.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Blue");
            drawingView.setupPaint(Color.BLUE);
                showColor.setImageResource(R.drawable.blue); }});
        btn4.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Cyan");
                drawingView.setupPaint(Color.CYAN);
                showColor.setImageResource(R.drawable.cyan);}});
        btn5.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Dark Gray");
                drawingView.setupPaint(Color.DKGRAY);
                showColor.setImageResource(R.drawable.darkgray);}});
        btn6.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Gray");
                drawingView.setupPaint(Color.GRAY);
                showColor.setImageResource(R.drawable.gray);}});
        btn7.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Light Gray");
                drawingView.setupPaint(Color.LTGRAY);
                showColor.setImageResource(R.drawable.lightgray); }});
        btn8.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Green");
                drawingView.setupPaint(Color.GREEN);
                showColor.setImageResource(R.drawable.green);}});
        btn9.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Magenta");
                drawingView.setupPaint(Color.MAGENTA);
                showColor.setImageResource(R.drawable.magenta); }});
        btn10.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Red");
                drawingView.setupPaint(Color.RED);
                showColor.setImageResource(R.drawable.red);}});
        btn11.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Yellow");
                drawingView.setupPaint(Color.YELLOW);
                showColor.setImageResource(R.drawable.yellow);}});
        btn12.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {txtViewColor.setText("Selected: Orange");
                drawingView.setupPaint(Color.parseColor("#ffa500"));
                showColor.setImageResource(R.drawable.orange);}});
        btnSubmit.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {if(sent==1)
                drawingView.setTool(Drawing.Tools.brush);
                popw.dismiss();
                sbStrokeWidth.setProgress(0);}});

    }


}






