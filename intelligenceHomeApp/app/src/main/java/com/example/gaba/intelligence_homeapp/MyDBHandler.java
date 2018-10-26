package com.example.gaba.intelligence_homeapp;


import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "usersDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    /**
     * Constuctor of class MyDBHandler
     * @param context
     */
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_USERID + " INTEGER PRIMARY KEY,"+ COLUMN_USERNAME
                + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_ROLE + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }
    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    /**
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getRole());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    /**
     *
     * @param username
     * @return
     */
    public User findUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();

        if(cursor.moveToFirst()) {
            user.setUser_id(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setRole(cursor.getString(3));
        } else  {
            user = null;
        }
        db.close();
        return user;
    }
    /**
     *
     * @return
     */
    public User findAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = \"" + "Administrator" + "\"";
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();

        if(cursor.moveToFirst()) {
            user.setUser_id(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setRole(cursor.getString(3));
        } else  {
            user = null;
        }
        db.close();
        return user;
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean deleteUser(String username) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_USERS, COLUMN_USERID + " = " + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    /**
     *
     * @param admin
     * @return
     */
    //Returns True if just a SINGLE Admin user is existing in the database
    public boolean checkAdmin(String admin){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "Select * FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + " = \"" + admin + "\"";
        Cursor cursor = db.rawQuery(query, null);
        User user = new User(); //Unnecessary

        boolean result;
        //cursor is at the row where "admin" value would be found
        //but how to assure its the ONLY one??? and is my logic above correct?
        if(cursor.moveToFirst()) {
            result = true;

        } else  {
            result = false;
        }
        db.close();
        return result;
    }
    /**
     *
     */
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS,null,null);
    }

    /**
     *
     * @return
     */
    public String getAllUsernames(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "\n\nUser Accounts: ";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
                result += "\n"+ name + ": "+ role;
                cursor.moveToNext();
            }
        }
        db.close();
        return result;
    }
}
