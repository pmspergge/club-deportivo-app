package com.example.clubdeportivo

import AdaptadorTablaCliente
import DatosTablaClientes
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Ver_Clientes : AppCompatActivity(), AdaptadorTablaCliente.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_clientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myButton = findViewById<ImageButton>(R.id.button_volver2)
        myButton.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val myButton2 = findViewById<Button>(R.id.button4)
        myButton2.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.tablaClientes)
        val datosTablaClientes = obtenerDatosTabla()
        val adaptador = AdaptadorTablaCliente(this, datosTablaClientes, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adaptador
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(this, Editar_Cliente::class.java)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        showDeleteConfirmationDialog(position)
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.modal_borrar_cliente, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val btnDelete: Button = dialogView.findViewById(R.id.btnDelete)

        btnCancel.setOnClickListener {
            dialogBuilder.dismiss()
        }

        btnDelete.setOnClickListener {
            deleteClient(position)
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }

    private fun deleteClient(position: Int) {
        val dbHelper = SqlHelper(this)
        val datosTablaClientes = obtenerDatosTabla()
        val clienteAEliminar = datosTablaClientes[position]

        dbHelper.deletePersona(clienteAEliminar.dni)

        val nuevosDatosTablaClientes = obtenerDatosTabla()
        val adaptador = findViewById<RecyclerView>(R.id.tablaClientes).adapter as AdaptadorTablaCliente
        adaptador.updateData(nuevosDatosTablaClientes)
    }

    private fun obtenerDatosTabla(): List<DatosTablaClientes> {
        val dbHelper = SqlHelper(this)
        val personas: List<Persona> = dbHelper.getAllPersonas()
        val datos = mutableListOf<DatosTablaClientes>()
        for (persona in personas) {
            Log.d("", persona.id.toString() + "---" + persona.nombre)
            datos.add(DatosTablaClientes(persona.dni, persona.nombre, persona.socio.toString(), "✏\uFE0F", "❌"))
        }
        return datos
    }
}
