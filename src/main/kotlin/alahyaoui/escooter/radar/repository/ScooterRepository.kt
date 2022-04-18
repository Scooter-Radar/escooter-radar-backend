package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ScooterRepository : CrudRepository<Scooter, UUID> {

    @Query("SELECT s FROM Scooter s WHERE s.isDisabled = false AND s.isReserved = false")
    fun findAllAvailable(): Iterable<Scooter>
}