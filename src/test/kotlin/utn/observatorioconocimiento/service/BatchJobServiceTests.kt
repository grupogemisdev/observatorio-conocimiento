package utn.observatorioconocimiento.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import utn.observatorioconocimiento.controller.dto.JobLaunchRequest
import utn.observatorioconocimiento.dao.repository.OrganizacionRepository

@SpringBootTest
class BatchJobServiceTests {

	@Autowired
	private lateinit var jobService: BatchJobService
	@Autowired
	private lateinit var organizacionRepository: OrganizacionRepository


	@Test
	fun jobService_runJob_completed() {
		val exitStatus = jobService.runJob(JobLaunchRequest("organizacionesJob", "src/test/resources/organizaciones-test.json"))
		assert(exitStatus.exitCode == "COMPLETED")
		val organizacionesDB = organizacionRepository.findAll()
		assert(organizacionesDB.size == 2)
	}

}
