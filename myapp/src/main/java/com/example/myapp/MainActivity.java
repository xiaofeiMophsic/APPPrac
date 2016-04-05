package com.example.myapp;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapp.db.MyDBHelper;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private MyDBHelper helper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.create_database);
        helper = new MyDBHelper(this, MyDBHelper.DB_NAME, null, 2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("author", "xiaofei");
                values.put("price", 12);
                values.put("pages", 100);
                values.put("name", "android从入门到精通");
                db.insert("book", null, values);

                Cursor cursor = db.query("book", null, null, null, null, null, null);
                if(cursor.moveToFirst()){
                    int i = 1;
                    do{
                        Log.e("main", cursor.getInt(cursor.getColumnIndex("id")) + "" );
                        Log.e("main", cursor.getString(cursor.getColumnIndex("author")) + "" );;
                        Log.e("main", cursor.getInt(cursor.getColumnIndex("price")) + "" );;;
                        Log.e("main", cursor.getInt(cursor.getColumnIndex("pages")) + "" );
                        Log.e("main", cursor.getString(cursor.getColumnIndex("name")) + "" );
                        i = 1;
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }


}
