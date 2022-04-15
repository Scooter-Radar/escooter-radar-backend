package alahyaoui.escooter.radar.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Scooter (
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

){
    @Id
    @GeneratedValue
    var id: UUID? = null
}