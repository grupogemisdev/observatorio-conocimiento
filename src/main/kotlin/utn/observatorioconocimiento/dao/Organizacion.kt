package utn.observatorioconocimiento.dao

import javax.persistence.*

@Entity
@Table(name = "organizacion")
data class Organizacion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val nombre: String,
    val sectorOrganizacional: String?,
    @Embedded
    val ubicacion: Ubicacion,
    @Embedded
    val medicion: Medicion?

)
