package com.knitechs.www.ss12.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Koli on 6/25/2016.
 */
public class SQLLiteData extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="login.db";
    private static final String TABLE_NAME="auth";
    private static final String COLUMN ="AUTHORIZED";

    private static final String CREATE_TABLE_QUERY ="CREATE TABLE auth (USER_CODE text null)";

    private static SQLiteDatabase db;

    public SQLLiteData(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static void setDb(SQLiteDatabase db) {
        if(SQLLiteData.db == null)
          SQLLiteData.db = db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
        this.setDb(db);
    }

    public void loggedin(String usercode,String username,String name){
        setDb(this.getWritableDatabase());
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_CODE",usercode);
        getDb().insert(TABLE_NAME, null, contentValues);
    }

    public void loggedout(){
        setDb(this.getWritableDatabase());
        getDb().execSQL("DELETE FROM " + TABLE_NAME + "");
    }

    public boolean isLogged(){
        setDb(this.getReadableDatabase());
        String query="SELECT * FROM "+TABLE_NAME +" ";
        Cursor cursor = getDb().rawQuery(query, null);
        if(cursor.moveToNext())
            return true;
        else
            return false;
    }

    public String getLoggerCode(){
        setDb(this.getReadableDatabase());
        String query="SELECT * FROM "+TABLE_NAME +"";
        Cursor cursor = getDb().rawQuery(query, null);
        if(cursor.moveToFirst())
            return cursor.getString(1);
        else
            return null;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
