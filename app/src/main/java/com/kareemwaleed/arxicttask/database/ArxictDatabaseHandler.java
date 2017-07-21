package com.kareemwaleed.arxicttask.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kareem Waleed on 7/2/2017.
 */

public class ArxictDatabaseHandler {
    private Context context;
    private SQLiteDatabase database;

    /**
     * Creates the database and the user table on create and instance of the handler
     */
    public ArxictDatabaseHandler(Context context){
        this.context = context;
        database = context.openOrCreateDatabase("arxict", Context.MODE_PRIVATE, null);
        String query = "CREATE TABLE IF NOT EXISTS user (name VARCHAR(50), email VARCHAR(50), password VARCHAR(50), primary key(email))";
        try{
            database.execSQL(query);
        }catch (Exception e){
            return;
        }
    }

    /**
     * Search the database for an email address/password match
     */
    public boolean login(String emailAddress, String password){
        String query = "SELECT * FROM user WHERE email = '" + emailAddress + "' and password = '" + password + "'";
        Cursor cursor;
        try{
            cursor = database.rawQuery(query, null);
        }catch (Exception e){
            return false;
        }
        if(cursor != null && cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    /**
     * Inserts the user data in the database
     */
    public boolean createAccount(String fullName, String emailAddress, String password){
        String query = "INSERT INTO user (name, email, password) VALUES('" + fullName + "', '" + emailAddress
                +"', '"+ password + "')";
        try {
            database.execSQL(query);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
