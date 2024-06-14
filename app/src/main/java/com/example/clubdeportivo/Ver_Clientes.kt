package com.example.clubdeportivo

import androidx.activity.enableEdgeToEdge
import AdaptadorTabla
import AdaptadorTablaCliente
import DatosTabla
import DatosTablaClientes
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ver_Clientes : AppCompatActivity(), AdaptadorTablaCliente.OnItemClickListener {
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
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val myButton2 = findViewById<Button>(R.id.button4)
        myButton2.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.tablaClientes)
        val datosTablaClientes = obtenerDatosTabla()
        val adaptador = AdaptadorTablaCliente(this, datosTablaClientes, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptador
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(this, Editar_Cliente::class.java)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        // Handle delete click
    }

    private fun obtenerDatosTabla(): List<DatosTablaClientes> {
        return listOf(
            DatosTablaClientes("Pedro Sanchez", "Socio", "✏\uFE0F", "❌"),
            DatosTablaClientes("Julieta Ibañez", "No Socio", "✏\uFE0F", "❌")
        )
    }
}
