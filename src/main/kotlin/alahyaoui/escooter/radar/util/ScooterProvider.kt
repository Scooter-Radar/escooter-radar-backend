package alahyaoui.escooter.radar.util

import alahyaoui.escooter.radar.entity.Scooter
import com.fasterxml.jackson.annotation.JsonProperty
import org.locationtech.jts.geom.Point
import javax.persistence.Column
import javax.persistence.Id

data class ScooterProviderJson(val data: Data)

data class Data(val bikes: Array<ScooterDto>)

data class ScooterDto(
    @JsonProperty("bike_id")
    var bikeId: String,

    @JsonProperty("lat")
    var latitude: Double,

    @JsonProperty("lon")
    var longitude: Double,

    @JsonProperty("is_reserved")
    var isReserved: Boolean,

    @JsonProperty("is_disabled")
    var isDisabled: Boolean,

    @JsonProperty("current_range_meters")
    var currentRangeMeters: Double,

    @JsonProperty("vehicle_type_id")
    var vehicleTypeId: String,

    @JsonProperty("last_reported")
    var lastReported: Long,
)
