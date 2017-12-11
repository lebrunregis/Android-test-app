package com.example.evoliris.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class DrawActivity extends AppCompatActivity implements SaveListener {

    private static final String TAG = "draw_activity";
    private static final int RC_PERMISSION_WRITE_EXTERNAL_STORAGE = 42;
    ArrayList<Button> buttonList = new ArrayList<>();
    ArrayList<Integer> colorList = new ArrayList<>();



    private static final int CODE_COLOR_EDIT = 129;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        colorList.add(Color.BLACK);
        colorList.add(Color.WHITE);
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.LTGRAY);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.parseColor("#fcddd1"));
        colorList.add(Color.BLACK);

        buttonList.add((Button) findViewById(R.id.color_0));
        buttonList.add((Button) findViewById(R.id.color_1));
        buttonList.add((Button) findViewById(R.id.color_2));
        buttonList.add((Button) findViewById(R.id.color_3));
        buttonList.add((Button) findViewById(R.id.color_4));
        buttonList.add((Button) findViewById(R.id.color_5));
        buttonList.add((Button) findViewById(R.id.color_6));
        buttonList.add((Button) findViewById(R.id.color_7));
        buttonList.add((Button) findViewById(R.id.color_8));
        buttonList.add((Button) findViewById(R.id.color_9));



        initView();
    }

    protected void initView() {

        GridLayout gvColChoice = (GridLayout) findViewById(R.id.grid_choix_couleur);
        DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
        setOptionButtonsListeners();
        updateColorButtons();
        setColorButtonsListener();
        setBrushButtonsListener();
    }

    private void setBrushButtonsListener() {
        Button bnTiny = (Button) findViewById(R.id.brush_tiny);
        Button bnSmall = (Button) findViewById(R.id.brush_small);
        Button bnNormal = (Button) findViewById(R.id.brush_normal);
        Button bnBig = (Button) findViewById(R.id.brush_big);

        bnTiny.setTag(R.string.tag_brush, 5f);
        bnSmall.setTag(R.string.tag_brush, 10f);
        bnNormal.setTag(R.string.tag_brush, 15f);
        bnBig.setTag(R.string.tag_brush, 20f);

        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
                drawZone.changeStrokeWidth((float) v.getTag(R.string.tag_brush));
            }
        };

        bnTiny.setOnClickListener(listenerButton);
        bnSmall.setOnClickListener(listenerButton);
        bnNormal.setOnClickListener(listenerButton);
        bnBig.setOnClickListener(listenerButton);

    }

    private void setOptionButtonsListeners() {
        setSaveButtonListener();
        setEditButtonListener();
        setBackButtonListener();
        setClearButtonListener();
    }

    private void setSaveButtonListener() {
        Thread thread;
        Button buttonSave = (Button) findViewById(R.id.save_drawing);
        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// Environment.getExternalStorageState();
                    if (checkWriteExternalStoragePermission()) {
                        startAsyncSave();
                    }
            }
        };
        buttonSave.setOnClickListener(listenerButton);
    }



  private void startAsyncSave() {
      SavePictureAsyncTask task = new SavePictureAsyncTask();
      task.setActivity(this);
      DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
      task.execute(new Object[]{drawZone.getBitmap()});
 }

    private boolean checkWriteExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_PERMISSION_WRITE_EXTERNAL_STORAGE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSION_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // allowed
                    Toast.makeText(this, "Write Access Allowed", Toast.LENGTH_LONG).show();
                } else {
                    // denied
                    Toast.makeText(this, "Write Access Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void setEditButtonListener() {
        Button buttonEdit = (Button) findViewById(R.id.edit_drawing);
        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Intent intent = new Intent(this, Activity1.class);
                //   startActivity(intent);
            }
        };
        buttonEdit.setOnClickListener(listenerButton);
    }

    private void setBackButtonListener() {
        Button buttonBack = (Button) findViewById(R.id.back_drawing);
        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
                drawZone.back();
            }
        };
        buttonBack.setOnClickListener(listenerButton);
    }

    private void setClearButtonListener() {
        Button buttonClear = (Button) findViewById(R.id.delete_drawing);
        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
                drawZone.clear();
            }
        };
        buttonClear.setOnClickListener(listenerButton);
    }

    private void setColorButtonsListener() {
        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
                ((Button) v).setSelected(true);
                drawZone.changePainterColor((int) v.getTag(R.string.tag_color));
            }

        };
        int cpt = 0;
        while (cpt < buttonList.size()) {
            buttonList.get(cpt).setOnClickListener(listenerButton);
            cpt++;
        }
    }

    private void updateColorButtons() {
        int cpt2 = 0;
        while (cpt2 < buttonList.size()) {
            changeButtonColor(buttonList.get(cpt2), colorList.get(cpt2));
            cpt2++;
        }
    }

    public void changeButtonColor(Button button, int color) {
        button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        button.setTag(R.string.tag_color, color);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mediaScan(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }
}

