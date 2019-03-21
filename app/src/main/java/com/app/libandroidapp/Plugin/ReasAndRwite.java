package com.app.libandroidapp.Plugin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReasAndRwite {

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


//
//    private String readTextFromUri(Uri uri) throws IOException {
//        InputStream inputStream = getContentResolver().openInputStream(uri);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(
//                inputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        fileInputStream.close();
//        parcelFileDescriptor.close();
//        return stringBuilder.toString();
//    }



}
