package com.example.clubdeportivo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetallePago : AppCompatActivity() {

    private lateinit var txtNombre: TextView
    private lateinit var txtApellido: TextView
    private lateinit var txtDni: TextView
    private lateinit var txtMonto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pago)

        // Obtener datos enviados desde la actividad PagarCuotaSocio
        val dni = intent.getStringExtra("dni")
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val monto = intent.getDoubleExtra("monto", 0.0)

        // Inicializar las vistas TextView
        txtNombre = findViewById(R.id.txtNombre)
        txtApellido = findViewById(R.id.txtApellido)
        txtDni = findViewById(R.id.txtDni)
        txtMonto = findViewById(R.id.txtMonto)

        // Mostrar los datos en las vistas correspondientes
        txtNombre.text = nombre
        txtApellido.text = apellido
        txtDni.text = dni
        txtMonto.text = String.format("%.2f", monto) // Formatear el monto con dos decimales
    }
}
