package com.example.taller3

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NetworkTask(
    private val listener: OnTaskCompleted,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {
    private var job: Job? = null

    //Función para iniciar la tarea simulada
    fun startTask() {
        job = CoroutineScope(coroutineContext).launch {
            for (i in 1..10) {
                delay(500L) //Simular tiempo de espera (500 ms)
                listener.onTaskComplete(i * 10) //Notificar progreso
            }
            listener.onTaskComplete(100) //Finalizar progreso
        }
    }

    //Función para cancelar la tarea si es necesario
    fun cancelTask() {
        job?.cancel()
    }
}
