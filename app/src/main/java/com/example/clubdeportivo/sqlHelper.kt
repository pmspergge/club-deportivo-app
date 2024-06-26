package com.example.clubdeportivo
import android.database.Cursor
import android.annotation.SuppressLint
import android.util.Log
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SqlHelper(context: Context) : SQLiteOpenHelper(context, "clubDeportivo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createPersonaTable = """
            CREATE TABLE persona (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                apellido VARCHAR(50),
                direccion VARCHAR(100),
                dni VARCHAR(20),
                fecha_nacimiento DATE,
                apto_fisico TINYINT(1),
                socio TINYINT(1),
                admin TINYINT(1),
                usuario VARCHAR(50),
                contrasena VARCHAR(255)
            );
        """
        db.execSQL(createPersonaTable)

        val createCuotaTable = """
            CREATE TABLE cuota (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                persona_id INTEGER,
                mes_dia VARCHAR(10),
                tipo VARCHAR(10),
                fecha_pago DATE,
                periodo VARCHAR(50),
                numero_cuota INTEGER,
                monto DECIMAL(10, 2),
                fecha_vencimiento DATE,
                FOREIGN KEY (persona_id) REFERENCES persona(id)
            );
        """
        db.execSQL(createCuotaTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS persona")
        db?.execSQL("DROP TABLE IF EXISTS cuota")
        if (db != null) {
            onCreate(db)
        }
    }

    fun insertPersona(name: String, surname: String, address: String, dni: String, birthDate: String, aptoFisico: Int, socio: Int, admin: Int, username: String, password: String) {
        val values = ContentValues()
        values.put("nombre", name)
        values.put("apellido", surname)
        values.put("direccion", address)
        values.put("dni", dni)
        values.put("fecha_nacimiento", birthDate)
        values.put("apto_fisico", aptoFisico)
        values.put("socio", socio)
        values.put("admin", admin)
        values.put("usuario", username)
        values.put("contrasena", password)

        val db = this.writableDatabase
        db.insert("persona", null, values)
        db.close()
    }

    fun truncatePersonaTable() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Persona")
        db.close()
    }

    @SuppressLint("Range")
    fun retrieveCuotasByFechaVencimiento(): List<Cuota> {
        val cuotas = mutableListOf<Cuota>()
        val db = readableDatabase

        // Get today's date in yyyy-MM-dd format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date())

        // Query cuotas where fecha_vencimiento equals today's date
        val cursor: Cursor? = db.rawQuery("SELECT * FROM cuota INNER JOIN Persona ON cuota.persona_id = Persona.id WHERE fecha_vencimiento = ?", arrayOf(todayDate))

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val personaId = it.getInt(it.getColumnIndex("persona_id"))
                val mesDia = it.getString(it.getColumnIndex("mes_dia"))
                val tipo = it.getString(it.getColumnIndex("tipo"))
                val fechaPago = it.getString(it.getColumnIndex("fecha_pago"))
                val periodo = it.getString(it.getColumnIndex("periodo"))
                val numeroCuota = it.getInt(it.getColumnIndex("numero_cuota"))
                val monto = it.getDouble(it.getColumnIndex("monto"))
                val fechaVencimiento = it.getString(it.getColumnIndex("fecha_vencimiento"))
                val personaNombre = it.getString(it.getColumnIndex("nombre"))

                val cuota = Cuota(id, personaId, mesDia, tipo, fechaPago, periodo, numeroCuota, monto, fechaVencimiento,personaNombre)
                cuotas.add(cuota)
            }
        }

        db.close()
        return cuotas
    }

    fun getAllPersonas(): List<Persona> {
        val personas = mutableListOf<Persona>()
        val db = this.readableDatabase
        val query = "SELECT * FROM persona"
        val cursor = db.rawQuery(query, null)

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow("id"))
                    val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                    val apellido = it.getString(it.getColumnIndexOrThrow("apellido"))
                    val direccion = it.getString(it.getColumnIndexOrThrow("direccion"))
                    val dni = it.getString(it.getColumnIndexOrThrow("dni"))
                    val fechaNacimiento = it.getString(it.getColumnIndexOrThrow("fecha_nacimiento"))
                    val aptoFisico = it.getInt(it.getColumnIndexOrThrow("apto_fisico"))
                    val socio = it.getInt(it.getColumnIndexOrThrow("socio"))
                    val admin = it.getInt(it.getColumnIndexOrThrow("admin"))
                    val usuario = it.getString(it.getColumnIndexOrThrow("usuario"))
                    val contrasena = it.getString(it.getColumnIndexOrThrow("contrasena"))

                    val persona = Persona(
                        id, nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio, admin, usuario, contrasena
                    )
                    personas.add(persona)
                } while (it.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return personas
    }
    fun insertCuota(personaId: Int, mesDia: String, tipo: String, fechaPago: String, periodo: String, numeroCuota: Int, monto: Double, fechaVencimiento: String) {
        val values = ContentValues()
        values.put("persona_id", personaId)
        values.put("mes_dia", mesDia)
        values.put("tipo", tipo)
        values.put("fecha_pago", fechaPago)
        values.put("periodo", periodo)
        values.put("numero_cuota", numeroCuota)
        values.put("monto", monto)
        values.put("fecha_vencimiento", fechaVencimiento)

        val db = this.writableDatabase
        db.insert("cuota", null, values)
        db.close()
    }

    fun deletePersona(dni: String) {
        val db = this.writableDatabase
        db.delete("persona", "dni=?", arrayOf(dni))
        db.close()
    }


    @SuppressLint("Range")
    fun getAllUsers(): List<Map<String, String>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM persona", null)
        val users = mutableListOf<Map<String, String>>()
        if (cursor.moveToFirst()) {
            do {
                val user = mapOf(
                    "id" to cursor.getString(cursor.getColumnIndex("id")),
                    "nombre" to cursor.getString(cursor.getColumnIndex("nombre")),
                    "apellido" to cursor.getString(cursor.getColumnIndex("apellido")),
                    "usuario" to cursor.getString(cursor.getColumnIndex("usuario"))
                )
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }

    fun getUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM persona WHERE usuario = ? AND contrasena = ?", arrayOf(username, password))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    @SuppressLint("Range", "Recycle")
    fun getUserType(username: String, password: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT admin FROM persona WHERE usuario = ? AND contrasena = ?", arrayOf(username, password))
        return if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex("admin")) == 1) "admin" else "cliente"
        } else {
            null
        }
    }


    @SuppressLint("Range")
    fun printPersonaTable() {
        val db = this.readableDatabase
        val query = "SELECT * FROM Persona"
        val cursor: Cursor? = db.rawQuery(query, null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex("id"))
                    val nombre = it.getString(it.getColumnIndex("nombre"))
                    val apellido = it.getString(it.getColumnIndex("apellido"))
                    val direccion = it.getString(it.getColumnIndex("direccion"))
                    val dni = it.getString(it.getColumnIndex("dni"))
                    val fechaNacimiento = it.getString(it.getColumnIndex("fecha_nacimiento"))
                    val aptoFisico = it.getInt(it.getColumnIndex("apto_fisico"))
                    val socio = it.getInt(it.getColumnIndex("socio"))
                    val admin = it.getInt(it.getColumnIndex("admin"))
                    val usuario = it.getString(it.getColumnIndex("usuario"))
                    val contrasena = it.getString(it.getColumnIndex("contrasena"))

                    Log.d("Persona", "ID: $id, Nombre: $nombre, Apellido: $apellido, Direccion: $direccion, " +
                                "DNI: $dni, Fecha Nacimiento: $fechaNacimiento, Apto Físico: $aptoFisico, " +
                            "Socio: $socio, Admin: $admin, Usuario: $usuario, Contraseña: $contrasena")
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        db.close()
    }

}
