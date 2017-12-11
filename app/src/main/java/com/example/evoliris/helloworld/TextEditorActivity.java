package com.example.evoliris.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class TextEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
    }

    void saveText() {
        File file = new File(this.getFilesDir(), "textsave");
        String string = "text";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("textsave", this.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

