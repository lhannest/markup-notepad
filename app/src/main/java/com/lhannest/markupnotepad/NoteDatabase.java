package com.lhannest.markupnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase database;

    private final Context context;

    private final String DATABASE_NAME = "note_database";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME = "note_table";
    private final String ID = "_id";
    private final String TITLE = "textView";
    private final String LAST_TIME_EDITED = "last_time_edited";
    private final String TIME_CREATED = "time_created";
    private final String WORD_COUNT = "word_count";

    private final int INDEX_ID = 0;
    private final int INDEX_TITLE = 1;
    private final int INDEX_LAST_TIME_EDITED = 2;
    private final int INDEX_TIME_CREATED = 3;
    private final int INDEX_WORD_COUNT = 4;


    public NoteDatabase(Context context) {
        this.context = context;
        helper = makeSQLiteHelper();
    }

    private SQLiteOpenHelper makeSQLiteHelper() {
        return new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " +
                        TABLE_NAME +
                        "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE + " TEXT, " + LAST_TIME_EDITED + " LONG, " +
                        TIME_CREATED + " LONG, " + WORD_COUNT + " INTEGER)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    public List<NoteData> getAll() {
        List<NoteData> notes = new ArrayList<NoteData>();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(INDEX_ID);
            String title = cursor.getString(INDEX_TITLE);
            long lastTimeEdited = cursor.getLong(INDEX_LAST_TIME_EDITED);
            long timeCreated = cursor.getLong(INDEX_TIME_CREATED);
            int wordCount = cursor.getInt(INDEX_WORD_COUNT);

            notes.add(new NoteData(id, title, new Time(lastTimeEdited),
                    new Time(timeCreated), wordCount));

            cursor.moveToNext();
        }

        cursor.close();

        return notes;
    }

    public long add(NoteData note) {
        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(LAST_TIME_EDITED, note.getLastEdited().get());
        values.put(TIME_CREATED, note.getTimeCreated().get());
        values.put(WORD_COUNT, note.getWordCount());

        long id = database.insert(TABLE_NAME, null, values);

        return id;
    }

    public int updateById(long id, NoteData data) {
        ContentValues values = new ContentValues();
        values.put(TITLE, data.getTitle());
        values.put(LAST_TIME_EDITED, data.getLastEdited().get());
        values.put(TIME_CREATED, data.getTimeCreated().get());
        values.put(WORD_COUNT, data.getWordCount());
        return database.update(TABLE_NAME, values, ID + "=" + id, null);
    }

    public int deleteById(long id) {
        return database.delete(TABLE_NAME, ID + "=" + id, null);
    }

    public NoteData getById(int id) {
        String strId = "" + id;
        Cursor cursor = database.query(TABLE_NAME, null, ID + "=?", new String[]{strId}, null, null, null);

        cursor.moveToFirst();

        if (cursor.isAfterLast()) {
            throw new NoteNotFoundException("no note with id=" + id);
        } else {
            String title = cursor.getString(INDEX_TITLE);
            long lastTimeEdited = cursor.getLong(INDEX_LAST_TIME_EDITED);
            long timeCreated = cursor.getLong(INDEX_TIME_CREATED);
            int wordCount = cursor.getInt(INDEX_WORD_COUNT);

            return new NoteData(id, title, new Time(lastTimeEdited),
                    new Time(timeCreated), wordCount);
        }
    }

    public NoteData getByPosition(int position) {
        int k = 0;
        for (NoteData noteData: getAll()) {
            if (k == position) {
                return noteData;
            } else {
                k++;
            }
        }

        throw new NoteNotFoundException("There is no " + position + "th note.");
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public boolean isOpen() {
        return database.isOpen();
    }

    private class NoteNotFoundException extends RuntimeException {
        public NoteNotFoundException(String message) {
            super(message);
        }
    }
}
