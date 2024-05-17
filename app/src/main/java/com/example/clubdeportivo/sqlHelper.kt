package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlHelper(context: Context):SQLiteOpenHelper(context, "clubDeportivo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val tableCreate = "CREATE TABLE Users" + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(55), password VARCHAR(22))";
        db.execSQL(tableCreate);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun InsertData(name: String, password: String){
        val data = ContentValues();
        data.put("name", name);
        data.put("password", password);

        val db = this.writableDatabase;
        db.insert("Users", null, data);

        db.close();
    }

}