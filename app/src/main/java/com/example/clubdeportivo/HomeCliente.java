/*package com.example.clubdeportivo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

class HomeCliente : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_cliente)

        // Aquí puedes establecer los valores de username y dirección, por ejemplo:
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvDireccion = findViewById<TextView>(R.id.tvDireccion)

        // Supongamos que tienes una función que obtiene los datos del usuario actual
        val currentUser = getCurrentUser()
        tvUsername.text = currentUser.username
        tvDireccion.text = currentUser.direccion

        // Configurar los botones para navegar a las respectivas actividades
        findViewById<Button>(R.id.btnCredenciales).setOnClickListener {
        // Navega a la actividad de credenciales
        val intent = Intent(this, CredencialesActivity::class.java)
        startActivity(intent)
        }

        findViewById<Button>(R.id.btnHistorialPagos).setOnClickListener {
        // Navega a la actividad de historial de pagos
        val intent = Intent(this, HistorialPagosActivity::class.java)
        startActivity(intent)
        }
        }

// Simulación de una función que obtiene los datos del usuario actual
private fun getCurrentUser(): User {
        return User("username", "Dirección")
        }

        data class User(val username: String, val direccion: String)
}
*/