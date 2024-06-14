package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: SqlHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SqlHelper(this)
        initializeDatabase(dbHelper)

        val buttonLogin = findViewById<Button>(R.id.button_login)
        val editTextUsername = findViewById<EditText>(R.id.name)
        val editTextPassword = findViewById<EditText>(R.id.password)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                val userType = dbHelper.getUserType(username, password)
                if (userType != null) {
                    if (userType == "admin") {
                        val intent = Intent(this, HomeAdmin::class.java)
                        startActivity(intent)
                    } else {
                        // Lógica para navegar a la pantalla de cliente cuando esté disponible
                    }
                } else {
                    Log.d("MainActivity", "Usuario o contraseña incorrectos.")
                }
            } else {
                Log.d("MainActivity", "Por favor, ingresa ambos campos.")
            }
        }
    }

    private fun initializeDatabase(dbHelper: SqlHelper) {
        // Insertar un administrador
        dbHelper.insertPersona("Admin", "ApellidoAdmin", "DireccionAdmin", "12345678A", "1980-01-01", 1, 1, 1, "adminUser", "adminPassword")

        // Insertar un cliente
        dbHelper.insertPersona("Cliente", "ApellidoCliente", "DireccionCliente", "87654321C", "1995-05-15", 1, 1, 0, "clienteUser", "clientePassword")

        // Insertar un cliente que no sea socio
        dbHelper.insertPersona("ClienteNoSocio", "ApellidoClienteNoSocio", "DireccionClienteNoSocio", "87654321N", "1998-07-22", 1, 0, 0, "clienteNoSocioUser", "clienteNoSocioPassword")

        // Insertar cuotas para los clientes
        dbHelper.insertCuota(2, "Enero", "mensual", "2024-01-05", "2024-01", 1, 100.00, "2024-01-31")
        dbHelper.insertCuota(2, "Febrero", "mensual", "2024-02-05", "2024-02", 2, 100.00, "2024-02-28")
        dbHelper.insertCuota(3, "Enero", "mensual", "2024-01-10", "2024-01", 1, 150.00, "2024-01-31")
        dbHelper.insertCuota(3, "Febrero", "mensual", "2024-02-10", "2024-02", 2, 150.00, "2024-02-28")
    }


    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
