package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ScooterRestControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var scooterService: ScooterService

    @Test
    fun `Get list of scooters by city`() {
        val id_pony = "pony_2afe18f6934c4e8c9a1f795d4ff2c8a7"
        val city_pony = "Brussels"
        val company_pony = "Pony"
        val location_pony = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.397333, 50.854962))
        val scooterPony = Scooter(id_pony, city_pony, company_pony, location_pony, false, false, 1650036039, 39144.0)

        val id_lime = "6f0df17f-47c4-418f-addd-24a6318dd927"
        val city_lime = "Paris"
        val company_lime = "Lime"
        val location_lime = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.427905, 51.217471666666675))
        val scooterLime = Scooter(id_lime, city_lime, company_lime, location_lime, false, false, 1650035914, 12480.0)

        every { scooterService.findByCity("Paris")} returns listOf(scooterPony, scooterLime)
        mockMvc.perform(get("/api/scooter/zone")
            .requestAttr("city", "paris")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].bikeId").value(scooterPony.bikeId))
    }
}