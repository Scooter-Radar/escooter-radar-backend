package alahyaoui.escooter.radar.util

import alahyaoui.escooter.radar.entity.Scooter
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.client.RestTemplate

data class ScooterProviderJson(val data: Data)

data class Data(val bikes: Array<ScooterDto>)

data class ScooterDto(
    @JsonProperty("bike_id")
    var bikeId: String,

    @JsonProperty("lat")
    var latitude: Double,

    @JsonProperty("lon")
    var longitude: Double,

    @JsonProperty("is_reserved")
    var isReserved: Boolean,

    @JsonProperty("is_disabled")
    var isDisabled: Boolean,

    @JsonProperty("current_range_meters")
    var currentRangeMeters: Double,

    @JsonProperty("vehicle_type_id")
    var vehicleTypeId: String,

    @JsonProperty("last_reported")
    var lastReported: Long,
)

private val restTemplate = RestTemplate()

public fun fetchScooters(company: String, city: String): Iterable<Scooter> {
    return when (company.lowercase()) {
        "lime" -> fetchScootersFromLime(city)
        "bird" -> fetchScootersFromBird(city)
        "pony" -> fetchScootersFromPony(city)
        "spin" -> fetchScootersFromSpin(city)
        else -> mutableListOf()
    }
}

private fun fetchScootersFromLime(city: String): Iterable<Scooter> {
    val uri = "https://data.lime.bike/api/partners/v2/gbfs/${city}/free_bike_status.json"
    val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
    return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Lime")
}

private fun fetchScootersFromBird(city: String): Iterable<Scooter> {
    val uri = "https://mds.bird.co/gbfs/v2/public/${city}/free_bike_status.json"
    val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
    return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Bird")
}

private fun fetchScootersFromPony(city: String): Iterable<Scooter> {
    val uri = "https://gbfs.getapony.com/v1/${city}/en/free_bike_status.json"
    val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
    return convertScooterDtoToScooter(response.body?.data?.bikes, city, "Pony")
}

private fun fetchScootersFromSpin(city: String): Iterable<Scooter> {
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