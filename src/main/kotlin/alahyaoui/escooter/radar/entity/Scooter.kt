package alahyaoui.escooter.radar.entity

import alahyaoui.escooter.radar.util.ScooterPonyDto
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

class ScooterId: Serializable {
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
class Scooter(
    @Id
    @JsonProperty("bike_id")
    @Column(name = "bike_id")
    var bikeId: String,

   // @JsonProperty("lat")
    //@Column(name = "lat")
   // @Transient
   // var latitude: Double,

   // @JsonProperty("lon")
    //@Column(name = "lon")
   // @Transient
   // var longitude: Double,

    @JsonProperty("is_disabled")
    @Column(name = "is_disabled")
    var isDisabled: Boolean,

    @JsonProperty("is_reserved")
    @Column(name = "is_reserved")
    var isReserved: Boolean,

    @JsonProperty("last_reported")
    @Column(name = "last_reported")
    var lastReported: Long,

    @JsonProperty("current_range_meters")
    @Column(name = "current_range_meters")
    var currentRangeMeters: Double,
) {
    @Id
    @JsonProperty("company")
    @Column(name = "company")
    lateinit var company: String

    @JsonProperty("zone")
    @Column(name = "zone")
    lateinit var zone: String

    //@Column (name="location", columnDefinition="geometry(Point,4326)", nullable=true)
    @JsonProperty("location")
    @Column(name="location", columnDefinition = "geography")
    lateinit var location : Point

    constructor(scooter: ScooterPonyDto, zone:String, company:String) : this(scooter.bikeId, scooter.isDisabled,  scooter.isReserved, scooter.lastReported, scooter.currentRangeMeters) {
        this.zone = zone
        this.company = company
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)
        this.location = geometryFactory.createPoint(Coordinate(scooter.longitude, scooter.latitude))
    }

}

//    @JsonSerialize(using = GeometrySerializer::class)
//    @JsonDeserialize(contentUsing = GeometryDeserializer::class)
//    @Column(columnDefinition = "geography")

//private val factory: GeometryFactory = GeometryFactory(PrecisionModel(), 4326)
// Point(Coordinate(longitude, latitude), PrecisionModel(), 4326)
//@Type(type = "org.hibernate.spatial.JTSGeometryType")

//    //@Column(columnDefinition = "Geometry", nullable = true)