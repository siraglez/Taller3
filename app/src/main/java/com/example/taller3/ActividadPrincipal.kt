package com.example.taller3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ActividadPrincipal : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Recuperar el nombre almacenado desde SharedPreferences
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val nombreGuardado = sharedPref.getString("nombre_usuario", "") ?: ""

        setContent {
            //Pasamos el nombre guardado al Composable para inicializarlo
            ActividadPrincipalScreen(nombreGuardado, onGuardarNombre = { nombre ->
                guardarNombre(nombre)
            }, onNavigate = { navigateToConfig() })
        }
    }

    //Función para guardar el nombre en SharedPreferences
    private fun guardarNombre(nombre: String) {
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nombre_usuario", nombre)
            apply()
        }
    }

    private fun navigateToConfig() {
        val intent = Intent(this, PantallaConfiguracion::class.java)
        startActivity(intent)
    }
}

@Composable
fun ActividadPrincipalScreen(
    nombreInicial: String,
    onGuardarNombre: (String) -> Unit,
    onNavigate: () -> Unit
) {
    var nombre by remember { mutableStateOf("") } //Nombre ingresado en el campo de texto
    var nombreGuardado by remember { mutableStateOf(nombreInicial) } //Nombre que se mostrará
    var isLoading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ingresa tu nombre:")

        //Campo de texto para ingresar el nombre
        BasicTextField(
            value = nombre,
            onValueChange = { nombre = it },
            modifier = Modifier.padding(16.dp)
                .background(Color.Magenta)
                .width(200.dp)
                .height(40.dp)
        )
        //Botón para guardar nombre
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onGuardarNombre(nombre)
            nombreGuardado = nombre //Actualizar el nombre guardado
            nombre = "" //Vaciar el campo de texto
        }) {
            Text(text = "Guardar Nombre")
        }

        //Mostrar el nombre ingresado
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nombre ingresado: $nombreGuardado")

        //Botón para navegar a la configuración
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate() }) {
            Text(text = "Ir a Configuración")
        }

        //Botón para iniciar tarea en segundo plano
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!isLoading) { //Solo iniciar si no está en carga
                isLoading = true
                progress = 0 //Reiniciar el progreso
                NetworkTask(object : OnTaskCompleted {
                    override fun onTaskComplete(progressValue: Int) {
                        progress = progressValue
                        if (progress >= 100) {
                            isLoading = false //Finalizar la carga
                        }
                    }
                }).execute() //Crear una nueva instancia cada vez
            }
        }) {
            Text(text = "Iniciar Tarea")
        }

        //Indicador de progreso
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(progress = progress / 100f)
            Text(text = "Progreso: $progress%")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ActividadPrincipalScreenPreview() {
    ActividadPrincipalScreen(
        nombreInicial = "Android",
        onGuardarNombre = {},
        onNavigate = {}
    )
}
