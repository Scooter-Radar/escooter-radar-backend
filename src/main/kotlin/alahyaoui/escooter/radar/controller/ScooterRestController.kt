package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("/{zone}")
    fun getScooters(@PathVariable zone: String): MutableIterable<Scooter> {
        return scooterService.getAllScootersBy(zone)
    }
}