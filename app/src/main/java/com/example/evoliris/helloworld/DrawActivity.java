package com.example.evoliris.helloworld;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class DrawActivity extends AppCompatActivity {

    ArrayList<Button> buttonList = new ArrayList<>();
    ArrayList<Integer> colorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initView();
    }

    protected void initView() {

        GridLayout gvColChoice = (GridLayout) findViewById(R.id.grid_choix_couleur);
        DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
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

        View.OnClickListener listenerButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawView drawZone = (DrawView) findViewById(R.id.drawZone);
                ((Button) v).setSelected(true);
                drawZone.changePainterColor((int) v.getTag(R.string.tag_color));

                // drawZone.changeColor();
            }

        };
        updateColorButtons();
        setButtonsListener(listenerButton);
    }

    void setButtonsListener(View.OnClickListener listenerButton) {
        int cpt = 0;
        while (cpt < buttonList.size())
            buttonList.get(cpt).setOnClickListener(listenerButton);
        cpt++;
    }

    void updateColorButtons() {
        int cpt = 0;
        while (cpt < buttonList.size())
            changeButtonColor(buttonList.get(cpt), colorList.get(cpt));
        cpt++;
    }

    void changeButtonColor(Button button, int color) {
        button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        button.setTag(R.string.tag_color, color);
    }
}

