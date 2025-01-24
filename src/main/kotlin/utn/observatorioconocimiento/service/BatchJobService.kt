package utn.observatorioconocimiento.service

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.ApplicationContext
import org.springframework.core.task.SyncTaskExecutor
import org.springframework.stereotype.Service
import utn.observatorioconocimiento.controller.dto.JobLaunchRequest

@Service
class BatchJobService(
    private val applicationContext: ApplicationContext,
    val jobRepository: JobRepository
) {

    fun runJob(jobLaunchRequest: JobLaunchRequest): ExitStatus {
        val jobBean = applicationContext.getBean(jobLaunchRequest.jobName, Job::class.java)
        val params = jobBean.jobParametersIncrementer?.getNext(
            JobParametersBuilder()
                .addString("filePath", jobLaunchRequest.filePath)
                .toJobParameters(
                )
        )!!
        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setJobRepository(jobRepository)
        jobLauncher.setTaskExecutor(SyncTaskExecutor())
        val exitStatus = jobLauncher.run(jobBean, params).exitStatus
        return exitStatus
    }

}

