package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import alahyaoui.escooter.radar.util.toSlug
import org.springframework.data.geo.GeoResults
import org.springframework.data.geo.Point
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("/{zone}")
    fun getScooters(@PathVariable zone: String): Iterable<Scooter> {
        return scooterService.getAllScootersBy(zone.toSlug())
    }

    @GetMapping("/location")
    fun getScooters(@RequestParam latitude: Double, @RequestParam longitude: Double): Iterable<Scooter> {
        return scooterService.findNear(longitude, latitude)
    }

    @GetMapping("/location2")
    fun getScooters(@RequestParam latitude: Double, @RequestParam longitude: Double, @RequestParam distance: Double): Iterable<Scooter> {
        return scooterService.findNear(longitude, latitude, distance)
    }
}