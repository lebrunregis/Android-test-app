package com.example.evoliris.helloworld;

import android.net.Uri;

import java.io.File;

/**
 * Created by Evoliris on 07-09-17.
 */

interface SaveListener {
    void toast(String toast);
    void mediaScan(File file);
}
