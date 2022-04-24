package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.getScootersFromBird
import alahyaoui.escooter.radar.util.getScootersFromLime
import alahyaoui.escooter.radar.util.getScootersFromPony
import alahyaoui.escooter.radar.util.getScootersFromSpin
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.FileReader
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
        val resource = ClassPathResource("data/companies_cities.csv")
        val bufferedReader = BufferedReader(FileReader(resource.file))
        val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT)
        for (csvRecord in csvParser) {
            val company = csvRecord.get(0);
            val city = csvRecord.get(1);

            try {
                val scooters = when (company.lowercase()) {
                    "pony" -> getScootersFromPony(city)
                    "lime" -> getScootersFromLime(city)
                    "bird" -> getScootersFromBird(city)
                    "spin" -> getScootersFromSpin(city)
                    else -> continue
                }
                db.saveAll(scooters)
            } catch (ex: Exception) {
                println("Caused by ${company} for ${city} : ${ex.message}")
            }
        }
    }
}