package com.example.clubdeportivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Pagar_cuota_socio : AppCompatActivity() {
    private lateinit var editTextDNI: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextMonto: EditText
    private lateinit var btnBuscarPersona: Button
    private lateinit var btnPagar: Button
    private lateinit var sqlHelper: SqlHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagar_cuota_socio)

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

            // Obtener datos de la persona por DNI utilizando SqlHelper
            val persona = sqlHelper.getOnePersona(dni)

            // Mostrar los datos obtenidos en los EditText correspondientes si se encontró un resultado
            if (persona != null) {
                editTextNombre.setText(persona.nombre)
                editTextApellido.setText(persona.apellido)
            } else {
                // Manejar el caso en que no se encuentre la persona en la base de datos
                editTextNombre.setText("")
                editTextApellido.setText("")
            }

            // Limpiar el campo del monto
            editTextMonto.setText("")
        }

        // Listener para el botón Pagar
        btnPagar.setOnClickListener {
            // Obtener el monto ingresado por el usuario
            val monto = editTextMonto.text.toString().toDoubleOrNull()

            if (monto != null) {
                // Obtener los datos de los EditText
                val dni = editTextDNI.text.toString()
                val nombre = editTextNombre.text.toString()
                val apellido = editTextApellido.text.toString()

                // Crear Intent para abrir la actividad DetallePago y enviar datos
                val intent = Intent(this, DetallePago::class.java).apply {
                    putExtra("dni", dni)
                    putExtra("nombre", nombre)
                    putExtra("apellido", apellido)
                    putExtra("monto", monto)
                }
                startActivity(intent)

                // Limpiar campos después de realizar el pago (opcional)
                editTextDNI.setText("")
                editTextNombre.setText("")
                editTextApellido.setText("")
                editTextMonto.setText("")
            } else {
                // Manejar el caso en que el monto ingresado no sea válido
                // Puedes mostrar un mensaje de error o realizar alguna acción adicional
            }
        }
    }

    override fun onDestroy() {
        sqlHelper.close() // Cerrar la conexión con la base de datos cuando la actividad se destruye
        super.onDestroy()
    }
}
