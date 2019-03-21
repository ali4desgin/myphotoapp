package com.app.libandroidapp.LocalStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.app.libandroidapp.Plugin.FileModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Requests {

    public static boolean LoginRequest(Context  context , String username, String password){


        //    public SqlLiteClass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        SqlLiteClass sqlLiteClass = new SqlLiteClass(context);
        SQLiteDatabase db = sqlLiteClass.getReadableDatabase();
        String selection =  "username = ?";
        String[] selectionArgs = { username };


        Cursor cursor = db.query(SqlLiteClass.usersTable,null,selection,selectionArgs,null,null,null);

        if(cursor.getCount()>=1){


            cursor.moveToFirst();

            String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));

            if(pass.matches(password)){

                SharedPref.saveString(context,"username",username);
                SharedPref.saveString(context,"user_id", String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))) );



                return  true;


            }else{
                return  false;
            }

        }



        return false;
    }





    public static boolean RegisterRequest(Context  context , String username, String password){


        //    public SqlLiteClass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        SqlLiteClass sqlLiteClass = new SqlLiteClass(context);
        SQLiteDatabase db2 = sqlLiteClass.getWritableDatabase();
        SQLiteDatabase db = sqlLiteClass.getReadableDatabase();
        String selection =  "username = ?";
        String[] selectionArgs = { username };


        Cursor cursor = db.query(SqlLiteClass.usersTable,null,selection,selectionArgs,null,null,null);
        if(cursor.getCount()>=1){

            return  false;

        }else{
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);

            long newRowId = db2.insert("users", null, values);



            SharedPref.saveString(context,"username",username);
            SharedPref.saveString(context,"user_id", String.valueOf(newRowId) );



        }





        return true;
    }





    public static void   deletefile(Context context , String id){
        SqlLiteClass sqlLiteClass = new SqlLiteClass(context);
        SQLiteDatabase db2 = sqlLiteClass.getWritableDatabase();


        String selection =  "id=?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { id };
// Issue SQL statement.
        int deletedRows = db2.delete("files", selection, selectionArgs);


//        String selection = " fileName = ? ";
//        String[] selectionArgs = { filename };
//
//        db2.delete(SqlLiteClass.fileTable, selection, selectionArgs);
//

    }




    public static boolean saveFile(Context  context ,String user_id,String username, String file_title, String file_zise, Bitmap file){


        //    public SqlLiteClass(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        SqlLiteClass sqlLiteClass = new SqlLiteClass(context);
        SQLiteDatabase db2 = sqlLiteClass.getWritableDatabase();

        Bitmap bitmap = file;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
          byte[] img = bos.toByteArray();


        ContentValues values = new ContentValues();
        values.put("user_id", user_id);
        values.put("fileName", file_title);
        values.put("fileSize", file_zise);
        values.put("username", username);
        values.put("file", img);


        long newRowId = db2.insert("files", null, values);


        if(newRowId == 0){
            Toast.makeText(context,String.valueOf(newRowId),Toast.LENGTH_LONG).show();
            return  false;
        }





        return true;
    }






    public static List<FileModel> getAllFiles(Context  context){


        SqlLiteClass sqlLiteClass = new SqlLiteClass(context);
        SQLiteDatabase db = sqlLiteClass.getReadableDatabase();

        Cursor cursor = db.query(SqlLiteClass.fileTable,null,null,null,null,null,null);
        cursor.moveToFirst();
         List<FileModel> files  = new ArrayList<FileModel>();
        while(cursor.moveToNext()) {


            Long itemId = cursor.getLong(
                    cursor.getColumnIndex("id"));
            String fileName = String.valueOf(cursor.getString(
                    cursor.getColumnIndex("fileName"))) ;
            String fileSize = String.valueOf(cursor.getString(
                    cursor.getColumnIndex("fileSize"))) ;

            String username = String.valueOf(cursor.getString(
                    cursor.getColumnIndex("username"))) ;


            byte[] fileBitmap2 =  cursor.getBlob(
                    cursor.getColumnIndex("file"));


    //        Bitmap fileBitmap = BitmapFactory.decodeByteArray(fileBitmap2,0,fileBitmap2.length);
            // (String id, String filename,String filesize, String username, Bitmap file)
            FileModel fileModel = new FileModel(String.valueOf(itemId), fileName,fileSize,username,fileBitmap2);
            files.add(fileModel);
        }




//        Toast.makeText(context,String.valueOf(cursor.getCount()),Toast.LENGTH_LONG).show();
        return files;
    }





}
