package com.example.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by paozi on 2016/3/7.
 */
public class MyDBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "book.db";
    public static final String CREATE_BOOK = "create table book(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text" +
            ")";
    public static final String CREATE_CATEGORY = "create table category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer" +
            ")";
    private Context context;
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(context, "create table", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("drop table if exists book");
                db.execSQL(CREATE_BOOK);
            case 2:
                db.execSQL("drop table if exists category");
                db.execSQL(CREATE_CATEGORY);
            default:
                break;
        }
        Toast.makeText(context, "drop table", Toast.LENGTH_SHORT).show();
    }
}
