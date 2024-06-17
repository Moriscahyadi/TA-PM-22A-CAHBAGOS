package com.uti.lumbung

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbData(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE tanaman (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, kapasitas INTEGER, isi INTEGER)")
        val tanaman = arrayOf("Padi", "Jagung", "Singkong")
        for (nama in tanaman) {
            val values = ContentValues().apply {
                put("nama", nama)
                put("kapasitas", 4000)
                put("isi", 0)
            }
            db.insert("tanaman", null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tanaman")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SmartLumbung.db"
    }
}