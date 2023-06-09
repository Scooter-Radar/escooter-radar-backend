package alahyaoui.escooter.radar.controller

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.service.ScooterService
import alahyaoui.escooter.radar.util.toSlug
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/scooter")
class ScooterRestController(private val scooterService: ScooterService) {

    @GetMapping("/provider")
    fun getScootersByCompany(@RequestParam company: String): Iterable<Scooter> {
        return scooterService.findByCompany(company.toSlug())
    }

    @GetMapping("/location/locality")
    fun getScootersByAddress(@RequestParam address: String): Iterable<Scooter> {
        return scooterService.findByAddress(address.toSlug())
    }

    @GetMapping("/location/within")
    fun getScootersByLocationWithinDegree(@RequestParam latitude: Double, @RequestParam longitude: Double, @RequestParam degree: Double): Iterable<Scooter> {
        return scooterService.findByLocationWithinDegree(latitude, longitude, degree)
    }

    @GetMapping("/location/near")
    fun getScootersNearLocation(@RequestParam latitude: Double, @RequestParam longitude: Double, @RequestParam(name = "limit") limit: Int): Iterable<Scooter> {
        return scooterService.findByLocationNear(latitude, longitude, limit)
    }
}