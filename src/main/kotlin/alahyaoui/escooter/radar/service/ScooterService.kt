package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.ScooterProviderJson
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ScooterService(private val db: ScooterRepository) {

    private val restTemplate = RestTemplate()

    fun getAllScootersBy(zone: String): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()

        try {
            findPony(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) { }

        try {
            findLime(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) { }

        try {
            findBird(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) { }

        try {
            findSpin(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) { }

        db.saveAll(scooters)
        return db.findAllAvailableByZone(zone)
    }

    private fun findLime(zone: String): Array<Scooter>? {
        val uri = "https://data.lime.bike/api/partners/v2/gbfs/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        addZoneAndCompany(response.body?.data?.bikes!!, zone,"Lime")
        return response.body?.data?.bikes
    }

    private fun findBird(zone: String): Array<Scooter>? {
        val uri = "https://mds.bird.co/gbfs/v2/public/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        addZoneAndCompany(response.body?.data?.bikes!!, zone,"Bird")
        return response.body?.data?.bikes
    }

    private fun findPony(zone: String): Array<Scooter>? {
        val uri = "https://gbfs.getapony.com/v1/${zone}/en/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        addZoneAndCompany(response.body?.data?.bikes!!, zone,"Pony")
        return response.body?.data?.bikes
    }

    private fun findSpin(zone: String): Array<Scooter>? {
        val uri = "https://gbfs.spin.pm/api/gbfs/v2_2/${zone}/free_bike_status"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        addZoneAndCompany(response.body?.data?.bikes!!, zone,"Spin")
        return response.body?.data?.bikes
    }

    private fun addZoneAndCompany(scooters: Array<Scooter>, zone: String, company: String) {
        for (i in scooters.indices!!) {
            scooters[i].zone = zone
            scooters[i].company = company
        }
    }
}