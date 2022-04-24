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
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

class ScooterId : Serializable {
    @JsonProperty("bike_id")
    @Column(name = "bike_id")
    lateinit var bikeId: String

    @JsonProperty("company")
    @Column(name = "company")
    lateinit var company: String
}

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    @JsonProperty("city")
    @Column(name = "city")
    var city: String

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
    var lastReported: Long

    @JsonProperty("current_range_meters")
    @Column(name = "current_range_meters")
    var currentRangeMeters: Double

    constructor(
        bikeId: String,
        city: String,
        company: String,
        location: Point,
        isDisabled: Boolean,
        isReserved: Boolean,
        lastReported: Long,
        currentRangeMeters: Double
    ) {
        this.bikeId = bikeId
        this.city = city
        this.company = company
        this.location = location
        this.isDisabled = isDisabled
        this.isReserved = isReserved
        this.lastReported = lastReported
        this.currentRangeMeters = currentRangeMeters
    }

    constructor(scooter: ScooterDto, city: String, company: String) {
        this.bikeId = scooter.bikeId
        this.isDisabled = scooter.isDisabled
        this.isReserved = scooter.isReserved
        this.lastReported = scooter.lastReported
        this.currentRangeMeters = scooter.currentRangeMeters
        this.city = city
        this.company = company
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)
        this.location = geometryFactory.createPoint(Coordinate(scooter.longitude, scooter.latitude))
    }
}