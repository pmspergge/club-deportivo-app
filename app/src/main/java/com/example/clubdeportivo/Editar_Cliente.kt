package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Editar_Cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myButton2 = findViewById<ImageButton>(R.id.button_registrar_clie_volver)
        myButton2.setOnClickListener {
            // Aquí es donde inicias la nueva actividad
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        val myButton4 = findViewById<Button>(R.id.button3)
        myButton4.setOnClickListener {
            // Aquí es donde inicias la nueva actividad
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

    }
}