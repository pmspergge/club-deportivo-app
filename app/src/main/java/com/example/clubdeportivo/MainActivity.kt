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
        dbHelper.printPersonaTable()
        val db = dbHelper.writableDatabase
        dbHelper.insertInitialData(db)
        val buttonLogin = findViewById<Button>(R.id.button_login)
        val editTextUsername = findViewById<EditText>(R.id.name)
        val editTextPassword = findViewById<EditText>(R.id.password)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {
                val userType = dbHelper.getUserType(username, password)
                Log.d("MainActivity", "Login attempt: username=$username, userType=$userType")

                if (userType != null) {
                    if (userType != null) {
                        SharedPreferencesManager.saveUserData(username, userType, this)
                    }
                    if (userType == "admin") {
                        Log.d("MainActivity", "Navigating to HomeAdmin")
                        val intent = Intent(this, HomeAdmin::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("MainActivity", "Navigating to HomeCliente")
                        val intent = Intent(this, HomeCliente::class.java)
                        intent.putExtra("USERNAME", username)
                        startActivity(intent)
                    }
                } else {
                    Log.d("MainActivity", "Usuario o contraseña incorrectos.")
                }
            } else {
                Log.d("MainActivity", "Por favor, ingresa ambos campos.")
            }
        }
    }
}
