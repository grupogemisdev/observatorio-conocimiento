package utn.observatorioconocimiento.service

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import utn.observatorioconocimiento.controller.dto.JobLaunchRequest

@Service
class BatchJobService(
    private val applicationContext: ApplicationContext,
    private val jobLauncher: JobLauncher,
) {

    fun runJob(jobLaunchRequest: JobLaunchRequest): ExitStatus {
        val jobBean = applicationContext.getBean(jobLaunchRequest.jobName, Job::class.java)
        val params = jobBean.jobParametersIncrementer?.getNext(
            JobParametersBuilder()
                .addString("filePath", jobLaunchRequest.filePath)
                .toJobParameters(
                )
        )
        val exitStatus = jobLauncher.run(jobBean, params!!).exitStatus
        return exitStatus
    }

}

