package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.repository.ScooterRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ScooterService(private val db: ScooterRepository, private val restTemplate: RestTemplate) {
    fun getAllScootersBy(zone: String): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()
        findLime(zone)?.let { scooters.addAll(it) }
        findBird(zone)?.let { scooters.addAll(it) }
        findPony(zone)?.let { scooters.addAll(it) }
        db.saveAll(scooters)
        return db.findAll()
    }

    private fun findLime(zone: String): Array<Scooter>? {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val entity = HttpEntity<String>(headers)

        val uri = "https://data.lime.bike/api/partners/v2/gbfs/${zone}/free_bike_status.json"
        val response = restTemplate.exchange(uri, HttpMethod.GET, entity, Array<Scooter>::class.java)
        return response.body
    }

    private fun findBird(zone: String): Array<Scooter>? {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val entity = HttpEntity<String>(headers)

        val uri = "https://mds.bird.co/gbfs/v2/public/${zone}/free_bike_status.json"
        val response = restTemplate.exchange(uri, HttpMethod.GET, entity, Array<Scooter>::class.java)
        return response.body
    }

    private fun findPony(zone: String): Array<Scooter>? {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val entity = HttpEntity<String>(headers)

        val uri = "https://gbfs.getapony.com/v1/${zone}/en/free_bike_status.json"
        val response = restTemplate.exchange(uri, HttpMethod.GET, entity, Array<Scooter>::class.java)
        return response.body
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}