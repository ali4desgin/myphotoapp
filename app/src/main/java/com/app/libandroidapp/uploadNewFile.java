package com.app.libandroidapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.libandroidapp.LocalStorage.Requests;
import com.app.libandroidapp.LocalStorage.SharedPref;
import com.app.libandroidapp.Plugin.Alerts;
import com.app.libandroidapp.Plugin.ReasAndRwite;

import java.io.FileDescriptor;
import java.io.IOException;

import static java.security.AccessController.getContext;

public class uploadNewFile extends AppCompatActivity {


    private Button uploadBtn;
    private ImageButton uploadImageBtn;
    private EditText filename,filesize;

    String filenName = "";
    String fileSize = "";
    Bitmap fileBitmap = null;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_new_file);
        setUiConst();
        setEvents();

    }



    private  void setUiConst(){
        uploadImageBtn = findViewById(R.id.uploadImage);
        uploadBtn = findViewById(R.id.addFileBtn);
        filename = findViewById(R.id.fileNameTxt);
        filesize = findViewById(R.id.fileSize);


    }


    private void setEvents(){
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStorage();

            }
        });



        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filenName.matches("") || fileSize.matches("") ||  fileBitmap == null){


                    Alerts.showDilog(uploadNewFile.this,String.valueOf((getString(R.string.please_load_file))));

                }else{


                    String  user_id =  SharedPref.getString(uploadNewFile.this,"user_id");
                    String  username =  SharedPref.getString(uploadNewFile.this,"username");
                    if(Requests.saveFile(uploadNewFile.this,user_id,username,filenName,fileSize,fileBitmap)){


                        Alerts.localNotification(uploadNewFile.this,getString(R.string.thanks_for_add));
                        Intent intent = new Intent(uploadNewFile.this,HomeActivity.class);
                        startActivity(intent);


                    }else{
                        Alerts.showDilog(uploadNewFile.this,String.valueOf((getString(R.string.upload_fail))));


                    }


                }
            }
        });
    }





    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().


            if (data != null) {

                uri = data.getData();

                this.dumpImageMetaData(uri);
                try {
                    this.fileBitmap = this.getBitmapFromUri(uri);
                    uploadImageBtn.setImageBitmap(fileBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("message", "Uri: " + uri.toString());
               // showImage(uri);
            }
        }
    }



    private void openStorage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent,100);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void dumpImageMetaData(Uri uri) {

        // The query, since it only applies to a single document, will only return
        // one row. There's no need to filter, sort, or select fields, since we want
        // all fields for one document.
        Cursor cursor = this.getContentResolver()
                .query(uri, null, null, null, null, null);

        try {
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null && cursor.moveToFirst()) {

                // Note it's called "Display Name".  This is
                // provider-specific, and might not necessarily be the file name.
                String displayName = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                Log.i("test", "Display Name: " + displayName);

                filename.setText("File name : " +  displayName);


                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                // If the size is unknown, the value stored is null.  But since an
                // int can't be null in Java, the behavior is implementation-specific,
                // which is just a fancy term for "unpredictable".  So as
                // a rule, check if it's null before assigning to an int.  This will
                // happen often:  The storage API allows for remote files, whose
                // size might not be locally known.
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    // Technically the column stores an int, but cursor.getString()
                    // will do the conversion automatically.
                    size = cursor.getString(sizeIndex / 1000) ;
                } else {
                    size = "Unknown";
                }
                filesize.setText("Size: " + size +  " KB");



                fileSize = String.valueOf(size);
                filenName = String.valueOf(displayName);
                Log.i("test3", "Size: " + size +  " KB");
            }
        } finally {
            cursor.close();
        }
    }



    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


}
