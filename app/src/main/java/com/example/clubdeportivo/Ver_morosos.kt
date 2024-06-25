package com.example.clubdeportivo

import AdaptadorTabla
import DatosTabla
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

class Ver_Morosos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_morosos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myButton = findViewById<ImageButton>(R.id.button_volver)
        myButton.setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
        }

        val myButton2 = findViewById<Button>(R.id.button5)
        myButton2.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.tablaMorosos)

        val datosTabla = obtenerDatosTabla()

        val adaptador = AdaptadorTabla(this, datosTabla)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adaptador
    }

    private fun obtenerDatosTabla(): List<DatosTabla> {
        val dbHelper = SqlHelper(this)

        // Example usage to insert a Cuota
        val db = dbHelper.writableDatabase
        dbHelper.insertCuota(db, 1, "June 22", "Socio", "2024-06-21", "Mes", 1, 100.0, "2024-06-22")
        dbHelper.insertCuota(db, 2, "June 22", "Socio", "2024-06-21", "Mes", 1, 1000.0, "2024-06-22")

        val cuotas: List<Cuota> = dbHelper.retrieveCuotasByFechaVencimiento()
        val datos = mutableListOf<DatosTabla>()
        for (cuota in cuotas) {
            datos.add(DatosTabla(cuota.personaNombre, cuota.monto.toString()))
        }
        return datos
    }
}
