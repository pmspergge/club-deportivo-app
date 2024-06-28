package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Editar_Cliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_cliente)

        // Ajustar los insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recibir el Intent
        val intent = intent
        val data:String = intent.getStringExtra("Dni").toString()

        // Aquí puedes hacer algo con los datos recibidos, por ejemplo, mostrarlos en un TextView
        // val textViewData = findViewById<TextView>(R.id.textViewData)
        // textViewData.text = data
        val dbHelper = SqlHelper(this);
        val persona: Persona? = dbHelper.getOnePersona(data);

        if (persona != null) {
            findViewById<TextInputEditText>(R.id.textInputEditText).setText(persona.nombre)
            findViewById<TextInputEditText>(R.id.textInputEditText2).setText(persona.apellido)
            findViewById<TextInputEditText>(R.id.textInputEditText3).setText(persona.direccion)
            findViewById<TextInputEditText>(R.id.textInputEditText4).setText(persona.dni)
            findViewById<TextInputEditText>(R.id.textInputEditText4).isFocusable = false
            findViewById<TextInputEditText>(R.id.textInputEditText4).isFocusableInTouchMode = false
            findViewById<TextInputEditText>(R.id.textInputEditText4).isClickable = false
            findViewById<TextInputEditText>(R.id.textInputEditText5).setText(persona.fechaNacimiento)
            findViewById<CheckBox>(R.id.checkBox).isChecked = persona.aptoFisico == 1
            findViewById<CheckBox>(R.id.checkBox2).isChecked = persona.socio == 1
        }



        // Configurar los botones
        val myButton2 = findViewById<ImageButton>(R.id.button_registrar_clie_volver)
        myButton2.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val myButton4 = findViewById<Button>(R.id.button3)
        myButton4.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.button3)
        registerButton.setOnClickListener {
            val dbHelper = SqlHelper(this)
            val nombre = findViewById<TextInputEditText>(R.id.textInputEditText).text.toString()
            val apellido = findViewById<TextInputEditText>(R.id.textInputEditText2).text.toString()
            val direccion = findViewById<TextInputEditText>(R.id.textInputEditText3).text.toString()
            val dni = data
            val fechaNacimiento = findViewById<TextInputEditText>(R.id.textInputEditText5).text.toString()
            val aptoFisico = if (findViewById<CheckBox>(R.id.checkBox).isChecked) 1 else 0
            val socio = if (findViewById<CheckBox>(R.id.checkBox2).isChecked) 1 else 0

            dbHelper.updatePersona(nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio);
            val intent = Intent(this, Ver_Clientes::class.java)
            startActivity(intent)
        }
    }
}