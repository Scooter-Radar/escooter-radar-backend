package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.ScooterDto
import alahyaoui.escooter.radar.util.ScooterProviderJson
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ScooterService(private val db: ScooterRepository) {

    private val restTemplate = RestTemplate()

    fun findByCompany(company: String): Iterable<Scooter> {
        return db.findAvailableByCompany(company)
    }

    fun findByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter> {
        return db.findAvailableByLocationWithinDegree(latitude, longitude, degree)
    }

    fun findByCity(city: String): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()

        try {
            findPony(city)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findLime(city)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findBird(city)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findSpin(city)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        db.saveAll(scooters)
        return db.findAvailableByCity(city)
    }

    private fun findLime(city: String): Iterable<Scooter> {
        val uri = "https://data.lime.bike/api/partners/v2/gbfs/${city}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Lime")
    }

    private fun findBird(city: String): Iterable<Scooter> {
        val uri = "https://mds.bird.co/gbfs/v2/public/${city}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Bird")
    }

    private fun findPony(city: String): Iterable<Scooter> {
        val uri = "https://gbfs.getapony.com/v1/${city}/en/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Pony")
    }

    private fun findSpin(city: String): Iterable<Scooter> {
        val uri = "https://gbfs.spin.pm/api/gbfs/v2_2/${city}/free_bike_status"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Spin")
    }

    private fun convertScooterDtoToScooter(scootersDto: Array<ScooterDto>?, city: String, company: String): Iterable<Scooter>{
        val scooters = mutableListOf<Scooter>()
        if (scootersDto != null) {
            for(scooter in scootersDto){
                scooters.add(Scooter(scooter, city, company))
            }
        }
        return scooters
    }
}