package com.example.evoliris.helloworld;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        initView();
    }
    protected void initView(){

        Button btnRetour = (Button) findViewById(R.id.retour2) ;
        Button btnOk = (Button) findViewById(R.id.ok2) ;
        SeekBar sbRed  = (SeekBar) findViewById(R.id.seekBarRed) ;
        SeekBar sbBlue  = (SeekBar) findViewById(R.id.seekBarBlue) ;
        SeekBar sbGreen  = (SeekBar) findViewById(R.id.seekBarGreen) ;
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
        sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBgColor();
            }
        })  ;
        sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBgColor();
            }
        });
        sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onStopTrackingTouch (SeekBar seekBar){

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setBgColor();
            }
        });
    }
    protected void setBgColor(){
        SeekBar sbRed  = (SeekBar) findViewById(R.id.seekBarRed) ;
        SeekBar sbBlue  = (SeekBar) findViewById(R.id.seekBarBlue) ;
        SeekBar sbGreen  = (SeekBar) findViewById(R.id.seekBarGreen) ;
        int couleur ;
        View activite = (View) findViewById(R.id.activite2) ;

        couleur= Color.rgb(sbRed.getProgress(), sbGreen.getProgress(), sbBlue.getProgress());
        activite.setBackgroundColor(couleur);
    }
    protected void result(View v){
        SeekBar sbRed  = (SeekBar) findViewById(R.id.seekBarRed) ;
        SeekBar sbBlue  = (SeekBar) findViewById(R.id.seekBarBlue) ;
        SeekBar sbGreen  = (SeekBar) findViewById(R.id.seekBarGreen) ;
        int couleur ;
        View activite = (View) findViewById(R.id.activite2) ;


        switch (v.getId()){
            case R.id.ok2:
                Intent output = new Intent();
                couleur= Color.rgb(sbRed.getProgress(), sbGreen.getProgress(), sbBlue.getProgress());
                output.putExtra("bg", couleur);
                setResult(RESULT_OK, output);
                break;
            case R.id.retour2:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}
