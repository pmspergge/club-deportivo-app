package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val myButton = findViewById<Button>(R.id.button_cuotas_que_vencen_hoy)
        myButton.setOnClickListener {
            val intent = Intent(this, Ver_morosos::class.java)
            startActivity(intent)
        }
        val myButton2 = findViewById<Button>(R.id.button_agregar_cliente)
        myButton2.setOnClickListener {
            val intent2 = Intent(this, registrar_cliente::class.java)
            startActivity(intent2)
        }

        val myButton3 = findViewById<Button>(R.id.button_Cobrar_cuota)
        myButton3.setOnClickListener {
            val intent3 = Intent(this, Pagar_cuota_socio::class.java)
            startActivity(intent3)
        }

        val myButton4 = findViewById<Button>(R.id.button_configuracion)
        myButton4.setOnClickListener {
            val intent4 = Intent(this, Ver_Clientes::class.java)
            startActivity(intent4)
        }
    }
}
