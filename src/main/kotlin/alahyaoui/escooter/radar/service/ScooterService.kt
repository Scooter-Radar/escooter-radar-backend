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


    fun findByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter> {
        return db.findAvailableByLocationWithinDegree(latitude, longitude, degree)
    }

    fun getAllScootersBy(zone: String): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()

        try {
            findPony(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findLime(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findBird(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        try {
            findSpin(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }

        db.saveAll(scooters)
        return db.findAllAvailableByZone(zone)
    }

    private fun findLime(zone: String): Iterable<Scooter> {
        val uri = "https://data.lime.bike/api/partners/v2/gbfs/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Lime")
    }

    private fun findBird(zone: String): Iterable<Scooter> {
        val uri = "https://mds.bird.co/gbfs/v2/public/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Bird")
    }

    private fun findPony(zone: String): Iterable<Scooter> {
        val uri = "https://gbfs.getapony.com/v1/${zone}/en/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Pony")
    }

    private fun findSpin(zone: String): Iterable<Scooter> {
        val uri = "https://gbfs.spin.pm/api/gbfs/v2_2/${zone}/free_bike_status"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Spin")
    }

    private fun convertScooterDtoToScooter(scootersDto: Array<ScooterDto>?, zone: String, company: String): Iterable<Scooter>{
        val scooters = mutableListOf<Scooter>()
        if (scootersDto != null) {
            for(scooter in scootersDto){
                scooters.add(Scooter(scooter, zone, company))
            }
        }
        return scooters
    }
}