package com.example.personalizedcontactsmanagementapp4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.example.personalizedcontactsmanagementapp4.ContactModel;
import com.example.personalizedcontactsmanagementapp4.ContactContract;

public class ContactDAO {
    private DatabaseHelper dbHelper;

    public ContactDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addContact(ContactModel contact) throws SQLException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
            values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail());
            values.put(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
            values.put(ContactContract.ContactEntry.COLUMN_BIRTHDAY, contact.getBirthday());

            return db.insertOrThrow(ContactContract.ContactEntry.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }


    public int updateContact(ContactModel contact) throws SQLException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = 0;

        try {
            ContentValues values = new ContentValues();
            values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
            values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail());
            values.put(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
            values.put(ContactContract.ContactEntry.COLUMN_BIRTHDAY, contact.getBirthday());

            // Log the values being updated
            Log.d("ContactDAO", "Updating contact with ID: " + contact.getId());
            Log.d("ContactDAO", "New contact details: " + contact.toString());

            // Perform the update operation
            rowsAffected = db.update(ContactContract.ContactEntry.TABLE_NAME,
                    values,
                    ContactContract.ContactEntry.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(contact.getId())});

            // Log the number of rows affected
            Log.d("ContactDAO", "Rows affected: " + rowsAffected);
        } catch (Exception e) {
            // Handle any exceptions that occur during the update operation
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return rowsAffected;
    }



    public int deleteContact(long contactId) throws SQLException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            return db.delete(ContactContract.ContactEntry.TABLE_NAME,
                    ContactContract.ContactEntry.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(contactId)});
        } finally {
            db.close();
        }
    }

    public List<ContactModel> getAllContacts() throws SQLException {
        List<ContactModel> contactsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query(ContactContract.ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ContactModel contact = new ContactModel();
                    contact.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_ID)));
                    contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME)));
                    contact.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_EMAIL)));
                    contact.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_PHONE_NUMBER)));
                    contact.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_BIRTHDAY)));

                    contactsList.add(contact);
                } while (cursor.moveToNext());
            }
        }

        return contactsList;
    }
}
