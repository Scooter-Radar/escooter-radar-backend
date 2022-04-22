package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.entity.ScooterId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ScooterRepository : JpaRepository<Scooter, ScooterId> {

    @Query("SELECT s FROM Scooter s WHERE s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailable(): Iterable<Scooter>

    @Query("SELECT s FROM Scooter s WHERE s.zone = :zone AND s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailableByZone(zone: String): Iterable<Scooter>

    @Query(
        value = "SELECT * " +
                "FROM Scooter " +
                "WHERE is_disabled = false AND is_reserved = false " +
                "AND ST_DWithin(location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :degree)",
        nativeQuery = true
    )
    fun findAvailableByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter>
}