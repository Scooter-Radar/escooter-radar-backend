package alahyaoui.escooter.radar.util

import alahyaoui.escooter.radar.entity.RentalUris
import alahyaoui.escooter.radar.entity.Scooter
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.delay
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ClassPathResource
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

data class ScooterProviderJson(val data: Data)

data class Data(val bikes: Array<ScooterDto>)

data class ScooterDto(
    @JsonProperty("bike_id")
    val bikeId: String,

    @JsonProperty("lat")
    val latitude: Double,

    @JsonProperty("lon")
    val longitude: Double,

    @JsonProperty("is_disabled")
    val isDisabled: Boolean,

    @JsonProperty("is_reserved")
    val isReserved: Boolean,

    @JsonProperty("last_reported")
    val lastReported: Long?,

    @JsonProperty("current_range_meters")
    val currentRangeMeters: Double?,

    @JsonProperty("pricing_plan_id")
    val pricingPlanId: String?,

    @JsonProperty("rental_uris")
    val rentalUris: RentalUris?,
)

private val restTemplate = RestTemplate()

suspend fun fetchScootersFromProviders(company: String, filePath: String): Iterable<Scooter> {
    val resource = ClassPathResource("data/$filePath.csv")
    val bufferedReader = BufferedReader(InputStreamReader(resource.inputStream))
    val csvParser = CSVParser(bufferedReader, CSVFormat.EXCEL.withFirstRecordAsHeader())

    val scooters = mutableListOf<Scooter>()

    for (csvRecord in csvParser) {
        val address = csvRecord.get("Location")
        val countryCode = csvRecord.get("Country Code")
        val scooterUrl = csvRecord.get("Scooter URL")

        try {
            var response = restTemplate.getForEntity(scooterUrl, ScooterProviderJson::class.java)
            if (response.statusCodeValue == 429) {
                println("Too Many Requests will delay and retry ${response.statusCode}")
                delay(9000)
                response = restTemplate.getForEntity(scooterUrl, ScooterProviderJson::class.java)
            }
            scooters.addAll(convertScooterDtoToScooter(response.body?.data?.bikes, company, address, countryCode))
        } catch (err: HttpStatusCodeException) {
            //err.printStackTrace()
            println("Error while fetching from $company: ${err.message}")
        }
    }
    println("Number of fetched scooter for $company is ${scooters.size}")
    return scooters
}

private fun convertScooterDtoToScooter(
    scooterDtos: Array<ScooterDto>?,
    company: String,
    address: String,
    countryCode: String
): Iterable<Scooter> {
    val scooters = mutableListOf<Scooter>()
    if (scooterDtos != null) {
        for (scooterDto in scooterDtos) {
            scooters.add(Scooter(scooter = scooterDto, company = company, address = address, countryCode = countryCode))
        }
    }
    return scooters
}