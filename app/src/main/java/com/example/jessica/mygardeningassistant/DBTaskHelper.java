package com.example.jessica.mygardeningassistant;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;

public class DBTaskHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "task_table";

    private static final String COL1 = "ID";
    private static final String COL2 = "task";
    private static final String COL3 = "date";

    public DBTaskHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " + COL3 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String newEntryTask, String newEntryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, newEntryTask);
        contentValues.put(COL3, newEntryDate);

        if (getItemID(newEntryTask, newEntryDate).getCount() != 0)
            return false;

//        Log.d(TAG, "addData: Adding " + newEntryTask + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void updateData(String newTask, String newDate, int id){
        newTask = newTask.replace("'","''");
        newDate = newDate.replace("'","''");

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newTask + "', "
                + COL3 + " = '" + newDate
                + "' WHERE " + COL1 + " = '" + id + "'";
//        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String task, String date){
        task = task.replace("'","''");
        date = date.replace("'","''");
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + task + "' AND " + COL3 + " = '" + date + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'";
//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + id + " from database.");
        db.execSQL(query);
    }
}
