package com.example.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_CALL_LOGS = "callLogs";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FAV = "favorite";
    private static final String KEY_BLOCKED = "blocked";

    private static final String KEY_CALL_LOG_ID = "id";
    private static final String KEY_CALL_NAME = "name";
    private static final String KEY_CALL_PH_NO = "phone_number";
    private static final String KEY_CALL_DAY = "call_day";
    private static final String KEY_CALL_TIME = "call_time";
    private static final String KEY_CALL_DATE = "call_date";
    private static final String KEY_CALL_TYPE = "call_type";
    private static final String KEY_CALL_DURATION = "duration";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_FAV + " INTEGER DEFAULT 0,"
                + KEY_BLOCKED + " INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE " + TABLE_CALL_LOGS + " ("
                + KEY_CALL_LOG_ID + " INTEGER PRIMARY KEY,"
                + KEY_CALL_NAME + " TEXT,"
                + KEY_CALL_PH_NO + " TEXT,"
                + KEY_CALL_DAY + " TEXT,"
                + KEY_CALL_TIME + " TEXT,"
                + KEY_CALL_DATE + " TEXT,"
                + KEY_CALL_TYPE + " TEXT,"
                + KEY_CALL_DURATION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALL_LOGS);
        onCreate(db);
    }

    // CONTACTS
    public void addContact(Contact contact) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());
            values.put(KEY_EMAIL, contact.getEmail());
            values.put(KEY_FAV, contact.getFavorite());
            values.put(KEY_BLOCKED, contact.getBlocked());
            db.insert(TABLE_CONTACTS, null, values);
        }
    }

    public Contact getContact(int id) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO, KEY_EMAIL, KEY_FAV, KEY_BLOCKED},
                     KEY_ID + "=?", new String[]{String.valueOf(id)},
                     null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5));
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " ORDER BY " + KEY_NAME + " COLLATE NOCASE ASC";
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setId(cursor.getInt(0));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));
                    contact.setEmail(cursor.getString(3));
                    contact.setFavorite(cursor.getInt(4));
                    contact.setBlocked(cursor.getInt(5));
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        }
        return contactList;
    }

    public int getContactsCount() {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null)) {
            return cursor.getCount();
        }
    }

    public int updateContact(Contact contact) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.getName());
            values.put(KEY_PH_NO, contact.getPhoneNumber());
            values.put(KEY_EMAIL, contact.getEmail());
            values.put(KEY_FAV, contact.getFavorite());
            values.put(KEY_BLOCKED, contact.getBlocked());
            return db.update(TABLE_CONTACTS, values, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
        }
    }

    public void deleteContact(int contactId) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_CONTACTS, KEY_ID + "=?", new String[]{String.valueOf(contactId)});
        }
    }

    public void deleteAllContacts() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_CONTACTS);
        }
    }


//    public void toggleFavorite(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Toggle favorite status
//        int newFavoriteValue = contact.getFavorite() == 1 ? 0 : 1;
//
//        ContentValues values = new ContentValues();
//        values.put("favorite", newFavoriteValue);  // Update the favorite column
//
//        // Update the contact in the database
//        db.update("contacts", values, "id = ?", new String[]{String.valueOf(contact.getId())});
//
//        // Update the contact object in memory
//        contact.setFavorite(newFavoriteValue);
//
//        db.close();  // Close the database connection
//    }

    public void toggleBlockedContact(Contact contact) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            int newBlockedValue = (contact.getBlocked() == 1) ? 0 : 1;
            ContentValues values = new ContentValues();
            values.put(KEY_BLOCKED, newBlockedValue);
            db.update(TABLE_CONTACTS, values, KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
            contact.setBlocked(newBlockedValue);
        }
    }

    // CALL LOGS
    public void addCallLog(CallLog callLog) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_CALL_NAME, callLog.getName());
            values.put(KEY_CALL_PH_NO, callLog.getPhoneNumber());
            values.put(KEY_CALL_DAY, callLog.getCallDay());
            values.put(KEY_CALL_TIME, callLog.getCallTime());
            values.put(KEY_CALL_DATE, callLog.getCallDate());
            values.put(KEY_CALL_TYPE, callLog.getCallType());
            values.put(KEY_CALL_DURATION, callLog.getDuration());
            db.insert(TABLE_CALL_LOGS, null, values);
        }
    }

    public void deleteCallLog(int id) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(TABLE_CALL_LOGS, KEY_CALL_LOG_ID + "=?", new String[]{String.valueOf(id)});
        }
    }

    public void deleteAllCallLogs() {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_CALL_LOGS);
        }
    }


    public List<CallLog> searchCallLogs(String query) {
        List<CallLog> callLogsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CALL_LOGS +
                " WHERE " + KEY_CALL_NAME + " LIKE ? OR " +
                KEY_CALL_PH_NO + " LIKE ? " +
                "ORDER BY " + KEY_CALL_DATE + " ASC, " + KEY_CALL_TIME + " ASC";

        String searchPattern = "%" + query + "%"; // Pattern for searching

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectQuery, new String[] { searchPattern, searchPattern })) {
            if (cursor.moveToFirst()) {
                do {
                    CallLog callLog = new CallLog();
                    callLog.setId(cursor.getInt(0));              // ID
                    callLog.setName(cursor.getString(1));         // Name
                    callLog.setPhoneNumber(cursor.getString(2));  // Phone number
                    callLog.setCallDay(cursor.getString(3));      // Call day
                    callLog.setCallTime(cursor.getString(4));     // Call time
                    callLog.setCallDate(cursor.getString(5));     // Call date
                    callLog.setCallType(cursor.getString(6));     // Call type
                    callLog.setDuration(cursor.getString(7));      // Duration
                    callLogsList.add(callLog);
                } while (cursor.moveToNext());
            }
        }
        return callLogsList;
    }

    public void updateCallDuration(int callLogId, String newDuration) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(KEY_CALL_DURATION, newDuration);
            db.update(TABLE_CALL_LOGS, values, KEY_CALL_LOG_ID + "=?", new String[]{String.valueOf(callLogId)});
        }
    }


    public List<CallLog> getAllCallLogs() {
        List<CallLog> callLogsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CALL_LOGS + " ORDER BY " + KEY_CALL_DATE + " DESC, "
                + KEY_CALL_TIME + " DESC";
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                do {
                    CallLog callLog = new CallLog();
                    callLog.setId(cursor.getInt(0));              // ID
                    callLog.setName(cursor.getString(1));         // Name (new)
                    callLog.setPhoneNumber(cursor.getString(2));  // Phone number
                    callLog.setCallDay(cursor.getString(3));      // Call day
                    callLog.setCallTime(cursor.getString(4));     // Call time
                    callLog.setCallDate(cursor.getString(5));     // Call date
                    callLog.setCallType(cursor.getString(6));     // Call type
                    callLog.setDuration(cursor.getString(7));
                    callLogsList.add(callLog);
                } while (cursor.moveToNext());
            }
        }
        return callLogsList;
    }
}
