package com.example.exp5_100

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "students.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE ${DBContract.StudentEntry.TABLE_NAME} (" +
                "${DBContract.StudentEntry.COLUMN_REGISTER} TEXT PRIMARY KEY," +
                "${DBContract.StudentEntry.COLUMN_NAME} TEXT," +
                "${DBContract.StudentEntry.COLUMN_CGPA} REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.StudentEntry.TABLE_NAME}")
        onCreate(db)
    }

    // ✅ INSERT Student
    fun insertStudent(student: UserModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.StudentEntry.COLUMN_REGISTER, student.registerNumber)
        values.put(DBContract.StudentEntry.COLUMN_NAME, student.name)
        values.put(DBContract.StudentEntry.COLUMN_CGPA, student.cgpa)

        val success = db.insert(DBContract.StudentEntry.TABLE_NAME, null, values)
        db.close()
        return success != -1L
    }

    // ✅ FETCH All Students
    fun getAllStudents(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM ${DBContract.StudentEntry.TABLE_NAME}", null)
    }

    // ✅ UPDATE Student Details
    fun updateStudent(student: UserModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.StudentEntry.COLUMN_NAME, student.name)
        values.put(DBContract.StudentEntry.COLUMN_CGPA, student.cgpa)

        val success = db.update(
            DBContract.StudentEntry.TABLE_NAME,
            values,
            "${DBContract.StudentEntry.COLUMN_REGISTER} = ?",
            arrayOf(student.registerNumber)
        )
        db.close()
        return success > 0
    }

    // ✅ DELETE Student by Register Number
    fun deleteStudent(registerNumber: String): Boolean {
        val db = this.writableDatabase
        val success = db.delete(
            DBContract.StudentEntry.TABLE_NAME,
            "${DBContract.StudentEntry.COLUMN_REGISTER} = ?",
            arrayOf(registerNumber)
        )
        db.close()
        return success > 0
    }
}
