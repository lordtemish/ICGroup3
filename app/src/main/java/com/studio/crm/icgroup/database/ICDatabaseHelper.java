package com.studio.crm.icgroup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ICDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "icgroupdb"; // the name of our database
    private static final int DB_VERSION = 1;
    public ICDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE token (_id INTEGER PRIMARY KEY AUTOINCREMENT,ID TEXT);");
        ContentValues cv3=new ContentValues();
        cv3.put("ID"," ");
        db.insert("token","",cv3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV,int newV){

    }
}
