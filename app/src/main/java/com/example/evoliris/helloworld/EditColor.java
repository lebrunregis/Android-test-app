package com.example.evoliris.helloworld;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;

public class EditColor extends AppCompatActivity {
    private Button selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_color);
    }
    protected void initView(){

        Button btnRetour = (Button) findViewById(R.id.retour2) ;
        Button btnOk = (Button) findViewById(R.id.ok2) ;
        SeekBar sbRed  = (SeekBar) findViewById(R.id.seekBarRed) ;
        SeekBar sbBlue  = (SeekBar) findViewById(R.id.seekBarBlue) ;
        SeekBar sbGreen  = (SeekBar) findViewById(R.id.seekBarGreen) ;
        GridView gvColChoice  = (GridView) findViewById(R.id.grid_choix_couleur) ;

        gvColChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Button bColor = (Button) findViewById(view.getId());

              //  bColor.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_color, null));

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result(v);
            }
        });
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result(v);
            }
        });
        SeekBar.OnSeekBarChangeListener sbChangeListener = new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
        };
        sbRed.setOnSeekBarChangeListener(sbChangeListener)  ;
        sbBlue.setOnSeekBarChangeListener(sbChangeListener);
        sbGreen.setOnSeekBarChangeListener(sbChangeListener);
    }
    protected void setButtonColor(long id){
        SeekBar sbRed  = (SeekBar) findViewById(R.id.seekBarRed) ;
        SeekBar sbBlue  = (SeekBar) findViewById(R.id.seekBarBlue) ;
        SeekBar sbGreen  = (SeekBar) findViewById(R.id.seekBarGreen) ;
        int color;
        GridView gvColor = (GridView) findViewById(R.id.colorChoice) ;

        color = Color.rgb(sbRed.getProgress(), sbGreen.getProgress(), sbBlue.getProgress());
       /// id.setBackgroundColor(color);
    }
    protected void result(View v){



        finish();
    }

}
