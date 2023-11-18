package com.example.memory

data class Carta(
    val nombre: String,
    val imagen: Int, // Usamos Int para representar la referencia a la imagen (drawable)
    var cara: Boolean,
    val valor: Int,
    val reverso:Int,
    var emparejada:Boolean=false
)

fun cartaList(): List<Carta> = datos().flatMap { listOf(it, it) }

fun cartaListFacil(): List<Carta> = datosFacil().flatMap { listOf(it, it) }

private fun datosFacil():List<Carta>{
    val reverso = R.drawable.ic_reverso_carta
    val datos = listOf(
        Carta("Josito", R.drawable.yo, false, 1, reverso),
        Carta("Marta", R.drawable.marta, false, 2, reverso),
        Carta("Ame", R.drawable.ame, false, 3, reverso),
        Carta("Alberto", R.drawable.alberto, false, 4, reverso),
        Carta("Sergio", R.drawable.sergio, false, 5, reverso),
    )
    return datos
}

private fun datos():List<Carta>{
    val reverso = R.drawable.ic_reverso_carta
    val datos = listOf(
        Carta("Josito", R.drawable.yo, false, 1, reverso),
        Carta("Marta", R.drawable.marta, false, 2, reverso),
        Carta("Ame", R.drawable.ame, false, 3, reverso),
        Carta("Alberto", R.drawable.alberto, false, 4, reverso),
        Carta("Sergio", R.drawable.sergio, false, 5, reverso),
        Carta("Amelia", R.drawable.amelia, false, 6, reverso),
        Carta("Raquel", R.drawable.raquel, false, 7, reverso),
        Carta("Vicentin", R.drawable.vicentin, false, 8, reverso),
        Carta("Mama", R.drawable.amparo, false, 9, reverso),
        Carta("Papa", R.drawable.miguel, false, 10, reverso)
    )
    return datos
}
