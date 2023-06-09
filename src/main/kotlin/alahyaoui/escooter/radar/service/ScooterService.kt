package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.fetchScootersFromProviders
import kotlinx.coroutines.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalTime
import java.util.concurrent.TimeUnit


@Service
class ScooterService(private val db: ScooterRepository) {

    fun findByCompany(company: String): Iterable<Scooter> {
        return db.findAvailableByCompany(company)
    }

    fun findByAddress(address: String): Iterable<Scooter> {
        return db.findAvailableByAddress(address)
    }

    fun findByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter> {
        return db.findAvailableByLocationWithinDegree(latitude, longitude, degree)
    }

    fun findByLocationNear(latitude: Double, longitude: Double, limit: Int): Iterable<Scooter> {
        return db.findAvailableNearest(latitude, longitude, limit)
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    private fun fetchScootersFromProvidersTask() {
        println("${LocalTime.now()}: FETCHING SCOOTER FROM PROVIDERS API TASK STARTED")
        val resource = ClassPathResource("data/providers_resource.csv")
        val bufferedReader = BufferedReader(InputStreamReader(resource.inputStream))
        val csvParser = CSVParser(bufferedReader, CSVFormat.EXCEL.withFirstRecordAsHeader())

        db.deleteAllInBatch()
        println("${LocalTime.now()}: SCOOTER TABLE CLEARED")

        val jobs = mutableListOf<Job>()
        for (csvRecord in csvParser) {
            jobs.add(GlobalScope.launch(Dispatchers.IO) {
                val company = csvRecord.get("Company")
                val fileName = csvRecord.get("File Name")
                try {
                    val scooters = fetchScootersFromProviders(company, fileName)
                    db.saveAll(scooters)
                } catch (ex: Exception) {
                    println("Caused by $company for $fileName : ${ex.message}")
                }
            })
        }
        runBlocking {
            for (job in jobs) {
                job.join()
            }
            println("${LocalTime.now()}: SCOOTER TABLE FILLED")
            println("${LocalTime.now()}: FETCHING SCOOTER FROM PROVIDERS API TASK ENDED")
        }
    }
}