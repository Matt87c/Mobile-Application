package com.example.myinventoryapp;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import java.util.Objects;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;

public class SQLiteDatabaseEndUser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "EndUserDatabase.DB";
    public static final String TABLE = "EndUser";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone_number";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COL_NAME + " VARCHAR, " +
            COL_PHONE + " VARCHAR, " +
            COL_EMAIL + " VARCHAR, " +
            COL_PASSWORD + " VARCHAR" + ");";

    public SQLiteDatabaseEndUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase database) {
        database.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE);
        onCreate(db);
    }

   // Create Read Update Delete Functionality
    // Creates end user to the database
    public void createUser(EndUser endUser) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, endUser.getEndUserName());
        values.put(COL_PHONE, endUser.getEndUserPhoneNumber());
        values.put(COL_EMAIL, endUser.getEndUserEmail());
        values.put(COL_PASSWORD, endUser.getEndUserPassword());
        db.insert(TABLE, null, values);
        db.close();
    }

    // Reads end user in the database
    public EndUser readEndUser(int id) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE,
                new String[] { COL_ID, COL_NAME, COL_PHONE, COL_EMAIL, COL_PASSWORD }, COL_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        EndUser endUser = new EndUser(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        cursor.close();
        return endUser;
    }

    // Update end user to the database
    public int updateEndUser(EndUser endUser) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, endUser.getEndUserName());
        values.put(COL_PHONE, endUser.getEndUserPhoneNumber());
        values.put(COL_EMAIL, endUser.getEndUserEmail());
        values.put(COL_PASSWORD, endUser.getEndUserPassword());

        return db.update(TABLE, values, COL_ID + " = ?", new String[] { String.valueOf(endUser.getId()) });
    }

    // The end user will be deleted from the database
    public void deleteEndUser(EndUser endUser) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE, COL_ID + " = ?", new String[] { String.valueOf(endUser.getId()) });
        db.close();
    }

    public java.util.List<EndUser> getAllUsers() {
        List<EndUser> endUserList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE;

        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                EndUser endUser = new EndUser();
                endUser.setId(Integer.parseInt(cursor.getString(0)));
                endUser.setEndUserName(cursor.getString(1));
                endUser.setEndUserPhoneNumber(cursor.getString(2));
                endUser.setEndUserEmail(cursor.getString(3));
                endUser.setEndUserPassword(cursor.getString(4));

                endUserList.add(endUser);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return endUserList;
    }

    public int getEndUser() {
        String queryEndUser = "SELECT * FROM " + TABLE;
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryEndUser, null);
        int total = cursor.getCount();
        cursor.close();

        return total;
    }



}
