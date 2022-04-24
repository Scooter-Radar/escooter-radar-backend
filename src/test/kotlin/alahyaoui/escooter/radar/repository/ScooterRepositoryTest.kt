package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ScooterRepositoryTest @Autowired constructor(val entityManager: TestEntityManager, val scooterRepository: ScooterRepository) {

    @Test
    fun `When adding a scooter field then add the scooter to the table`() {
        val id = "6f0df17f-47c4-418f-addd-24a6318dd927"
        val city = "Brussels"
        val company = "Lime"
        val location = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.427905, 51.217471666666675))
        val scooter = Scooter(id, city, company, location, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter)

        entityManager.flush()
        val found = scooterRepository.findAll()
        Assertions.assertThat(found.contains(scooter))
    }

    @Test
    fun `When deleting a scooter field then delete the scooter from the table`() {
        val id = "6f0df17f-47c4-418f-addd-24a6318dd927"
        val city = "Brussels"
        val company = "Lime"
        val location = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.427905, 51.217471666666675))
        val scooter = Scooter(id, city, company, location, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter)

        entityManager.flush()
        scooterRepository.delete(scooter)
        val found = scooterRepository.findAll()
        Assertions.assertThat(!found.contains(scooter))
    }

    @Test
    fun `When findAll then return list of Scooters`() {
        val id_scooter1 = "6f0df17f-47c4-418f-addd-24a6318dd927"
        val city_scooter1 = "Brussels"
        val company_scooter1 = "Lime"
        val location_scooter1 = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.427905, 51.217471666666675))
        val scooter1 = Scooter(id_scooter1, city_scooter1, company_scooter1, location_scooter1, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter1)

        val id_scooter2 = "pony_2afe18f6934c4e8c9a1f795d4ff2c8a7"
        val city_scooter2 = "Brussels"
        val company_scooter2 = "Pony"
        val location_scooter2 = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(4.397333, 50.854962))
        val scooter2 = Scooter(id_scooter2, city_scooter2, company_scooter2, location_scooter2, false, false, 1650036039, 39144.0)
        entityManager.persist(scooter2)

        entityManager.flush()
        val found = scooterRepository.findAll()
        Assertions.assertThat(found.contains(scooter1)).isTrue()
        Assertions.assertThat(found.contains(scooter2)).isTrue()
    }
}