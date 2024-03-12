package com.example.personalizedcontactsmanagementapp4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactContract.ContactEntry.TABLE_NAME + " (" +
                    ContactContract.ContactEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ContactContract.ContactEntry.COLUMN_NAME + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_EMAIL + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_PHONE_NUMBER + " TEXT," +
                    ContactContract.ContactEntry.COLUMN_BIRTHDAY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
