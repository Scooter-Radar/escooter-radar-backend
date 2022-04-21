package alahyaoui.escooter.radar.util

import alahyaoui.escooter.radar.entity.Scooter
import com.fasterxml.jackson.annotation.JsonProperty
import org.locationtech.jts.geom.Point
import javax.persistence.Column
import javax.persistence.Id

data class ScooterProviderJson(val data: Data)

data class Data(val bikes: Array<ScooterPonyDto>)


data class ScooterPonyDto(
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

    @JsonProperty("current_fuel_percent")
    var currentFuelPercent: Double,

    @JsonProperty("current_range_meters")
    var currentRangeMeters: Double,

    @JsonProperty("pricing_plan_id")
    var pricingPlanId: String,

    //@JsonProperty("rental_uris")
    //var rentalUris: Array<String>,

    @JsonProperty("system_id")
    var systemId: String,

    @JsonProperty("vehicle_type_id")
    var vehicleTypeId: String
)