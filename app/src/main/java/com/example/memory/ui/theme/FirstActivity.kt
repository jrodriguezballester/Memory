package com.example.memory.ui.theme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.memory.MainActivity
import com.example.memory.ui.theme.ui.theme.MemoryTheme


class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        // Sección de texto
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally


                        ){
                            Greeting(  )
                        }


                        var opcionSeleccionada by remember { mutableStateOf("") }
                        // Dividir verticalmente en dos secciones
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            // Sección de botones
                            BotonFacil(opcionSeleccionada) { opcionSeleccionada = "Facil" }
                            BotonDificil(opcionSeleccionada) { opcionSeleccionada = "Dificil" }
                        }

//
//                        BotonFacil(opcionSeleccionada) { opcionSeleccionada = "Facil" }
//                        BotonDificil(opcionSeleccionada) { opcionSeleccionada = "Dificil" }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Juego de hacer parejas",
        modifier = modifier
            .padding(bottom = 16.dp),

        style = typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary

    )

}

@Composable
fun BotonFacil(opcionSeleccionada: String, onSeleccion: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onSeleccion("Facil", context)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Facil")
    }
}


@Composable
fun BotonDificil(opcionSeleccionada: String, onSeleccion: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = {
            onSeleccion("Dificil", context)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Dificil")
    }
}


fun onSeleccion(opcionSeleccionada: String, context: Context) {
    Log.d("Start", "Opción Seleccionada: $opcionSeleccionada")
    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("opcionSeleccionada", opcionSeleccionada)
    }
    context.startActivity(intent)
}