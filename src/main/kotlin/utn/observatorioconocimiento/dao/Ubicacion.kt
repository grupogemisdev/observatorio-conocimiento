package utn.observatorioconocimiento.dao

import javax.persistence.Embeddable

@Embeddable
data class Ubicacion(
    val latitud: Double,
    val longitud: Double
)