package utn.observatorioconocimiento.dao

import jakarta.persistence.*


@Entity
@Table(name = "area")
data class Area(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val nombre: String,
    val nivel: Int?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "superarea_id")
    val subareas: Set<Area>? = null
)