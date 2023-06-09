package alahyaoui.escooter.radar.entity

import alahyaoui.escooter.radar.util.ScooterDto
import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import java.io.Serializable
import java.util.*
import javax.persistence.*

class ScooterId : Serializable {
    @JsonProperty("bike_id")
    @Column(name = "bike_id")
    lateinit var bikeId: String

    @JsonProperty("company")
    @Column(name = "company")
    lateinit var company: String
}

@Embeddable
data class RentalUris(
    @JsonProperty("android")
    val android: String,

    @JsonProperty("ios")
    val ios: String
)

@Entity
@JsonInclude(JsonInclude.Include.ALWAYS)
@IdClass(ScooterId::class)
class Scooter {
    @Id
    @JsonProperty("bike_id")
    @Column(name = "bike_id")
    var bikeId: String

    @Id
    @JsonProperty("company")
    @Column(name = "company")
    var company: String

    @JsonProperty("address")
    @Column(name = "address")
    var address: String

    @JsonProperty("country_code")
    @Column(name = "country_code")
    var countryCode: String

    @JsonProperty("location")
    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
    var location: Point

    @JsonProperty("is_disabled")
    @Column(name = "is_disabled")
    var isDisabled: Boolean

    @JsonProperty("is_reserved")
    @Column(name = "is_reserved")
    var isReserved: Boolean

    @JsonProperty("last_reported")
    @Column(name = "last_reported")
    var lastReported: Long?

    @JsonProperty("current_range_meters")
    @Column(name = "current_range_meters")
    var currentRangeMeters: Double?

    @JsonProperty("rental_uris")
    @Column(name = "rental_uris")
    var rentalUris: RentalUris?

    constructor(
        bikeId: String,
        company: String,
        address: String,
        countryCode: String,
        location: Point,
        isDisabled: Boolean,
        isReserved: Boolean,
        lastReported: Long,
        currentRangeMeters: Double,
        rentalUris: RentalUris
    ) {
        this.bikeId = bikeId
        this.company = company
        this.address = address
        this.countryCode = countryCode
        this.location = location
        this.isDisabled = isDisabled
        this.isReserved = isReserved
        this.lastReported = lastReported
        this.currentRangeMeters = currentRangeMeters
        this.rentalUris = rentalUris
    }

    constructor(scooter: ScooterDto, company: String, address: String, countryCode: String) {
        this.bikeId = scooter.bikeId
        this.company = company
        this.address = address
        this.countryCode = countryCode
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)
        this.location = geometryFactory.createPoint(Coordinate(scooter.longitude, scooter.latitude))
        this.isDisabled = scooter.isDisabled
        this.isReserved = scooter.isReserved
        this.lastReported = scooter.lastReported
        this.currentRangeMeters = scooter.currentRangeMeters
        this.rentalUris = scooter.rentalUris
    }
}