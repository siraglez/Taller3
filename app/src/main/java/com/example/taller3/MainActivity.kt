package com.example.taller3

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
