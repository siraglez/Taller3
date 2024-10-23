package com.example.taller3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

object AlmacenamientoUtils {

    //Funciones para almacenamiento interno
    fun guardarEnAlmacenamientoInterno(context: Context, fileName: String, data: String) {
        try {
            val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fos.write(data.toByteArray())
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerDesdeAlmacenamientoInterno(context: Context, fileName: String): String? {
        return try {
            val fis = context.openFileInput(fileName)
            val bytes = fis.readBytes()
            fis.close()
            String(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //Funciones para almacenamiento externo
    fun guardarEnAlmacenamientoExterno(fileName: String, data: String) {
        if (isAlmacenamientoExternoDisponible()) {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
            try {
                file.writeText(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun leerDesdeAlmacenamientoExterno(fileName: String): String? {
        if (isAlmacenamientoExternoDisponible()) {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName)
            return try {
                file.readText()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }

    private fun isAlmacenamientoExternoDisponible(): Boolean {
        return android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
    }

    //Funciones para almacenar y leer imágenes
    fun guardarImagenInterna(context: Context, bitmap: Bitmap, nombreArchivo: String) {
        val fileOutputStream = context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
    }

    fun leerImagenInterna(context: Context, nombreArchivo: String): Bitmap? {
        return try {
            val fileInputStream = context.openFileInput(nombreArchivo)
            BitmapFactory.decodeStream(fileInputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}