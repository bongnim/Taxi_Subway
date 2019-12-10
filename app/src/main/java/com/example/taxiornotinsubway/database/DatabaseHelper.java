package com.example.taxiornotinsubway.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.taxiornotinsubway.database.model.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String type, String start, String end, String start_x, String start_y, String end_x, String end_y) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_TYPE, type);
        values.put(Note.COLUMN_START,start);
        values.put(Note.COLUMN_END,end);
        values.put(Note.COLUMN_START_X, start_x);
        values.put(Note.COLUMN_START_Y,start_y);
        values.put(Note.COLUMN_END_X, end_x);
        values.put(Note.COLUMN_END_Y, end_y);

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_TYPE,Note.COLUMN_START,Note.COLUMN_END,Note.COLUMN_START_X,Note.COLUMN_START_Y,Note.COLUMN_END_X,Note.COLUMN_END_Y, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_END)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_X)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_Y)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_X)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_Y)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }

//    public List<Note> getAllNotes() {
//        List<Note> notes = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
//                Note.COLUMN_TIMESTAMP + " DESC";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Note note = new Note();
//                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
//
//                notes.add(note);
//            } while (cursor.moveToNext());
//        }
//
//        // close db connection
//        db.close();
//
//        // return notes list
//        return notes;
//    }
    //    history
    public List<Note> getAllHistory(){
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " WHERE type='history'" + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setType(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TYPE)));
                note.setStart(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START)));
                note.setEnd(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END)));
                note.setStart_x(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_X)));
                note.setStart_y(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_Y)));
                note.setEnd_x(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_X)));
                note.setEnd_y(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_Y)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
     return notes;
    }
//    bookmark
    public List<Note> getAllBookMark(){
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " WHERE type='bookmark'" + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setType(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TYPE)));
                note.setStart(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START)));
                note.setEnd(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END)));
                note.setStart_x(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_X)));
                note.setStart_y(cursor.getString(cursor.getColumnIndex(Note.COLUMN_START_Y)));
                note.setEnd_x(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_X)));
                note.setEnd_y(cursor.getString(cursor.getColumnIndex(Note.COLUMN_END_Y)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TYPE, note.getType());
        values.put(Note.COLUMN_START,note.getStart());
        values.put(Note.COLUMN_END,note.getEnd());
        values.put(Note.COLUMN_START_X,note.getStart_x() );
        values.put(Note.COLUMN_START_Y,note.getStart_y());
        values.put(Note.COLUMN_END_X, note.getEnd_x());
        values.put(Note.COLUMN_END_Y, note.getEnd_y());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
