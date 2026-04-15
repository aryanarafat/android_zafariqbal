package com.yeasinrabbee.zafariqbal.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class Helper {

    private Context context;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public static int TYPE_HISTORY = 1;
    public static int TYPE_FAV = 2;

    public Helper(Context context) {
        this.context = context;
        this.sqLiteOpenHelper = new Db(context);
    }



    public void add(String title,String content,String category,String main_category,long time,int type){

        Cursor cursor  = getAt(title,type);

        if (cursor != null && cursor.getCount()>0){

            update(title,type);
            return;

        }

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Db.TITLE,title);
        cv.put(Db.CONTENT,content);
        cv.put(Db.TIME,time);
        cv.put(Db.CATEGORY,category);
        cv.put(Db.TYPE,type);
        cv.put(Db.Main_CATEGORY,main_category);
        long result =  db.insert(Db.TABLE,null,cv);

    }

    public Cursor getAllHistory(){

        String query = "SELECT  * FROM "+Db.TABLE +" WHERE "+Db.TYPE+"=?  ORDER BY "+Db.TIME+" DESC";
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = null;

        if (db!=null){
            cursor =  db.rawQuery(query,new String[] {String.valueOf(TYPE_HISTORY)});
        }

        return  cursor;

    }


    public Cursor getAllFav(){

        String query = "SELECT  * FROM "+Db.TABLE +" WHERE "+Db.TYPE+"=?  ORDER BY "+Db.TIME+" DESC";
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = null;

        if (db!=null){
            cursor =  db.rawQuery(query,new String[] {String.valueOf(TYPE_FAV)});
        }

        return  cursor;

    }

    public Cursor getAt(String title,int type){

        String query = "SELECT  * FROM "+Db.TABLE +" WHERE "+Db.TITLE+"=? and "+Db.TYPE+"=? ORDER BY "+Db.TIME+" DESC";
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = null;

        if (db!=null){
            cursor =  db.rawQuery(query,new String[] {title, String.valueOf(type)});
        }

        return  cursor;

    }




    public void update(String title,int type){

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Db.TIME, new Date().getTime());

        db.update(Db.TABLE, cv, Db.TITLE+"= ? and "+Db.TYPE+"= ?", new String[]{title, String.valueOf(type)});

    }


    public void delete(int id,onDelete cb){

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
       int result = db.delete(Db.TABLE,  Db.ID+"= ?", new String[]{String.valueOf(id)});

       if (result != -1){
           cb.delete();

       }

    }


    public void deleteHis(){

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        int result = db.delete(Db.TABLE,  Db.TYPE+"= ?", new String[]{String.valueOf(TYPE_HISTORY)});



    }

    public void deleteFav(){

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        int result = db.delete(Db.TABLE,  Db.TYPE+"= ?", new String[]{String.valueOf(TYPE_FAV)});



    }


    //instant show kore na

    public interface onDelete{
        void delete();
    }



}
