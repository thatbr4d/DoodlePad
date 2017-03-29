package com.bradleywilcox.doodlepad;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.bradleywilcox.doodlepad.R.id.btnBgC1;
import static com.bradleywilcox.doodlepad.R.id.textViewcolor3;
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
 * 3.  Undo Feature, the undo button will undo the users most recent actions, one at a time, much like the common 'ctrl-z',
 * most of the code for this feature is found in the class 'BitmapManager' and is used throughout the 'Drawing' class
 *
 * 4.  Eraser Tool, found in the 'EraserTool' class
 *
 * 5.  Background Colors, CircleTool, Rounded Rectangle Tool, rgb colors
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String prefName = "DoodlePrefs";

    private ImageButton btnLineTool, btnRectTool, btnBrushTool, btnSave, btnErase, btnUndo, btnRoundRect, btnCircle, btnNewFile, btnInfo;
    private SeekBar sbStrokeWidth;
    private Drawing drawingView;
    private Button btnColor, btnSubmit, btnAdvColor, btnSubmitAdv, btnCancelAdv;
    private TextView txtViewColor, txtViewColor2, txtViewColor3, txtViewColor4, txtViewColorAdv;
    private EditText red, green, blue;
    private ImageView showColor, showbgColor;
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12,
            bgbtn1, bgbtn2, bgbtn3, bgbtn4, bgbtn5, bgbtn6, bgbtn7, bgbtn8, bgbtn9, bgbtn10, bgbtn11, bgbtn12;
    private Integer setVal = Color.RED, bgVal = Color.WHITE, ed1, ed2, ed3;

    private boolean hasExtPermission = true;
    private boolean isInfoShowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        setContentView(R.layout.activity_main);

        btnLineTool = (ImageButton) findViewById(R.id.btnLineTool);
        btnRectTool = (ImageButton) findViewById(R.id.btnRectangleTool);
        btnRoundRect = (ImageButton) findViewById(R.id.btnRoundRect);
        btnCircle = (ImageButton) findViewById(R.id.btnCircle);
        btnBrushTool = (ImageButton) findViewById(R.id.btnBrushTool);
        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnErase = (ImageButton) findViewById(R.id.btnErase);
        btnUndo = (ImageButton) findViewById(R.id.btnUndo);
        btnNewFile = (ImageButton) findViewById(R.id.btnNewFile);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);

        sbStrokeWidth = (SeekBar) findViewById(R.id.sbStrokeWidth);
        drawingView = (Drawing) findViewById(R.id.drawing_view);

        txtViewColor2 = (TextView) findViewById(R.id.textViewcolor);
        txtViewColor3 = (TextView) findViewById(R.id.textViewcolor2);
        showColor = (ImageView) findViewById(R.id.imageButtoncolor);
        showbgColor = (ImageView) findViewById(R.id.imageButtoncolor2);

        btnColor = (Button) findViewById(R.id.buttonColor);
        btnAdvColor = (Button) findViewById(R.id.buttonColor2);

        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
        btnRoundRect.setOnClickListener(this);
        btnCircle.setOnClickListener(this);
        btnBrushTool.setOnClickListener(this);
        btnErase.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnAdvColor.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnNewFile.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

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
            setPopVal(setVal, bgVal);
        } else if (view == btnRectTool) {
            drawingView.setTool(Drawing.Tools.rectangle);
            txtViewColor3.setText("Rectangle");
            setPopVal(setVal, bgVal);
        } else if (view == btnRoundRect) {
            drawingView.setTool(Drawing.Tools.round_rectangle);
            txtViewColor3.setText("Round Rect");
            setPopVal(setVal, bgVal);
        } else if (view == btnCircle) {
            drawingView.setTool(Drawing.Tools.circle);
            txtViewColor3.setText("Circle");
            setPopVal(setVal, bgVal);
        } else if (view == btnBrushTool) {
            drawingView.setTool(Drawing.Tools.brush);
            txtViewColor3.setText("Brush");
            setPopVal(setVal, bgVal);
        } else if (view == btnColor) {
            runPopup();
        }else if(view == btnAdvColor){
            runPopupAdv();
        }
        else if (view == btnErase) {
            drawingView.setTool(Drawing.Tools.eraser);
            txtViewColor3.setText("Eraser");
            setEraser(bgVal);
        } else if (view == btnSave) {

            if (!hasExtPermission) {
                Toast.makeText(getApplicationContext(), "This feature requires permissions", Toast.LENGTH_LONG).show();
                return;
            }

            if (Image.Save(drawingView, getContentResolver()))
                Toast.makeText(getApplicationContext(), "Drawing Saved Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Problem Saving Image", Toast.LENGTH_SHORT).show();
        } else if(view == btnUndo){
            drawingView.performUndo();
        } else if(view == btnNewFile){
            newFileDialog();
        } else if(view == btnInfo){
            showInfo(view);
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

        popupWindow.showAtLocation(drawingView, 100, 100, 0);

    }

    public void runPopupAdv() {

        LayoutInflater layoutInflater2 = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView2 = layoutInflater2.inflate(R.layout.popupadvanced, null);
        final PopupWindow popupWindow2 = new PopupWindow(
                popupView2,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        setAdvColor(popupView2, popupWindow2);

        popupWindow2.showAtLocation(drawingView, 100, 100, 0);
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

        btn1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.BLACK;
                txtViewColor.setText("Black");
            }
        });
        btn2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.WHITE;
                txtViewColor.setText("White");
            }
        });
        btn3.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.BLUE;
                txtViewColor.setText("Blue");
            }
        });
        btn4.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.CYAN;
                txtViewColor.setText("Cyan");
            }
        });
        btn5.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.DKGRAY;
                txtViewColor.setText("Dark Gray");
            }
        });
        btn6.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.GRAY;
                txtViewColor.setText("Gray");
            }
        });
        btn7.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.LTGRAY;
                txtViewColor.setText("Light Gray");
            }
        });
        btn8.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.GREEN;
                txtViewColor.setText("Green");
            }
        });
        btn9.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.MAGENTA;
                txtViewColor.setText("Magenta");
            }
        });
        btn10.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.RED;
                txtViewColor.setText("Red");
            }
        });
        btn11.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.YELLOW;
                txtViewColor.setText("Yellow");
            }
        });
        btn12.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVal = Color.parseColor("#ffa500");
                txtViewColor.setText("Orange");
            }
        });

        bgbtn1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.BLACK;
                txtViewColor4.setText("Black");
            }
        });
        bgbtn2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.WHITE;
                txtViewColor4.setText("White");
            }
        });
        bgbtn3.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.BLUE;
                txtViewColor4.setText("Blue");
            }
        });
        bgbtn4.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.CYAN;
                txtViewColor4.setText("Cyan");
            }
        });
        bgbtn5.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.DKGRAY;
                txtViewColor4.setText("Dark Gray");
            }
        });
        bgbtn6.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.GRAY;
                txtViewColor4.setText("Gray");
            }
        });
        bgbtn7.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.LTGRAY;
                txtViewColor4.setText("Light Gray");
            }
        });
        bgbtn8.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.GREEN;
                txtViewColor4.setText("Green");
            }
        });
        bgbtn9.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.MAGENTA;
                txtViewColor4.setText("Magenta");
            }
        });
        bgbtn10.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.RED;
                txtViewColor4.setText("Red");
            }
        });
        bgbtn11.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.YELLOW;
                txtViewColor4.setText("Yellow");
            }
        });
        bgbtn12.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgVal = Color.parseColor("#ffa500");
                txtViewColor4.setText("Orange");
            }
        });
        btnSubmit.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                popw.dismiss();
                setPopVal(setVal, bgVal);
            }
        });
    }

    public void setEraser(int x)
    {

        drawingView.setEraserColor(x);
        showColor.setBackgroundColor(x);
    }

    public void setPopVal(int x, int y) {

        drawingView.setBackg(y);
        showbgColor.setBackgroundColor(y);

        drawingView.setupPaint(x);
        showColor.setBackgroundColor(x);

        drawingView.setStrokeWidth((float) sbStrokeWidth.getProgress());
    }

    public void setAdvColor(View pop2, final PopupWindow pow2)
    {

        txtViewColorAdv = (TextView) pop2.findViewById(R.id.txtViewpopAd);
        red = (EditText) pop2.findViewById(R.id.inputRed);
        green = (EditText) pop2.findViewById(R.id.inputGreen);
        blue = (EditText) pop2.findViewById(R.id.inputBlue);

        btnSubmitAdv = (Button) pop2.findViewById(R.id.submitAdColor);
        btnCancelAdv = (Button) pop2.findViewById(R.id.cancelAdColor);

        btnSubmitAdv.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v){
                verifyRGB();
                pow2.dismiss();

                fullScreen();
            }
        });

        btnCancelAdv.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                pow2.dismiss();
                fullScreen();
            }
        });
    }

    public void verifyRGB()
    {
        String xx = red.getText().toString(), yy=green.getText().toString(), zz=blue.getText().toString();

        if(xx.equals("")||yy.equals("")||zz.equals(""))
        {
            return;
        }else {
            ed1 = Integer.parseInt(red.getText().toString());
            ed2 = Integer.parseInt(green.getText().toString());
            ed3 = Integer.parseInt(blue.getText().toString());

            if (ed1 < 0 || ed1 > 255 || ed2 < 0 || ed2 > 255 || ed3 < 0 || ed3 > 255) {
                return;
            }

            mixColors(ed1, ed2, ed3);
            btnSubmitAdv.setClickable(true);
        }
    }

    public void mixColors(int x, int y, int z)
    {
        int advCol;
        advCol = Color.rgb(x,y,z);
        setVal = advCol;
        drawingView.setupPaint(advCol);
        showColor.setBackgroundColor(advCol);
    }

    public void showInfo(View v) {

        if(!isInfoShowing) {
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.info, null);
            final PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            isInfoShowing = true;

            Button close = (Button) popupView.findViewById(R.id.btnCloseInfo);
            close.setOnClickListener(new View.OnClickListener() {

                public void onClick(View popupView) {
                    isInfoShowing = false;
                    popupWindow.dismiss();
                }
            });
        }
    }

    public void newFileDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("New Doodle");
        dialog.setMessage("Are you sure you want to start a new doodle?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                txtViewColor3.setText("Line");
                setVal = Color.RED;
                bgVal = Color.WHITE;
                setPopVal(setVal, bgVal);
                drawingView.reset();
                dialog.dismiss();

                fullScreen();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();

                fullScreen();
            }
        });
        dialog.show();
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
        fullScreen();

        SharedPreferences settings = getSharedPreferences(prefName, MODE_PRIVATE);
        int color = settings.getInt("color", -99);
        int bgColor = settings.getInt("bgColor", -99);
        int tool = settings.getInt("tool", -99);
        String toolName = settings.getString("toolName", "");
        int stroke = settings.getInt("stroke", -99);

        if(color != -99 && bgColor != -99){
            txtViewColor3.setText(toolName);
            setVal = color;
            bgVal = bgColor;
            setPopVal(color, bgColor);
            drawingView.setTool(Drawing.Tools.values()[tool]);
            if(Drawing.Tools.values()[tool] == Drawing.Tools.eraser)
                setEraser(bgColor);
            sbStrokeWidth.setProgress(stroke);
            drawingView.setStrokeWidth(stroke);
        }
    }

    private void fullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences settings = getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("color", setVal);
        editor.putInt("bgColor", bgVal);
        editor.putString("toolName", txtViewColor3.getText().toString());
        editor.putInt("tool", drawingView.getTool().ordinal());
        editor.putInt("stroke", sbStrokeWidth.getProgress());
        editor.commit();

        BitmapManager.saveNewest(drawingView.getNewestBitmap(), getApplicationContext());
    }




}






