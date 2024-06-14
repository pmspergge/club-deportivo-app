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
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }



        val myButton2 = findViewById<Button>(R.id.button4)
        myButton2.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }



        val myButton3: RecyclerView = findViewById(R.id.tablaClientes)
        myButton3.setOnClickListener {
            val intent = Intent(this, Editar_Cliente::class.java)
            startActivity(intent)
        }


        val recyclerView: RecyclerView = findViewById(R.id.tablaClientes)

        val DatosTablaClientes = obtenerDatosTabla()

        val adaptador = AdaptadorTablaCliente(this, DatosTablaClientes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adaptador

    }

    private fun obtenerDatosTabla(): List<DatosTablaClientes> {
        return listOf(
            DatosTablaClientes("Pedro Sanchez","32.456.567","✏","❌"),
            DatosTablaClientes("Julieta Ibañez","45.345.234","✏\uFE0F","❌"),
        )
    }
    }
