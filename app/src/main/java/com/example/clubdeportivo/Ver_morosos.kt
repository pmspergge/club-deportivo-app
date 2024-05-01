package com.example.clubdeportivo

import AdaptadorTabla
import DatosTabla
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ver_morosos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_morosos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
// Obtener una referencia al RecyclerView desde el diseño
        val recyclerView: RecyclerView = findViewById(R.id.tablaMorosos)

        // Crear una lista de datos para la tabla (supongamos que tienes una lista llamada datosTabla)
        val datosTabla = obtenerDatosTabla()

        // Crear una instancia del adaptador y pasarle la lista de datos
        val adaptador = AdaptadorTabla(this, datosTabla)

        // Configurar RecyclerView con un LinearLayoutManager (vertical)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Asignar el adaptador al RecyclerView
        recyclerView.adapter = adaptador

    }

    private fun obtenerDatosTabla(): List<DatosTabla> {
        // Aquí puedes retornar una lista de datos para tu tabla
        // Por ejemplo, podrías consultar una base de datos o crear datos de muestra manualmente
        return listOf(
            DatosTabla("Nombre1", "Importe1"),
            DatosTabla("Nombre2", "Importe2"),
            // Agrega más datos según sea necesario
        )
    }
}