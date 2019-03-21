package com.app.libandroidapp.LocalStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SqlLiteClass extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader";
    public static  String usersTable = "users";
    public static  String fileTable = "files";

    public SqlLiteClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + usersTable + " (id INTEGER PRIMARY KEY,username TEXT,password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + fileTable + " (id INTEGER PRIMARY KEY,user_id INTEGER,fileName TEXT,fileSize TEXT,file BLOB,username TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       // sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }





}
