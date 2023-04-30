package com.example.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmployeeDBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "Login.db";
    private static final int DBVER = 1;
    public EmployeeDBHelper(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE employeeTB(" +
                "employeeId TEXT primary key, " +
                "employeeRole TEXT, " +
                "employeeName TEXT, " +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("Drop Table if exists employeeTB");
    }

    //create admin if does not exist
    public void adminData(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from employeeTB where employeeID = ?", new String[] {"000001"});
        if (cursor.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("employeeId", "000001");
            contentValues.put("employeeRole", "admin");
            contentValues.put("employeeName", "admin");
            contentValues.put("password", "admin");
            MyDB.insert("employeeTB", null, contentValues);
        }
    }

    //add employee to table
    public boolean insertData(String id, String role, String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("employeeId",id);
        contentValues.put("employeeRole", role);
        contentValues.put("employeeName", username);
        contentValues.put("password", password);
        long result = MyDB.insert("employeeTB", null, contentValues);
        if (result<0) return false;
        else return true;
    }

    //remove employee from table
    public void removeData (String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("employeeTB", "employeeID = ?", new String[] {id});
    }

    //check if employee id is in use
    public boolean checkID(String ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from employeeTB where employeeId = ?", new String[] {ID});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    //get employee's role
    public String getRole(String ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * FROM employeeTB WHERE employeeId = ?", new String[] {ID});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            return String.valueOf(cursor.getString(1));
        }else
            return "error";
    }

    //get employee's name
    public String getName(String ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * FROM employeeTB WHERE employeeId = ?", new String[] {ID});
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            return String.valueOf(cursor.getString(2));
        }else
            return "error";
    }

    //check if id and password match
    public boolean checkIDPassword(String ID, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from employeeTB where employeeId = ? and password = ?", new String[] {ID, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }
}
