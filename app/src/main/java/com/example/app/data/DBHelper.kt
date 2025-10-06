package com.example.app.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context): SQLiteOpenHelper(
    context,"base_textReader.db",
    null,
    1
    ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
              CREATE TABLE registros(
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  nombre TEXT NOT NULL,
                  correo TEXT NOT NULL,
                  contrasena TEXT NOT NULL               
             )
             """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS registros")
        onCreate(db)

    }

}