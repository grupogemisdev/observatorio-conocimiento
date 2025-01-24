package utn.observatorioconocimiento.batch

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.support.PassThroughItemProcessor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.InputStreamResource
import org.springframework.transaction.PlatformTransactionManager
import utn.observatorioconocimiento.dao.Organizacion
import utn.observatorioconocimiento.dao.repository.OrganizacionRepository
import java.io.File
import java.io.FileInputStream


@Configuration
@EnableBatchProcessing
class OrganizacionesBatchJobDefinition {


    @Bean
    fun organizacionesJob(organizacionesStep: Step, jobRepository: JobRepository): Job {
        // Job ->* Step -> Reader (json) -> Processor -> Writer (DB)
        return JobBuilder("organizacionesJob")
            .repository(jobRepository)
            .incrementer(RunIdIncrementer())
            .start(organizacionesStep)
            .build()
    }

    @Bean
    fun organizacionesStep(
        stepBuilderFactory: StepBuilderFactory,
        transactionManager: PlatformTransactionManager,
        organizacionesItemReader: JsonItemReader<Organizacion>,
        organizacionItemWriter: RepositoryItemWriter<Organizacion>,
        @Value("\${app.batch.chunk.size}") chunkSize: Int
    ): Step {
        return stepBuilderFactory.get("organizacionesStep")
            .transactionManager(transactionManager)
            .chunk<Organizacion, Organizacion>(chunkSize)
            .reader(organizacionesItemReader)
            .processor(PassThroughItemProcessor()) // No es necesario transformar el objeto
            .writer(organizacionItemWriter)
            .build()
    }

    @Bean
    @StepScope
    fun organizacionesItemReader(
        @Value("#{jobParameters['filePath']}") filePath: String,
        objectMapper: ObjectMapper
    ): JsonItemReader<Organizacion> {
        return JsonItemReader<Organizacion>().apply {
            setResource(InputStreamResource(FileInputStream(File(filePath))))
            // busca archivo persistido en el filesystem segun parametro del job
            setJsonObjectReader(JacksonJsonObjectReader(objectMapper, Organizacion::class.java))
        }
    }

    @Bean
    fun organizacionItemWriter(organizacionRepository: OrganizacionRepository): RepositoryItemWriter<Organizacion> {
        return RepositoryItemWriterBuilder<Organizacion>()
            .repository(organizacionRepository)
            .build()
    }

}