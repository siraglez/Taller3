package com.example.taller3

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Alamcenamiento externo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Usa el almacenamiento específico de la app
            val externalDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val filePath = "${externalDir?.absolutePath}/datos_externos.txt"
            AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")

            val datosExternos = AlmacenamientoUtils.leerDesdeAlmacenamientoExterno(filePath)
            Log.d("Externo", "Datos leídos del almacenamiento externo: $datosExternos")
        } else {
            //Para versiones anteriores de Android que requieren WRITE_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/datos_externos.txt"
                AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")

                val datosExternos = AlmacenamientoUtils.leerDesdeAlmacenamientoExterno(filePath)
                Log.d("Externo", "Datos leídos del almacenamiento externo: $datosExternos")
            } else {
                //Solicitar permisos para versiones anteriores si es necesario
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_CODE)
            }
        }

        //Almacenamiento interno
        AlmacenamientoUtils.guardarEnAlmacenamientoInterno(this, "datos_internos.txt", "Almacenamiento interno")
        val datosInternos = AlmacenamientoUtils.leerDesdeAlmacenamientoInterno(this, "datos_internos.txt")
        Log.d("Interno", "Datos leídos del almacenamiento interno: $datosInternos")

        setContent {
            MainScreen { navigateToMainActivity() }
        }
    }

     fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, continuar con el acceso al almacenamiento externo
                val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/datos_externos.txt"
                AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")
            }
        }
    }

    companion object {
        const val MY_PERMISSION_REQUEST_CODE = 1001
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, ActividadPrincipal::class.java)
        startActivity(intent)
    }
}

@Composable
fun MainScreen(onNavigate: () -> Unit) {
    val saludo = remember { calcularSaludo() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray)
    ) {
        Text(text = saludo)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate() }) {
            Text(text = "Ir a Actividad Principal")
        }
    }
}

private fun calcularSaludo(): String {
    val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hora in 0..11 -> "Buenos días"
        hora in 12..19 -> "Buenas tardes"
        else -> "Buenas noches"
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onNavigate = {})
}
