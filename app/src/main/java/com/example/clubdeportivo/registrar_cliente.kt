package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class registrar_cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_cliente)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageButton>(R.id.button_registrar_clie_volver)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeAdmin::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.button)
        registerButton.setOnClickListener {
            val dbHelper = SqlHelper(this)
            val db = dbHelper.writableDatabase
            val nombre = findViewById<TextInputEditText>(R.id.textInputEditTextNombre).text.toString()
            val apellido = findViewById<TextInputEditText>(R.id.textInputEditTextApellido).text.toString()
            val direccion = findViewById<TextInputEditText>(R.id.textInputEditTextDireccion).text.toString()
            val dni = findViewById<TextInputEditText>(R.id.textInputEditTextDNI).text.toString()
            val fechaNacimiento = findViewById<TextInputEditText>(R.id.textInputEditTextFechaNacimiento).text.toString()
            val aptoFisico = if (findViewById<CheckBox>(R.id.checkBox).isChecked) 1 else 0
            val socio = if (findViewById<CheckBox>(R.id.checkBox2).isChecked) 1 else 0

            dbHelper.insertPersona(db, nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio, 0, "defaultUser", "defaultPassword")
            db.close()

            val intent = Intent(this, Ver_Clientes::class.java)
            startActivity(intent)
        }
    }
}
