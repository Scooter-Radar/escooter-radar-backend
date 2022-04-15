package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("/")
    fun getScooters(@RequestParam zone: String): MutableIterable<Scooter> {
        return scooterService.getAllScootersBy(zone)
    }
}