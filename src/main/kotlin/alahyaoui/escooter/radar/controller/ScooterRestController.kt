package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import alahyaoui.escooter.radar.util.toSlug
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("")
    fun getScootersByCompany(@RequestParam company: String): Iterable<Scooter> {
        return scooterService.findByCompany(company.toSlug())
    }

    @GetMapping("/zone")
    fun getScootersByZone(@RequestParam city: String): Iterable<Scooter> {
        return scooterService.getAllScootersBy(city.toSlug())
    }

    @GetMapping("/location")
    fun getScootersByLocationWithinDegree(@RequestParam latitude: Double, @RequestParam longitude: Double, @RequestParam degree: Double): Iterable<Scooter> {
        return scooterService.findByLocationWithinDegree(latitude, longitude, degree)
    }
}