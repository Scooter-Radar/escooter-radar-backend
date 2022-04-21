package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.junit.jupiter.api.Test
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
    fun `List scooters`() {
        val scooterPony = Scooter("pony_2afe18f6934c4e8c9a1f795d4ff2c8a7", 50.854962, 4.397333, false, false, 1650036039, 39144.0)
        val scooterLime = Scooter("e4abdccb-2353-405e-b796-47fee26945c3", 50.8157, 4.435943, false, false, 1650035878, 8004.0)
        val scooterBird = Scooter("6f0df17f-47c4-418f-addd-24a6318dd927", 51.217471666666675, 4.427905, false, false, 1650035914, 12480.0)

        every { scooterService.getAllScootersBy("brussels")} returns listOf(scooterPony, scooterLime)
        mockMvc.perform(get("/api/scooter/brussels").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].id").value(scooterPony.bikeId))
            .andExpect(jsonPath("\$.[1].id").value(scooterLime.bikeId))
    }
}