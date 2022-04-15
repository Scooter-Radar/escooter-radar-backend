package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import alahyaoui.escooter.radar.util.toSlug
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("/{zone}")
    fun getScooters(@PathVariable zone: String): Iterable<Scooter> {
        return scooterService.getAllScootersBy(zone.toSlug())
    }
}