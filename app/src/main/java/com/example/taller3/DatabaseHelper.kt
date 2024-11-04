package com.example.taller3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "UserTable"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    init {
        // Copiar la base de datos desde assets si no existe en el directorio de bases de datos
        val dbPath = context.getDatabasePath(DATABASE_NAME).path
        if (!File(dbPath).exists()) {
            copyDatabaseFromAssets(context, dbPath)
        }
    }

    private fun copyDatabaseFromAssets(context: Context, destinationPath: String) {
        try {
            context.assets.open("databases/$DATABASE_NAME").use { input ->
                FileOutputStream(destinationPath).use { output ->
                    input.copyTo(output)
                }
            }
            Log.d("DatabaseHelper", "Database copied successfully from assets.")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error copying database: ${e.message}")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
        Log.d("DatabaseHelper", "Table created successfully.")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Deja esta función vacía para evitar borrar la tabla y los datos
    }

    fun insertName(name: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        Log.d("DatabaseHelper", "Insert result: $result")
        return result != -1L
    }

    fun getAllNames(): List<String> {
        val names = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            names.add(name)
        }
        cursor.close()
        Log.d("DatabaseHelper", "Retrieved names: $names")
        return names
    }
}
