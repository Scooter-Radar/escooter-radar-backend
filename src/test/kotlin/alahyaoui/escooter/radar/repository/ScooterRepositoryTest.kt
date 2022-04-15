package alahyaoui.escooter.radar.repository

import alahyaoui.escooter.radar.entity.Scooter
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class ScooterRepositoryTest @Autowired constructor(val entityManager: TestEntityManager, val scooterRepository: ScooterRepository) {

    @Test
    fun `When adding a scooter field then add the scooter to the table`() {
        val scooter = Scooter("6f0df17f-47c4-418f-addd-24a6318dd927", 51.217471666666675, 4.427905, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter)

        entityManager.flush()
        val found = scooterRepository.findAll()
        Assertions.assertThat(found.contains(scooter))
    }

    @Test
    fun `When deleting a scooter field then delete the scooter from the table`() {
        val scooter = Scooter("6f0df17f-47c4-418f-addd-24a6318dd927", 51.217471666666675, 4.427905, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter)

        entityManager.flush()
        scooterRepository.delete(scooter)
        val found = scooterRepository.findAll()
        Assertions.assertThat(!found.contains(scooter))
    }

    @Test
    fun `When findAll then return list of Scooters`() {
        val scooter1 = Scooter("6f0df17f-47c4-418f-addd-24a6318dd927", 51.217471666666675, 4.427905, false, false, 1650035914, 12480.0)
        entityManager.persist(scooter1)

        val scooter2 = Scooter("pony_2afe18f6934c4e8c9a1f795d4ff2c8a7", 50.854962, 4.397333, false, false, 1650036039, 39144.0)
        entityManager.persist(scooter2)

        entityManager.flush()
        val found = scooterRepository.findAll()
        Assertions.assertThat(found.contains(scooter1)).isTrue()
        Assertions.assertThat(found.contains(scooter2)).isTrue()
    }
}