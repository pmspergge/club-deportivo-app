package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class registrar_cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val myButton = findViewById<ImageButton>(R.id.button_registrar_clie_volver)
        myButton.setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
        }
        val myButton2 = findViewById<Button>(R.id.button)
        myButton2.setOnClickListener {
            val dbHelper = SqlHelper(this);
            dbHelper.insertPersona("John",
                "Doe",
                "123 Main St",
                "123456789",
                "19900101",
                1,
                1,
                0,
                "johndoe",
                "password123");
        }




    }
}