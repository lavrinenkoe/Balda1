package com.example.lavr.balda1.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lavr.balda1.controllers.WordsController;

import java.util.Vector;

/* Created by Lavr on 27.06.2017. */

public class SqlLiteDataProvider extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Balda1.db";
    private static final String WORDS_TABLE_NAME = "words";
    private static final String WORDS_COLUMN_VALUE = "value";
private Context _context;
    public SqlLiteDataProvider(Context context){
        super(context, DATABASE_NAME, null, 1);
        setContext(context);
    }

    public Context getContext() {
        return _context;
    }

    public void setContext(Context context) {
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(tableExists(WORDS_TABLE_NAME, db)) return;

        db.execSQL("create table IF NOT EXISTS " + WORDS_TABLE_NAME + " (" + WORDS_COLUMN_VALUE + " text primary key)");
        Vector<String> words = WordsController.getWords(this.getContext());
        insertDictionary(words, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE_NAME);
        onCreate(db);
    }

    private boolean tableExists(String tableName, SQLiteDatabase db){
        String Query = "select * from sqlite_master WHERE type='table' AND name=" + tableName;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    private  void insertDictionary(Vector<String> words, SQLiteDatabase db) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (int i = 0; i < words.size(); i++) {
                values.put(WORDS_COLUMN_VALUE, words.get(i));
                db.insert(WORDS_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public boolean insertWord(String word){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WORDS_COLUMN_VALUE, word);
        db.insert(WORDS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteWord (String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(WORDS_TABLE_NAME, "value = ? ", new String[] { word });
    }

    public Vector<String> getAllWords() {
        Vector<String> vector  = new Vector<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + WORDS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            vector.add(res.getString(res.getColumnIndex(WORDS_COLUMN_VALUE)));
            res.moveToNext();
        }
        res.close();
        return vector;
    }

    public String getRandomByLength(int length)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "select * from " + WORDS_TABLE_NAME + " where length(" + WORDS_COLUMN_VALUE + ") = " + Integer.toString(length) + " order by random() limit 1";
        Cursor res =  db.rawQuery(q, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            return res.getString(res.getColumnIndex(WORDS_COLUMN_VALUE));
        }
        return "";
   }

    public boolean wordExists(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "select * from " + WORDS_TABLE_NAME + " where " + WORDS_COLUMN_VALUE + " = '" + word + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
