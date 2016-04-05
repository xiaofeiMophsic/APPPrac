package com.example.myapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.myapp.db.MyDBHelper;

/**
 * Created by paozi on 2016/3/8.
 */
public class DataBaseContentProvider extends ContentProvider {

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;

    public static final String AUTHORITY = "com.example.myapp.provider";
    private static UriMatcher matcher;

    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    static{
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "book", BOOK_DIR);
        matcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        matcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        matcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        helper = new MyDBHelper(getContext(), MyDBHelper.DB_NAME, null, 2);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int code = matcher.match(uri);
        db = helper.getReadableDatabase();
        Cursor cursor = null;
        switch (code){
            case BOOK_DIR:
                cursor = db.query("book", projection,selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query("book", projection, "id = ?", new String[]{id}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("category", projection, "id = ?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        String mimeType = "";
        switch (matcher.match(uri)){
            case BOOK_DIR:
                mimeType = "vnd.android.cursor.dir/vnd." + AUTHORITY + ".book";
                break;
            case BOOK_ITEM:
                mimeType = "vnd.android.cursor.item/vnd." + AUTHORITY + ".book";
                break;
            case CATEGORY_DIR:
                mimeType = "vnd.android.cursor.dir/vnd." + AUTHORITY + ".category";
                break;
            case CATEGORY_ITEM:
                mimeType = "vnd.android.cursor.item/vnd." + AUTHORITY + ".category";
                break;
            default:
                break;
        }
        return mimeType;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = helper.getWritableDatabase();
        Uri returnUri = null;
        switch (matcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long bookId = db.insert("book", null, values);
                returnUri = Uri.parse("content://" + AUTHORITY + "/book/" + bookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long categoryId = db.insert("category", null, values);
                returnUri = Uri.parse("content://" + AUTHORITY + "/category/" + categoryId);
                break;
            default:
                break;
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = helper.getWritableDatabase();
        int deleteRows = 0;
        switch (matcher.match(uri)){
            case BOOK_DIR:
                deleteRows = db.delete("book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("book", "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows = db.delete("category", "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        db = helper.getWritableDatabase();
        int updateRows = 0;
        switch (matcher.match(uri)){
            case BOOK_DIR:
                updateRows = db.update("book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updateRows = db.update("book", values, "id = ?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update("category", values, "id = ?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return updateRows;
    }
}
