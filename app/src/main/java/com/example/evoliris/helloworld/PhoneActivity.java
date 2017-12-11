package com.example.evoliris.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Permission;


public class PhoneActivity extends AppCompatActivity {

    private static final int PERMISSION_CALL_PHONE = 725;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    void openDefaultCallApp() {
        TextView textPhone = (TextView) findViewById(R.id.text_phone_number);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", textPhone.getText().toString(), null));
        startActivity(intent);
    }

    void startCall() {
        if (checkPermissionCallPhone()) {
            TextView textPhone = (TextView) findViewById(R.id.text_phone_number);
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", textPhone.getText().toString(), null));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CALL_PHONE:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startCall();
                }
                break;
        }
    }

    private boolean checkPermissionCallPhone() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL_PHONE);
        return false;
    }

    void initView() {
        Button btnCall = (Button) findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCall();
            }
        });
    }
}
