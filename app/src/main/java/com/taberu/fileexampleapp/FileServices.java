package com.taberu.fileexampleapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Taberu on 08/11/2016.
 */

class FileServices {

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean isExternalStorageReadOnly() {
        String state = Environment.getExternalStorageState();

//        if (Environment.MEDIA_MOUNTED.equals(state) ||
//            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//            return true;
//        }
//        return false;
        return (Environment.MEDIA_MOUNTED.equals(state) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    boolean fileAppendExternalStorage(Context context, String sFileName, String sBody) {
        File myExternalFile;
        String sFilepath = "MyFileStorage";

        if(!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
//            saveButton.setEnabled(false);
        } else {
            myExternalFile = new File(context.getExternalFilesDir(sFilepath), sFileName);

            try {
                FileOutputStream fos = new FileOutputStream(myExternalFile, true);

                fos.write(sBody.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
