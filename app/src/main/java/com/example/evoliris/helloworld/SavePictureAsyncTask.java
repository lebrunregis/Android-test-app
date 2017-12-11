package com.example.evoliris.helloworld;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Result;

/**
 * Created by Evoliris on 07-09-17.
 */

public class SavePictureAsyncTask extends AsyncTask {
   private SaveListener activity;

    private String saveToExternalStorage(Bitmap drawing) {

        String returnString;

        File pictureFile = getOutputMediaFile(Environment.getExternalStorageDirectory()
                + "/Pictures/");
        if (pictureFile != null) {
            Bitmap resizedBitmap = resizeBitmap(drawing);
            if (resizedBitmap != null) {
                storeImage(resizedBitmap, pictureFile);
                galleryAddPic(pictureFile);
                returnString ="Saved to external storage";
            } else {
                returnString= "Nothing to save";
            }
        } else {
            returnString= "Error creating file";
        }
        return returnString;
    }

    public Bitmap resizeBitmap(Bitmap bmp) {
        int firstNonTransparentRow = -1;
        int lastNonTransparentRow = -1;
        int firstNonTransparentColumn = -1;
        int lastNonTransparentColumn = -1;
        Bitmap resizedBitmap;
        boolean isTransparent;
        int row;
        int column;
        int height = bmp.getHeight();
        int width = bmp.getWidth();

        for (row = 0; row < height; row++) {
            isTransparent = true;
            column = 0;
            while (isTransparent && column < width) {
                int pixel = bmp.getPixel(column, row);
                int alpha = Color.alpha(pixel);
                isTransparent = alpha == 0;
                column++;
            }
            if (!isTransparent) {
                if (firstNonTransparentRow == -1) {
                    firstNonTransparentRow = row;
                } else {
                    lastNonTransparentRow = row;
                }
            }
        }

        for (column = 0; column < width; column++) {
            isTransparent = true;
            row = 0;
            while (isTransparent && row < height) {
                int pixel = bmp.getPixel(column, row);
                isTransparent = Color.alpha(pixel) == 0;
                row++;
            }
            if (!isTransparent) {
                if (firstNonTransparentColumn == -1) {
                    firstNonTransparentColumn = column;
                } else {
                    lastNonTransparentColumn = column;
                }
            }
        }

        if (lastNonTransparentColumn != firstNonTransparentColumn &&
                lastNonTransparentRow != firstNonTransparentRow) {
            resizedBitmap = Bitmap.createBitmap(bmp,
                    firstNonTransparentColumn,
                    firstNonTransparentRow,
                    lastNonTransparentColumn - firstNonTransparentColumn,
                    lastNonTransparentRow - firstNonTransparentRow);
        } else {
            resizedBitmap = null;
        }
        return resizedBitmap;
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(String path) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(path);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "oe_" + timeStamp + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void storeImage(Bitmap image, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG_ASYNC_SAVE", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG_ASYNC_SAVE", "Error accessing file: " + e.getMessage());
        }

    }

    private void galleryAddPic(File file) {
activity.mediaScan(file);
    }

    public void setActivity(SaveListener activity) {
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (Looper.myLooper()==null)
            Looper.prepare();
        Bitmap bitmap = (Bitmap) params[0];

        return saveToExternalStorage(bitmap);
    }

    @Override
    protected void onPostExecute(Object o) {
        activity.toast((String)o);

    }
}
