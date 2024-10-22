package com.example.taller3

import android.os.AsyncTask

//Clase AsyncTask para simular una operación de red

class NetworkTask(private val listener: OnTaskCompleted) : AsyncTask<Void, Int, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 1..100) {
            Thread.sleep(500) //Simular tiempo de espera
            publishProgress(i * 10) //Publicar el progreso
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        values.firstOrNull()?.let { listener.onTaskComplete(it) } //Notifica el progreso al listener
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onTaskComplete(100) //Finaliza el progreso
    }
}
