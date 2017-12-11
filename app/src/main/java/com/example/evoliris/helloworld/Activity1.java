package com.example.evoliris.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        initView();
    }
    protected void initView(){

        Button btnRetour = (Button) findViewById(R.id.retour1) ;
        Button btnOk = (Button) findViewById(R.id.ok1) ;
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result(v);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result(v);
            }
        });
    }
        protected void result(View v){
            switch (v.getId()){
                case R.id.ok1:
                    setResult(RESULT_OK);
                    break;
                case R.id.retour1:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }

}
