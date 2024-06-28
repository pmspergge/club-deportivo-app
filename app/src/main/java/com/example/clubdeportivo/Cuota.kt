package com.example.clubdeportivo

class Cuota(
    val id: Int,
    val personaId: Any, // Puede ser Int o String según el contexto
    val mesDia: String,
    val tipo: String,
    val fechaPago: String,
    val periodo: String,
    val numeroCuota: Int,
    val monto: Double,
    val fechaVencimiento: String,
    val personaNombre: String
) {
    constructor(
        id: Int,
        personaId: Int,
        mesDia: String,
        tipo: String,
        fechaPago: String,
        periodo: String,
        numeroCuota: Int,
        monto: Double,
        fechaVencimiento: String,
        personaNombre: String
    ) : this(id, personaId.toString(), mesDia, tipo, fechaPago, periodo, numeroCuota, monto, fechaVencimiento, personaNombre)
}
