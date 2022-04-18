package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.entity.ScooterId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ScooterRepository : JpaRepository<Scooter, ScooterId> {

    @Query("SELECT s FROM Scooter s WHERE s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailable(): Iterable<Scooter>

    @Query("SELECT s FROM Scooter s WHERE s.zone = :zone AND s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailableByZone(zone: String): Iterable<Scooter>
}