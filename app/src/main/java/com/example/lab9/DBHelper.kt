package com.example.lab9

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "students.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DBContract.StudentEntry.TABLE_NAME} (" +
                    "${DBContract.StudentEntry.ID} INTEGER PRIMARY KEY," +
                    "${DBContract.StudentEntry.COLUMN_NAME} TEXT," +
                    "${DBContract.StudentEntry.COLUMN_AGE} INTEGER)"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${DBContract.StudentEntry.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}