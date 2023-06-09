package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import alahyaoui.escooter.radar.entity.ScooterId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface ScooterRepository : JpaRepository<Scooter, ScooterId> {

    @Query("SELECT s FROM Scooter s WHERE s.isDisabled = false AND s.isReserved = false")
    fun findAvailable(): Iterable<Scooter>

    @Query("SELECT s FROM Scooter s WHERE LOWER(s.address) LIKE LOWER(concat('%', :address, '%')) AND s.isDisabled = false AND s.isReserved = false")
    fun findAvailableByAddress(address: String): Iterable<Scooter>

    @Query("SELECT s FROM Scooter s WHERE LOWER(s.company) LIKE LOWER(:company) AND s.isDisabled = false AND s.isReserved = false")
    fun findAvailableByCompany(company: String): Iterable<Scooter>

    @Query(
        value = "SELECT * " +
                "FROM Scooter " +
                "WHERE is_disabled = false AND is_reserved = false " +
                "AND ST_DWithin(location, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326), :degree)",
        nativeQuery = true
    )
    fun findAvailableByLocationWithinDegree(latitude: Double, longitude: Double, degree: Double): Iterable<Scooter>

    @Query(
        value = "SELECT * " +
                "FROM (SELECT *, location <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS distance " +
                        "FROM Scooter " +
                        "WHERE is_disabled = false AND is_reserved = false " +
                        "ORDER BY distance " +
                        "LIMIT :limit " +
                ") AS NEAREST_SCOOTERS",
        nativeQuery = true
    )
    fun findAvailableNearest(latitude: Double, longitude: Double, limit: Int): Iterable<Scooter>
}