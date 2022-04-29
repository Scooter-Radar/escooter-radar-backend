package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.getScootersFromBird
import alahyaoui.escooter.radar.util.getScootersFromLime
import alahyaoui.escooter.radar.util.getScootersFromPony
import alahyaoui.escooter.radar.util.getScootersFromSpin
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

    fun findByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter> {
        return db.findAvailableByLocationWithinDegree(latitude, longitude, degree)
    }

    fun findByCity(city: String): Iterable<Scooter> {
        return db.findAvailableByCity(city)
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    private fun fetchScootersFromProvidersTask() {
        println("${LocalTime.now()}: FETCHING SCOOTER FROM PROVIDERS API TASK STARTED")
        val resource = ClassPathResource("data/companies_cities.csv")
        val bufferedReader = BufferedReader(InputStreamReader(resource.inputStream))
        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)

        db.deleteAllInBatch()
        println("${LocalTime.now()}: SCOOTER TABLE CLEARED")

        val jobs = mutableListOf<Job>()
        for (csvRecord in csvParser) {
            jobs.add(GlobalScope.launch(Dispatchers.IO)  {
                val company = csvRecord.get(0)
                val city = csvRecord.get(1)
                try {
                    val scooters = when (company.lowercase()) {
                        "pony" -> getScootersFromPony(city)
                        "lime" -> getScootersFromLime(city)
                        "bird" -> getScootersFromBird(city)
                        "spin" -> getScootersFromSpin(city)
                        else -> mutableListOf()
                    }
                    db.saveAll(scooters)
                } catch (ex: Exception) {
                    println("Caused by ${company} for ${city} : ${ex.message}")
                }
            })
        }
        runBlocking {
            for (job in jobs) {
                job.join()
            }
            println("${LocalTime.now()}: FETCHING SCOOTER FROM PROVIDERS API TASK ENDED")
        }
    }
}