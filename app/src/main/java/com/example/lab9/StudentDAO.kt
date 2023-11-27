package com.example.lab9

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class StudentDAO(context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)
    private lateinit var database: SQLiteDatabase

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun insertStudent(student: StudentModel): Long {
        val values = ContentValues().apply {
            put(DBContract.StudentEntry.COLUMN_NAME, student.name)
            put(DBContract.StudentEntry.COLUMN_AGE, student.age)
        }

        return database.insert(DBContract.StudentEntry.TABLE_NAME, null, values)
    }

    fun updateStudent(student: StudentModel): Int {
        val values = ContentValues().apply {
            put(DBContract.StudentEntry.COLUMN_NAME, student.name)
            put(DBContract.StudentEntry.COLUMN_AGE, student.age)
        }

        return database.update(
            DBContract.StudentEntry.TABLE_NAME,
            values,
            "${DBContract.StudentEntry.ID} = ?",
            arrayOf(student.id.toString())
        )
    }

    fun getAllStudents(): List<StudentModel> {
        val students = mutableListOf<StudentModel>()
        val cursor: Cursor = database.query(
            DBContract.StudentEntry.TABLE_NAME,
            null, null, null, null, null, null
        )

        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val student = cursorToStudent(cursor)
            students.add(student)
            cursor.moveToNext()
        }
        cursor.close()

        return students
    }

    @SuppressLint("Range")
    private fun cursorToStudent(cursor: Cursor): StudentModel {
        val student = StudentModel()
        student.id = cursor.getInt(cursor.getColumnIndex(DBContract.StudentEntry.ID))
        student.name = cursor.getString(cursor.getColumnIndex(DBContract.StudentEntry.COLUMN_NAME))
        student.age = cursor.getInt(cursor.getColumnIndex(DBContract.StudentEntry.COLUMN_AGE))
        return student
    }
}