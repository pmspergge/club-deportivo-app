package com.example.clubdeportivo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

}
