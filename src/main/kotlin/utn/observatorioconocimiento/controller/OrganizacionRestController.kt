package utn.observatorioconocimiento.controller


import utn.observatorioconocimiento.dao.Organizacion
import utn.observatorioconocimiento.dao.repository.OrganizacionRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(originPatterns = ["http://localhost:[*]", "https://*.awsapprunner.com"])
class OrganizacionRestController(
    private val organizacionRepository: OrganizacionRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(OrganizacionRestController::class.java)

    @GetMapping("/api/organizaciones")
    fun getAll(): List<Organizacion> {
        logger.info("GET /organizaciones")
        return organizacionRepository.findAll()
    }
    
}
