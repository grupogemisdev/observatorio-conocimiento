package utn.observatorioconocimiento.dao

import javax.persistence.*


@Embeddable
data class Medicion(
    @Column(name = "medicion_nivel")
    val nivel: Int?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "organizacion_id")
    val areas: Set<Area> = emptySet()
)
