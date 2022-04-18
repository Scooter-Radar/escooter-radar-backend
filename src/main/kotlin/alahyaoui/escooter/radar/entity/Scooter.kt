package alahyaoui.escooter.radar.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.IdClass

class ScooterId: Serializable {
    @JsonProperty("bike_id")
    lateinit var bikeId: String

    @Id
    @JsonProperty("company")
    lateinit var company: String
}

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@IdClass(ScooterId::class)
class Scooter(
    @Id
    @JsonProperty("bike_id")
    var bikeId: String,

    @JsonProperty("lat")
    var latitude: Double,

    @JsonProperty("lon")
    var longitude: Double,

    @JsonProperty("is_disabled")
    var isDisabled: Boolean,

    @JsonProperty("is_reserved")
    var isReserved: Boolean,

    @JsonProperty("last_reported")
    var lastReported: Long,

    @JsonProperty("current_range_meters")
    var currentRangeMeters: Double,
) {
    @Id
    @JsonProperty("company")
    lateinit var company: String

    @JsonProperty("zone")
    lateinit var zone: String
}