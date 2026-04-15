package com.yeasinrabbee.zafariqbal.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db extends SQLiteOpenHelper {



    private Context context;

    public static   String ID = "id";

    public static   String TABLE = "history";
    public static   String TITLE = "title";
    public static   String CONTENT = "content";
    public static   String CATEGORY = "category";
    public static   String Main_CATEGORY = "main_category";
    public static   String TIME = "time";
    public static   String TYPE = "type";



    public Db(@Nullable Context context) {
        super(context, DbName.DB_NAME, null, DbName.DB_VERSION);
        this.context = context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {


        String chats = "CREATE TABLE "+TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" TEXT, "+CONTENT+" TEXT, "
                +CATEGORY+" TEXT,  " +Main_CATEGORY+" TEXT, "+TIME+" INTEGER, "+TYPE+" INTEGER)";

        db.execSQL(chats);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);

    }




}
