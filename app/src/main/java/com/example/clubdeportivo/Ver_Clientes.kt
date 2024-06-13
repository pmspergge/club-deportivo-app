package com.example.clubdeportivo

import androidx.activity.enableEdgeToEdge
import AdaptadorTabla
import AdaptadorTablaCliente
import DatosTabla
import DatosTablaClientes
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ver_Clientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_clientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val myButton = findViewById<ImageButton>(R.id.button_volver2)
        myButton.setOnClickListener {
            // Aquí es donde inicias la nueva actividad
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }



        val myButton2 = findViewById<Button>(R.id.button4)
        myButton2.setOnClickListener {
            // Aquí es donde inicias la nueva actividad
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }



        val myButton3: RecyclerView = findViewById(R.id.tablaClientes)
        myButton3.setOnClickListener {
            // Aquí es donde inicias la nueva actividad
            val intent = Intent(this, Editar_Cliente::class.java)
            startActivity(intent)
        }



        // Obtener una referencia al RecyclerView desde el diseño
        val recyclerView: RecyclerView = findViewById(R.id.tablaClientes)

        // Crear una lista de datos para la tabla (supongamos que tienes una lista llamada datosTabla)
        val DatosTablaClientes = obtenerDatosTabla()

        // Crear una instancia del adaptador y pasarle la lista de datos
        //val adaptador = AdaptadorTablaCliente(this, DatosTablaClientes)
        val adaptador = AdaptadorTablaCliente(this, DatosTablaClientes)

        // Configurar RecyclerView con un LinearLayoutManager (vertical)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Asignar el adaptador al RecyclerView
        recyclerView.adapter = adaptador

    }

    private fun obtenerDatosTabla(): List<DatosTablaClientes> {
        // Aquí puedes retornar una lista de datos para tu tabla
        // Por ejemplo, podrías consultar una base de datos o crear datos de muestra manualmente
        return listOf(
            DatosTablaClientes("Pedro Sanchez","32.456.567","✏","❌"),
            DatosTablaClientes("Julieta Ibañez","45.345.234","✏\uFE0F","❌"),
            // Agrega más datos según sea necesario
        )
    }
    }