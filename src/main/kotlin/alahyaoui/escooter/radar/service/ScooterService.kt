package alahyaoui.escooter.radar.service

import alahyaoui.escooter.radar.entity.Scooter
//import alahyaoui.escooter.radar.entity.ScooterId
import alahyaoui.escooter.radar.repository.ScooterRepository
import alahyaoui.escooter.radar.util.ScooterPonyDto
import alahyaoui.escooter.radar.util.ScooterProviderJson
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ScooterService(private val db: ScooterRepository) {

    private val restTemplate = RestTemplate()

    /*fun findWithinArea(location: Point): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()
        String zone =

        return db.findAvailableWithinArea(location)
    }*/

    /*fun findWithinDistanceInKm(location: Point, distance: Double): Iterable<Scooter> {
        /*
        val scooters = mutableListOf<Scooter>()
        String zone =
        if(!scooters.isNullOrEmpty()) db.saveAll(scooters)
        */
       return db.findByLocationWithin(location, Distance(distance, Metrics.KILOMETERS))
   }*/

    /*fun findNear(location: Point): Iterable<Scooter> {
        return db.findAvailableWithinArea(location)
    }*/

    fun findNear(latitude: Double, longitude: Double): Iterable<Scooter> {
        return db.findAllByDistanceFromLocation(latitude, longitude)
    }

    fun findNear(latitude: Double, longitude: Double, distance: Double): Iterable<Scooter> {
        return db.findAllByDistanceFromLocation(latitude, longitude, distance)
    }

    fun getAllScootersBy(zone: String): Iterable<Scooter> {
        val scooters = mutableListOf<Scooter>()

        //try {
            findPony(zone)?.let { scooters.addAll(it) }
        //} catch (ex: Exception) {
        //    println(ex.message)
        //}

        //try {
        //    findLime(zone)?.let { scooters.addAll(it) }
        //} catch (ex: Exception) {
        //    println(ex.message)
        //}

        /*try {
            findBird(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }*/

        /*try {
            findSpin(zone)?.let { scooters.addAll(it) }
        } catch (ex: Exception) {
            println(ex.message)
        }*/

        db.saveAll(scooters)
        return db.findAll()//db.findAllAvailableByZone(zone)
    }

    private fun findLime(zone: String): Iterable<Scooter> {
        val uri = "https://data.lime.bike/api/partners/v2/gbfs/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        //addMissingData(response.body?.data?.bikes!!, zone,"Lime")
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Lime")
    }

    private fun findBird(zone: String): Iterable<Scooter> {
        val uri = "https://mds.bird.co/gbfs/v2/public/${zone}/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        //addMissingData(response.body?.data?.bikes!!, zone,"Bird")
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Bird")
    }

    private fun findPony(zone: String): Iterable<Scooter> {
        val uri = "https://gbfs.getapony.com/v1/${zone}/en/free_bike_status.json"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        //addMissingData(response.body?.data?.bikes!!, zone,"Pony")
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Pony")
    }

    private fun findSpin(zone: String): Iterable<Scooter> {
        val uri = "https://gbfs.spin.pm/api/gbfs/v2_2/${zone}/free_bike_status"
        val response = restTemplate.getForEntity(uri, ScooterProviderJson::class.java)
        //addMissingData(response.body?.data?.bikes!!, zone,"Spin")
        return convertScooterDtoToScooter(response.body?.data?.bikes, zone, "Spin")
    }

    /*private fun addMissingData(scooters: Array<ScooterPonyDto>, zone: String, company: String) {
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)

        for (i in scooters.indices!!) {
            val point: Point = geometryFactory.createPoint(Coordinate(scooters[i].longitude, scooters[i].latitude))
            scooters[i].location = point
            scooters[i].zone = zone
            scooters[i].company = company
        }
    }*/

    private fun convertScooterDtoToScooter(scootersDto: Array<ScooterPonyDto>?, zone: String, company: String): Iterable<Scooter>{
        val scooters = mutableListOf<Scooter>()
        if (scootersDto != null) {
            for(scooter in scootersDto){
                scooters.add(Scooter(scooter, zone, company))
            }
        }
        return scooters
    }
}