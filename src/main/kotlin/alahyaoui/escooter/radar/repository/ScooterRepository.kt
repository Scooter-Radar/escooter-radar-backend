package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
//import alahyaoui.escooter.radar.entity.ScooterId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface ScooterRepository : JpaRepository<Scooter, /*ScooterId*/String> {

    @Query("SELECT s FROM Scooter s WHERE s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailable(): Iterable<Scooter>

    @Query("SELECT s FROM Scooter s WHERE s.zone = :zone AND s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailableByZone(zone: String): Iterable<Scooter>

    @Query(
        value = "SELECT s, ST_Distance(s.location,ST_SetSRID(ST_Point(:longitude,:latitude),4326)) AS distance "
                + "FROM Scooter s "
                + "ORDER BY s.location  <-> ST_SetSRID(ST_Point(:longitude,:latitude),4326) "
                + "LIMIT 30",
        nativeQuery = true
    )
    fun findAllByDistanceFromLocation(@Param("longitude") longitude: Double, @Param("latitude") latitude: Double): Iterable<Scooter>


    @Query(
        value = "SELECT s, ST_Distance(s.location,ST_SetSRID(ST_Point(:longitude,:latitude),:distance)) AS distance "
                + "FROM Scooter s "
                + "ORDER BY s.location  <-> ST_SetSRID(ST_Point(:longitude,:latitude),:distance) "
                + "LIMIT 30",
        nativeQuery = true
    )
    fun findAllByDistanceFromLocation(@Param("longitude") longitude: Double, @Param("latitude") latitude: Double, distance: Double): Iterable<Scooter>
}

/*@Query("SELECT s FROM Scooter s" +
" WHERE intersects(s.location, :area) = true" +
" AND s.isDisabled = false AND s.isReserved = false")
fun findAvailableWithinArea(area: Point): List<Scooter>*/

//fun findByLocationWithin(location: Point, distance: Distance): Iterable<Scooter>

//fun findByLocationNear(location: Point, oneKilometer: Distance): GeoResults<Scooter>