package com.bradleywilcox.doodlepad;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btnLineTool, btnRectTool;
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
        drawingView = (Drawing) findViewById(R.id.drawing_view);

        btnLineTool.setOnClickListener(this);
        btnRectTool.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLineTool){
            drawingView.setTool(Drawing.Tools.line);
        }else if(view == btnRectTool){
            drawingView.setTool(Drawing.Tools.rectangle);
        }
    }
}
