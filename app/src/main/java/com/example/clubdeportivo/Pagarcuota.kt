package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Pagarcuota : AppCompatActivity() {
    private lateinit var editTextDNI: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextMonto: EditText
    private lateinit var btnBuscarPersona: Button
    private lateinit var btnPagar: Button
    private lateinit var sqlHelper: SqlHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagarcuota)

        // Inicialización de SqlHelper
        sqlHelper = SqlHelper(this)

        // Inicialización de vistas
        editTextDNI = findViewById(R.id.editTextDNI)
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextMonto = findViewById(R.id.editTextMonto)
        btnBuscarPersona = findViewById(R.id.btnBuscarPersona)
        btnPagar = findViewById(R.id.btnPagar)

        // Listener para el botón Buscar Persona
        btnBuscarPersona.setOnClickListener {
            val dni = editTextDNI.text.toString()

            val persona = sqlHelper.getOnePersona(dni)
            if (persona != null) {
                editTextNombre.setText(persona.nombre)
                editTextApellido.setText(persona.apellido)

                val montoCuota = sqlHelper.getMontoByDNI(dni)
                // Convertir el monto a String antes de establecerlo en EditText
                val montoStr = montoCuota.toString()
                editTextMonto.setText(montoStr)
                if (montoCuota == null){
                    showInfoDialog("No tiene cuotas a pagar")
                    editTextMonto.setText("")
                }
                } else {
                editTextNombre.setText("")
                editTextApellido.setText("")
                editTextMonto.setText("")
                Toast.makeText(this, R.string.persona_no_encontrada, Toast.LENGTH_SHORT).show()
            }
        }

        btnPagar.setOnClickListener {
            val dni = editTextDNI.text.toString()
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val montoStr = editTextMonto.text.toString()
            val monto = montoStr.toDoubleOrNull()

            if (monto != null) {
                sqlHelper.actualizarCuotaPagada(dni, monto)

                val intent = Intent(this, DetallePago::class.java).apply {
                    putExtra("dni", dni)
                    putExtra("nombre", nombre)
                    putExtra("apellido", apellido)
                    putExtra("monto", monto)
                }
                startActivity(intent)

                editTextDNI.setText("")
                editTextNombre.setText("")
                editTextApellido.setText("")
                editTextMonto.setText("")
            } else {
                Toast.makeText(this, R.string.monto_invalido, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInfoDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        sqlHelper.close() // Cerrar la conexión con la base de datos cuando la actividad se destruye
        super.onDestroy()
    }
}