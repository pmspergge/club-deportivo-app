package com.example.clubdeportivo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeCliente : AppCompatActivity() {

    private lateinit var dbHelper: SqlHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_cliente)

        dbHelper = SqlHelper(this)

        val (username, _) = SharedPreferencesManager.getUserData(this)
        val user = dbHelper.getUserDetails(username)

        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvDireccion = findViewById<TextView>(R.id.tvDireccion)

        tvUsername.text = user?.usuario ?: "Usuario no encontrado"
        tvDireccion.text = user?.direccion ?: "Dirección no encontrada"
    }
}