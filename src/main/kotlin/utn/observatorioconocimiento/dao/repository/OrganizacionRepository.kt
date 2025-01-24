package utn.observatorioconocimiento.dao.repository

import utn.observatorioconocimiento.dao.Organizacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizacionRepository : JpaRepository<Organizacion, Long> {}