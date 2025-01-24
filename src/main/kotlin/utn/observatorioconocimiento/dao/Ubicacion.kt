package utn.observatorioconocimiento.dao

import jakarta.persistence.Embeddable

@Embeddable
data class Ubicacion(
    val latitud: Double,
    val longitud: Double
)