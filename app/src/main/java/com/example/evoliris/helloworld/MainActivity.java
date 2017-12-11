package com.example.evoliris.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG_LOG = "LOG DEMO";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String login = sharedPref.getString("login", "");
        String password = sharedPref.getString("password", "");
        Boolean rememberMe = sharedPref.getBoolean("remember_me", false);

        initView(login, password,rememberMe);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG_LOG, "Methode onStop lancee");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG_LOG, "Methode onResume lancee");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG_LOG, "Methode onStart lancee");

    }


    private void initView(String login,String password, boolean rememberMe) {
        Button btnLogin = (Button) findViewById(R.id.login_ok);
        Button btnReset = (Button) findViewById(R.id.login_reset);
        TextView tvLogin = (TextView) findViewById(R.id.input_login);
        TextView tvPw = (TextView) findViewById(R.id.input_password);
        CheckBox cbRemember = (CheckBox) findViewById(R.id.cb_login_remember);

        tvLogin.setText(login);
        tvPw.setText(password);
        cbRemember.setChecked(rememberMe);


        tvPw.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Button btnLogin = (Button) findViewById(R.id.login_ok);
                if(s.length() == 0){
                    btnLogin.setEnabled(true);
                }else
                    if ( (s.toString().matches(
                                "^(?!.*((\\S)\\1|\\s))(?=.*(\\d.+){2,})(?=.*(\\W.+){2,}).{8,}"))) {
                    btnLogin.setEnabled(true);

                }  else{
                        btnLogin.setEnabled(false);
                    }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

    }

    private void login() {

        SharedPreferences sharedPref = getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        TextView tvLogin = (TextView) findViewById(R.id.input_login);
        TextView tvPw = (TextView) findViewById(R.id.input_password);
        CheckBox cbRemember = (CheckBox) findViewById(R.id.cb_login_remember);
        if (cbRemember.isChecked() &&
                !tvLogin.getText().toString().isEmpty() &&
                !tvPw.getText().toString().isEmpty()
                ) {
            editor.putString("login", tvLogin.getText().toString());
            editor.putString("password", tvPw.getText().toString());
            editor.putBoolean("remember_me", cbRemember.isChecked());
            editor.commit();
            startMenuActivity();
        } else {
            editor.remove("login");
            editor.remove("remember_me");
            editor.commit();
            startMenuActivity();
        }

    }

    private void reset() {
        TextView username = (TextView) findViewById(R.id.input_login);
        TextView pwd = (TextView) findViewById(R.id.input_password);

        username.setText("");
        pwd.setText("");
        Toast.makeText(this, R.string.reset, Toast.LENGTH_LONG).show();
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);

        TextView tVLogin = (TextView) findViewById(R.id.input_login);
        Toast.makeText(this, tVLogin.getText(), Toast.LENGTH_LONG).show();
        intent.putExtra("login", tVLogin.getText().toString());
        startActivity(intent);

    }
}

