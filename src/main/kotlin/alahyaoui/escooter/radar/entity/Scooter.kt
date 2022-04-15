package alahyaoui.escooter.radar.entity

import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Scooter (
    @Id
    @GeneratedValue
    var id: UUID,

    var bike_id: String,

    var lat: Double,

    var lon: Double,

    var is_disabled: Boolean,

    var is_reserved: Boolean,

    var last_reported: LocalDateTime,

    var current_range_meters: Double,
)