package utn.observatorioconocimiento.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import utn.observatorioconocimiento.controller.dto.JobLaunchRequest
import utn.observatorioconocimiento.service.BatchJobService
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@CrossOrigin(originPatterns = ["http://localhost:[*]", "https://*.awsapprunner.com"])
class OrganizacionFileController(
    private val jobService: BatchJobService
) {

    private val logger: Logger = LoggerFactory.getLogger(OrganizacionFileController::class.java)

    @PostMapping("/upload/organizaciones-json")
    fun uploadJsonFile(@RequestPart("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            logger.info("Archivo de organizaciones recibido")

            val path = Paths.get("src/main/resources/organizaciones.json")
            Files.write(path, file.bytes)
            val exitStatus = jobService.runJob(JobLaunchRequest("organizacionesJob", path.toString()))

            logger.info("Archivo de organizaciones procesado: $exitStatus")
            if (exitStatus.exitCode == "COMPLETED") {
                ResponseEntity("Archivo de organizaciones procesado: $exitStatus", HttpStatus.OK)
            } else {
                ResponseEntity("Error procesando archivo de organizaciones: $exitStatus", HttpStatus.INTERNAL_SERVER_ERROR)
            }

        } catch (e: Exception) {
            logger.error("Error procesando archivo de organizaciones", e)
            ResponseEntity("Error procesando archivo de organizaciones", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}