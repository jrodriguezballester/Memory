package com.example.memory


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.memory.ui.theme.MemoryTheme
import kotlin.math.ceil


private lateinit var cartas: MutableList<Carta>
var isFacil = true

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica si el Intent tiene el extra "opcionSeleccionada"
        if (intent.hasExtra("opcionSeleccionada")) {
            // Recupera el valor del extra "opcionSeleccionada"
            val opcionSeleccionada = intent.getStringExtra("opcionSeleccionada")
            isFacil = opcionSeleccionada == "Facil"
            // Carga cartas y baraja
            ejecutar()

            // Ahora puedes usar opcionSeleccionada según tus necesidades
            Log.d("MainActivity", "Opción Seleccionada: $opcionSeleccionada")
            Log.d("MainActivity", "Opción isFacil: $isFacil ")
        }

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
                    ) {
                        ImageGrid(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(3f)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Primer elemento
                            BotonReiniciar(
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(16.dp)
                            )
                            // Espacio entre elementos (puedes ajustar el tamaño según tus necesidades)
                            Spacer(modifier = Modifier.width(16.dp))

                            // Segundo elemento
                            BotonSalir(
                                modifier = Modifier
                                    .height(50.dp)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun ejecutar() {
        cartas = if (isFacil) {
            cartaListFacil().toMutableStateList()
        } else {
            cartaList().toMutableStateList()
        }

        reiniciar()
    }
}

@Composable
fun BotonReiniciar(modifier: Modifier) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Volver atras
            (context as? ComponentActivity)?.finish()
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Text("Volver")
        }
    }
}


@Composable
fun BotonSalir(modifier: Modifier) {
    val activity = LocalContext.current as? Activity
    Button(
        onClick = {
            activity?.finishAffinity()
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = null)
            Text("Salir")
        }
    }
}

fun reiniciar() {
    var nuevasCartas = cartas.map { it.copy(cara = false, emparejada = false) }
    nuevasCartas = nuevasCartas.shuffled()
    cartas.clear()
    cartas.addAll(nuevasCartas)
}


fun revisarGanar() {
    var ganar = true
    cartas.forEach { if (!it.emparejada) ganar = false }
    Log.i("MyTag", "GANA $ganar")
    var index = 0

    if (ganar) {

//
//        if (index < cartas.size) {
//            Log.i("MyTag", "index $index")
//            cartas[index].cara = false
//            index++
//        }
//
//        reiniciar()
    }
}


@Composable
fun ImageGrid(modifier: Modifier) {
    val indicesCartaVuelta: MutableList<Int> = mutableListOf()
    val cartasVueltas: MutableList<Carta> = mutableListOf()
    val columns = if (isFacil) 2 else 4

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Blue)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
        //  .border(2.dp, Color.Red)
    ) {
        items(ceil(cartas.size / columns.toDouble()).toInt()) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (colIndex in 0 until columns) {
                    val cartasIndex = rowIndex * columns + colIndex
                    val carta: Carta = cartas[cartasIndex]
                    val imageSize = if (isFacil) 90.dp else 80.dp
                    Box(
                        modifier = Modifier
                            .width(IntrinsicSize.Min) // Asegurar que el ancho sea solo el necesario
                            .clickable {
                                onImageClick(
                                    cartasIndex, carta, indicesCartaVuelta, cartasVueltas,
                                    cartas as SnapshotStateList<Carta>
                                )
                            }
                            .border(
                                if (carta.emparejada) 4.dp else 2.dp,
                                if (carta.emparejada) Color.Red else Color.Black
                            )

                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(
                                    id = if (carta.cara) carta.imagen else carta.reverso
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(imageSize) // Tamaño de la imagen
                            )

                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (carta.cara) carta.nombre else "",
                                fontWeight = FontWeight.Bold
                            )
                       }
                    }
                }
            }
            // Agregar un Spacer para separar las filas
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}


fun onImageClick(
    cartasIndex: Int, carta: Carta, indicesCartaVuelta: MutableList<Int>,
    cartasVueltas: MutableList<Carta>,
    cartas: SnapshotStateList<Carta>,
) {
    if (carta.cara) return // la carta ya esta vuelta
    if (carta.emparejada) return // No hacer nada si la carta ya está emparejada

    if (cartasVueltas.size == 2) {
        if (cartasVueltas[0].valor != cartasVueltas[1].valor) {
            for (i in 0..1) {
                cartasVueltas[i].emparejada = false
                cartasVueltas[i].cara = false
                cartas[indicesCartaVuelta[i]] = cartasVueltas[i]
            }
            cartasVueltas.clear()
            indicesCartaVuelta.clear()
        }
    }
    indicesCartaVuelta.add(cartasIndex)
    cartasVueltas.add(carta)
    // Actualiza la carta en el MutableStateList (da la vuelta a la carta)
    cartas[cartasIndex] = carta.copy(cara = !carta.cara)

    if (cartasVueltas.size == 2) {
        if (cartasVueltas[0].valor == cartasVueltas[1].valor) {
            for (i in 0..1) {
                cartasVueltas[i].emparejada = true
                cartasVueltas[i].cara = true
                cartas[indicesCartaVuelta[i]] = cartasVueltas[i]
            }
            cartasVueltas.clear()
            indicesCartaVuelta.clear()
        }
    }
    revisarGanar()
}



