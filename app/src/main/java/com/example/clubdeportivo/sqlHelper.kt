package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

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
                persona_dni INTEGER,
                mes_dia VARCHAR(10),
                tipo VARCHAR(10),
                fecha_pago DATE,
                periodo VARCHAR(50),
                numero_cuota INTEGER,
                monto DECIMAL(10, 2),
                fecha_vencimiento DATE,
                pagado TINYINT DEFAULT 0,
                FOREIGN KEY (persona_dni) REFERENCES persona(dni)
            );
        """
        db.execSQL(createCuotaTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS persona")
        db.execSQL("DROP TABLE IF EXISTS cuota")
        onCreate(db)
    }

    fun insertInitialData(db: SQLiteDatabase) {
        insertPersona(db, "Admin2", "Admin2", "Admin Address2", "123456782", "1970-01-02", 1, 1, 1, "adminUser", "adminPassword")
        insertPersona(db, "Admin", "Admin", "Admin Address", "12345678", "1970-01-01", 1, 1, 1, "admin", "admin123")
        insertPersona(db, "Juan", "Perez", "Calle Falsa 123", "87654321", "1980-01-01", 1, 1, 0, "juan", "juan123")
        insertPersona(db, "ClienteSocio", "Perez", "Calle Real 456", "11223344", "1990-05-15", 1, 0, 0, "clienteSocio", "cliente123")

        // Insert initial cuotas
        insertCuota(db, "12345678", "July 22", "Socio", "2024-07-21", "Mes", 1, 15000.0, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        insertCuota(db, "11223344", "August 22", "No Socio", "2024-08-21", "Mes", 1, 25000.0, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
    }

    fun insertPersona(
        db: SQLiteDatabase,
        name: String,
        surname: String,
        address: String,
        dni: String,
        birthDate: String,
        aptoFisico: Int,
        socio: Int,
        admin: Int,
        usuario: String,
        contrasena: String
    ) {
        val values = ContentValues().apply {
            put("nombre", name)
            put("apellido", surname)
            put("direccion", address)
            put("dni", dni)
            put("fecha_nacimiento", birthDate)
            put("apto_fisico", aptoFisico)
            put("socio", socio)
            put("admin", admin)
            put("usuario", usuario)
            put("contrasena", contrasena)
        }
        db.insert("persona", null, values)
    }

    fun insertCuota(
        db: SQLiteDatabase,
        dni: String,
        mesDia: String,
        tipo: String,
        fechaPago: String,
        periodo: String,
        numeroCuota: Int,
        monto: Double,
        fechaVencimiento: String
    ) {
        val persona = getOnePersona(dni)

        if (persona != null) {
            val values = ContentValues().apply {
                put("persona_dni", dni) // Usamos el DNI de la persona
                put("mes_dia", mesDia)
                put("tipo", tipo)
                put("fecha_pago", fechaPago)
                put("periodo", periodo)
                put("numero_cuota", numeroCuota)
                put("monto", monto)
                put("fecha_vencimiento", fechaVencimiento)
            }
            db.insert("cuota", null, values)
        } else {
            Log.e("SqlHelper", "No se encontró persona con DNI: $dni")
        }
    }

    fun isPersonaTableEmpty(db: SQLiteDatabase): Boolean {
        val cursor = db.rawQuery("SELECT COUNT(*) FROM persona", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        return count == 0
    }
    @SuppressLint("Range")
    fun retrieveCuotasByFechaVencimiento(): List<Cuota> {
        val cuotas = mutableListOf<Cuota>()
        val db = readableDatabase
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = dateFormat.format(Date())
        val cursor: Cursor? = db.rawQuery(
            "SELECT * FROM cuota INNER JOIN persona ON cuota.persona_dni = persona.dni WHERE fecha_vencimiento = ?",
            arrayOf(todayDate)
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val personaDni = it.getString(it.getColumnIndex("dni"))
                val mesDia = it.getString(it.getColumnIndex("mes_dia"))
                val tipo = it.getString(it.getColumnIndex("tipo"))
                val fechaPago = it.getString(it.getColumnIndex("fecha_pago"))
                val periodo = it.getString(it.getColumnIndex("periodo"))
                val numeroCuota = it.getInt(it.getColumnIndex("numero_cuota")) // Asegúrate de obtener un Int aquí
                val monto = it.getDouble(it.getColumnIndex("monto"))
                val fechaVencimiento = it.getString(it.getColumnIndex("fecha_vencimiento"))
                val personaNombre = it.getString(it.getColumnIndex("nombre"))

                val cuota = Cuota(id, personaDni, mesDia, tipo, fechaPago, periodo, numeroCuota, monto, fechaVencimiento, personaNombre)
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

                    val persona = Persona(id, nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio, admin, usuario, contrasena)
                    personas.add(persona)
                } while (it.moveToNext())
            }
        }

        cursor.close()
        db.close()
        return personas
    }

    fun deletePersona(dni: String) {
        val db = this.writableDatabase
        db.delete("persona", "dni=?", arrayOf(dni))
        db.close()
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
    fun getUserDetails(username: String?): Persona? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM persona WHERE usuario = ?", arrayOf(username))

        var user: Persona? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
            val apellido = cursor.getString(cursor.getColumnIndex("apellido"))
            val direccion = cursor.getString(cursor.getColumnIndex("direccion"))
            val dni = cursor.getString(cursor.getColumnIndex("dni"))
            val fechaNacimiento = cursor.getString(cursor.getColumnIndex("fecha_nacimiento"))
            val aptoFisico = cursor.getInt(cursor.getColumnIndex("apto_fisico"))
            val socio = cursor.getInt(cursor.getColumnIndex("socio"))
            val admin = cursor.getInt(cursor.getColumnIndex("admin"))
            val usuario = cursor.getString(cursor.getColumnIndex("usuario"))
            val contrasena = cursor.getString(cursor.getColumnIndex("contrasena"))

            user = Persona(id, nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio, admin, usuario, contrasena)
        }

        cursor.close()
        db.close()
        return user
    }
    fun updatePersona(name: String, surname: String, address: String, dni: String, birthDate: String, aptoFisico: Int, socio: Int) {
        val values = ContentValues().apply {
            put("nombre", name)
            put("apellido", surname)
            put("direccion", address)
            put("fecha_nacimiento", birthDate)
            put("apto_fisico", aptoFisico)
            put("socio", socio)
        }

        val db = this.writableDatabase

        // Update con criterio de selección
        val selection = "dni = ?"
        val selectionArgs = arrayOf(dni)

        db.update("persona", values, selection, selectionArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun getOnePersona(dni: String): Persona? {
        val db = this.readableDatabase
        var persona: Persona? = null

        try {
            val cursor = db.rawQuery("SELECT * FROM persona WHERE dni = ?", arrayOf(dni))

            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val apellido = cursor.getString(cursor.getColumnIndex("apellido"))
                val direccion = cursor.getString(cursor.getColumnIndex("direccion"))
                val fechaNacimiento = cursor.getString(cursor.getColumnIndex("fecha_nacimiento"))
                val aptoFisico = cursor.getInt(cursor.getColumnIndex("apto_fisico"))
                val socio = cursor.getInt(cursor.getColumnIndex("socio"))
                val admin = cursor.getInt(cursor.getColumnIndex("admin"))
                val usuario = cursor.getString(cursor.getColumnIndex("usuario"))
                val contrasena = cursor.getString(cursor.getColumnIndex("contrasena"))

                persona = Persona(id, nombre, apellido, direccion, dni, fechaNacimiento, aptoFisico, socio, admin, usuario, contrasena)
            }

            cursor.close()
        } catch (e: Exception) {
            Log.e("Pagarcuota", "Error al obtener persona por DNI: $dni", e)
        } finally {
            db.close()
        }

        return persona
    }
    @SuppressLint("Range")
    fun getMontoByDNI(dni: String): Double? {
        var montoCuota: Double? = null
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT monto FROM cuota WHERE persona_dni = ? AND pagado = 0 ORDER BY fecha_pago DESC LIMIT 1",
            arrayOf(dni)
        )

        if (cursor.moveToFirst()) {
            montoCuota = cursor.getDouble(cursor.getColumnIndex("monto"))
        }

        cursor.close()
        db.close()
        return montoCuota
    }

    @SuppressLint("Range")
    fun actualizarCuotaPagada(dni: String, monto: Double) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("pagado", 1)

        // Ajuste en la consulta para buscar por persona_dni (cadena) y monto
        val whereClause = "persona_dni = ? AND monto = ? AND pagado = 0"
        val whereArgs = arrayOf(dni, monto.toString())

        db.update("cuota", contentValues, whereClause, whereArgs)
        db.close()
    }
}