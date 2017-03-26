package com.bradleywilcox.doodlepad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.bradleywilcox.doodlepad.R.id.btnBgC1;
import static com.bradleywilcox.doodlepad.R.id.txtViewpop2;


/**
 * Bradley Wilcox / Michael Cha
 * CSCI 4020
 * Assignment 3
 *
 * Additional Features
 *
 * 1.  Brush Tool, an extra tool that acts like a paint brush, allowing you to draw
 * more than just a straight line.  Found in the class 'BrushTool'
 *
 * 2.  Save Feature, the save button will save the current drawing into the phones
 * gallery.  Found in the class 'Image'.  Also required requestPermissions found in this class for sdk >= 23
 *
 *
 * 3.  Undo Feature, the undo button will undo the users most recent actions, one at a time, much like the common 'ctrl-z',
 * most of the code for this feature is found in the class 'BitmapManager' and is used throughout the 'Drawing' class
 *
 *
 * 4.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton btnLineTool, btnRectTool, btnBrushTool, btnSave, btnErase, btnUndo;
    private SeekBar sbStrokeWidth;
    private Drawing drawingView;
    private Button btnColor, btnSubmit;
    private TextView txtViewColor, txtViewColor2, txtViewColor3, txtViewColor4;
    private ImageView showColor, showbgColor;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12,
            bgbtn1, bgbtn2, bgbtn3, bgbtn4, bgbtn5, bgbtn6, bgbtn7, bgbtn8, bgbtn9, bgbtn10, bgbtn11, bgbtn12 ;
    private Integer setVal = 10, bgVal = 2;

    private boolean hasExtPermission = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        setContentView(R.layout.activity_main);

        btnLineTool = (ImageButton) findViewById(R.id.btnLineTool);
        btnRectTool = (ImageButton) findViewById(R.id.btnRectangleTool);
        btnBrushTool = (ImageButton) findViewById(R.id.btnBrushTool);
        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnErase = (ImageButton) findViewById(R.id.btnErase);
        btnUndo = (ImageButton) findViewById(R.id.btnUndo);

        sbStrokeWidth = (SeekBar) findViewById(R.id.sbStrokeWidth);
        drawingView = (Drawing) findViewById(R.id.drawing_view);

        txtViewColor2 = (TextView) findViewById(R.id.textViewcolor);
        txtViewColor3 = (TextView)findViewById(R.id.textViewcolor2);
        showColor = (ImageView) findViewById(R.id.imageButtoncolor);
        showbgColor = (ImageView) findViewById(R.id.imageButtoncolor2);

        btnColor = (Button) findViewById(R.id.buttonColor);
        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
        btnBrushTool.setOnClickListener(this);
        btnErase.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnUndo.setOnClickListener(this);

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
            txtViewColor3.setText("Line");
            setPopVal(setVal, 0);
        } else if (view == btnRectTool) {
            drawingView.setTool(Drawing.Tools.rectangle);
            txtViewColor3.setText("Rectangle");
            setPopVal(setVal, 0);
        } else if (view == btnBrushTool) {
            drawingView.setTool(Drawing.Tools.brush);
            txtViewColor3.setText("Brush");
            setPopVal(setVal, 0);
        } else if (view == btnColor) {
            runPopup();
        }else if(view == btnErase) {
            drawingView.setTool(Drawing.Tools.eraser);
            txtViewColor3.setText("Eraser");
            setPopVal(0, bgVal);
        }else if(view == btnUndo){
            drawingView.performUndo();
        }else if(view == btnSave){

            if(!hasExtPermission) {
                Toast.makeText(getApplicationContext(), "This feature requires permissions", Toast.LENGTH_LONG).show();
                return;
            }

            if(Image.Save(drawingView, getContentResolver()))
                Toast.makeText(getApplicationContext(), "Drawing Saved Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Problem Saving Image", Toast.LENGTH_SHORT).show();
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

        popupWindow.showAtLocation(drawingView, 0, 10, 0);

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

        bgbtn1 = (ImageButton) pop.findViewById(R.id.btnBgC1);
        bgbtn2 = (ImageButton) pop.findViewById(R.id.btnBgC2);
        bgbtn3 = (ImageButton) pop.findViewById(R.id.btnBgC3);
        bgbtn4 = (ImageButton) pop.findViewById(R.id.btnBgC4);
        bgbtn5 = (ImageButton) pop.findViewById(R.id.btnBgC5);
        bgbtn6 = (ImageButton) pop.findViewById(R.id.btnBgC6);
        bgbtn7 = (ImageButton) pop.findViewById(R.id.btnBgC7);
        bgbtn8 = (ImageButton) pop.findViewById(R.id.btnBgC8);
        bgbtn9 = (ImageButton) pop.findViewById(R.id.btnBgC9);
        bgbtn10 = (ImageButton) pop.findViewById(R.id.btnBgC10);
        bgbtn11 = (ImageButton) pop.findViewById(R.id.btnBgC11);
        bgbtn12 = (ImageButton) pop.findViewById(R.id.btnBgC12);

        txtViewColor = (TextView) pop.findViewById(R.id.txtViewpop2);
        txtViewColor4 = (TextView) pop.findViewById(R.id.txtViewpop4);
        btnSubmit = (Button) pop.findViewById(R.id.submitColor);

        btn1.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=1;
                txtViewColor.setText("Black");}});
        btn2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=2;
                txtViewColor.setText("White");}});
        btn3.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=3;
                txtViewColor.setText("Blue");}});
        btn4.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=4;
                txtViewColor.setText("Cyan");}});
        btn5.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=5;
                txtViewColor.setText("Dark Gray");}});
        btn6.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal = 6;
                txtViewColor.setText("Gray");}});
        btn7.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=7;
                txtViewColor.setText("Light Gray");}});
        btn8.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=8;
                txtViewColor.setText("Green");}});
        btn9.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=9;
                txtViewColor.setText("Magenta");}});
        btn10.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=10;
                txtViewColor.setText("Red");}});
        btn11.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=11;
                txtViewColor.setText("Selected: Yellow");}});
        btn12.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {setVal=12;
                txtViewColor.setText("Selected: Orange");}});

        bgbtn1.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                bgVal = 1;
                txtViewColor4.setText("Black");}});
        bgbtn2.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("White");}});
        bgbtn3.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Blue");}});
        bgbtn4.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Cyan");}});
        bgbtn5.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Dark Gray");}});
        bgbtn6.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Gray");}});
        bgbtn7.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Light Gray");}});
        bgbtn8.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Green");}});
        bgbtn9.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor.setText("Magenta");}});
        bgbtn10.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Red");}});
        bgbtn11.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Selected: Yellow");}});
        bgbtn12.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                txtViewColor4.setText("Selected: Orange");}});
        btnSubmit.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                popw.dismiss();
                setPopVal(setVal, bgVal);
                }});
    }

    public void setPopVal(int x, int y)
    {

        if(y==1) {
            drawingView.setBackg(Color.BLACK);
            showbgColor.setBackgroundColor(Color.BLACK);
        }
        else if(y==2)
            drawingView.setBackg(Color.WHITE);
        else if(y==3)
            drawingView.setBackg(Color.BLUE);
        else if(y==4)
            drawingView.setBackg(Color.CYAN);
        else if(y==5)
            drawingView.setBackg(Color.DKGRAY);
        else if(y==6)
            drawingView.setBackg(Color.GRAY);
        else if(y==7)
            drawingView.setBackg(Color.LTGRAY);
        else if(y==8)
            drawingView.setBackg(Color.GREEN);
        else if(y==9)
            drawingView.setBackg(Color.MAGENTA);
        else if(y==10)
            drawingView.setBackg(Color.RED);
        else if(y==11)
            drawingView.setBackg(Color.YELLOW);
        else if(y==12)
            drawingView.setBackg(Color.parseColor("#ffa500"));


        switch (x){

            case 0:
                showColor.setImageResource(R.drawable.white);
                break;
            case 1:
                drawingView.setupPaint(Color.BLACK);
                showColor.setBackgroundColor(Color.BLACK);
                break;
            case 2:
                drawingView.setupPaint(Color.WHITE);
                showColor.setImageResource(R.drawable.white);
                break;
            case 3:
                drawingView.setupPaint(Color.BLUE);
                showColor.setImageResource(R.drawable.blue);
                break;
            case 4:
                drawingView.setupPaint(Color.CYAN);
                showColor.setImageResource(R.drawable.cyan);
                break;
            case 5:
                drawingView.setupPaint(Color.DKGRAY);
                showColor.setImageResource(R.drawable.darkgray);
                break;
            case 6:
                drawingView.setupPaint(Color.GRAY);
                showColor.setImageResource(R.drawable.gray);
                break;
            case 7:
                drawingView.setupPaint(Color.LTGRAY);
                showColor.setImageResource(R.drawable.lightgray);
                break;
            case 8:
                drawingView.setupPaint(Color.GREEN);
                showColor.setImageResource(R.drawable.green);
                break;
            case 9:
                drawingView.setupPaint(Color.MAGENTA);
                showColor.setImageResource(R.drawable.magenta);
                break;
            case 10:
                drawingView.setupPaint(Color.RED);
                showColor.setImageResource(R.drawable.red);
                break;
            case 11:
                drawingView.setupPaint(Color.YELLOW);
                showColor.setImageResource(R.drawable.yellow);
                break;
            case 12:
                drawingView.setupPaint(Color.parseColor("#ffa500"));
                showColor.setImageResource(R.drawable.orange);
                break;
            default:
                break;
    }

        drawingView.setStrokeWidth((float) sbStrokeWidth.getProgress());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    hasExtPermission = false;
                }
                return;
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}






