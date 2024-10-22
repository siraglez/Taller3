package com.example.taller3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class PantallaConfiguracion : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Recuperar el color de fondo almacenado en SharedPreferences
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val colorGuardado = sharedPref.getInt("color_fondo", Color.LightGray.hashCode())

        setContent {
            PantallaConfiguracionScreen(
                backgroundColor = Color(colorGuardado),
                onColorSelected = { nuevoColor ->
                    guardarColor(nuevoColor)
                },
                onNavigate = { navigateToMain() }
            )
        }
    }

    //Guardar el color seleccionado en SharedPreferences
    private fun guardarColor(color: Color) {
        val sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("color_fondo", color.hashCode()) //Guardar el valor del color como un entero
            apply()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun PantallaConfiguracionScreen(
    backgroundColor: Color,
    onColorSelected: (Color) -> Unit,
    onNavigate: () -> Unit
) {
    var colorFondo by remember { mutableStateOf(backgroundColor) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
    ) {
        Text(text = "Selecciona un color para el fondo")

        //Botones para seleccionar diferentes colores
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            colorFondo = Color.Red
            onColorSelected(Color.Red) //Guardar la selección
        }) {
            Text(text = "Rojo")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            colorFondo = Color.Blue
            onColorSelected(Color.Blue) //Guardar la selección
        }) {
            Text(text = "Azul")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            colorFondo = Color.Green
            onColorSelected(Color.Green) //Guardar la selección
        }) {
            Text(text = "Verde")
        }

        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = {
            colorFondo = Color.LightGray
            onColorSelected(Color.LightGray) //Guardar la selección
            onNavigate() }) {
            Text(text = "Volver a Inicio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaConfiguracionScreenPreview() {
    PantallaConfiguracionScreen(
        backgroundColor = Color.LightGray,
        onColorSelected = {},
        onNavigate = {}
    )
}
