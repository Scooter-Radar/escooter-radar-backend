package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.RentalUris
import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ScooterRestControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var scooterService: ScooterService

    @Test
    fun `Get list of scooters by city`() {
        val id_pony = "pony_2afe18f6934c4e8c9a1f795d4ff2c8a7"
        val address_pony = "Brussels"
        val country_code_pony = "BE"
        val company_pony = "Pony"
        val location_pony = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.397333, 50.854962))
        val rental_uris_pony = RentalUris("https://example/android.com", "https://example/ios.com")
        val scooterPony = Scooter(
            bikeId = id_pony,
            address = address_pony,
            company = company_pony,
            countryCode = country_code_pony,
            location = location_pony,
            isDisabled = false,
            isReserved = false,
            lastReported = 1650036039,
            currentRangeMeters = 39144.0,
            rentalUris = rental_uris_pony
        )

        val id_lime = "6f0df17f-47c4-418f-addd-24a6318dd927"
        val address_lime = "Paris"
        val country_code_lime = "FR"
        val company_lime = "Lime"
        val location_lime =
            GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.427905, 51.217471666666675))
        val rental_uris_lime = RentalUris("https://example/android.com", "https://example/ios.com")
        val scooterLime = Scooter(
            bikeId = id_lime,
            address = address_lime,
            company = company_lime,
            countryCode = country_code_lime,
            location = location_lime,
            isDisabled = false,
            isReserved = false,
            lastReported = 1650035914,
            currentRangeMeters = 12480.0,
            rentalUris = rental_uris_lime
        )

        every { scooterService.findByAddress("Paris") } returns listOf(scooterPony, scooterLime)
        mockMvc.perform(
            get("/api/scooter/address")
                .requestAttr("address", "paris")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].bikeId").value(scooterPony.bikeId))
    }
}