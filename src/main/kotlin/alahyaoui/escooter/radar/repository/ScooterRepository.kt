package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScooterRepository : CrudRepository<Scooter, Long> {
}