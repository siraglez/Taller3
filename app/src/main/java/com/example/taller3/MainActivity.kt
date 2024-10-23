package com.example.taller3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : ComponentActivity() {

    //Lanzador de permisos para manejar la solicitud de WRITE_EXTERNAL_STORAGE
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            //Permiso concedido
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/datos_externos.txt"
            AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")
            val datosExternos = AlmacenamientoUtils.leerDesdeAlmacenamientoExterno(filePath)
            Log.d("Externo", "Datos leídos del almacenamiento externo: $datosExternos")
        } else {
            //Permiso denegado
            Log.e("Permisos", "Permiso de almacenamiento externo denegado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Almacenamiento externo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val externalDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val filePath = "${externalDir?.absolutePath}/datos_externos.txt"
            AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")

            val datosExternos = AlmacenamientoUtils.leerDesdeAlmacenamientoExterno(filePath)
            Log.d("Externo", "Datos leídos del almacenamiento externo: $datosExternos")
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/datos_externos.txt"
                AlmacenamientoUtils.guardarEnAlmacenamientoExterno(filePath, "Algunos datos de prueba")

                val datosExternos = AlmacenamientoUtils.leerDesdeAlmacenamientoExterno(filePath)
                Log.d("Externo", "Datos leídos del almacenamiento externo: $datosExternos")
            } else {
                //Solicitar permisos de escritura en almacenamiento externo
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        //Almacenamiento interno
        AlmacenamientoUtils.guardarEnAlmacenamientoInterno(this, "datos_internos.txt", "Almacenamiento interno")
        val datosInternos = AlmacenamientoUtils.leerDesdeAlmacenamientoInterno(this, "datos_internos.txt")
        Log.d("Interno", "Datos leídos del almacenamiento interno: $datosInternos")

        //Establecer el contenido de la pantalla
        setContent {
            MainScreen { navigateToMainActivity() }
        }
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
        modifier = Modifier
            .fillMaxSize()
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
