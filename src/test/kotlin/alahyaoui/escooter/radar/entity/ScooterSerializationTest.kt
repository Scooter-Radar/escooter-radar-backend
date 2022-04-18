package alahyaoui.escooter.radar.entity

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.core.io.Resource
import java.nio.file.Files

@JsonTest
class ScooterSerializationTest {

    @Value("classpath:sample_lime.json")
    private lateinit var resource_lime: Resource

    @Value("classpath:sample_pony.json")
    private lateinit var resource_pony: Resource

    @Value("classpath:sample_voi.json")
    private lateinit var resource_voi: Resource

    @Autowired
    private lateinit var mapper :ObjectMapper

    @Test
    fun contextLoadsLime(){
        val json = String(Files.readAllBytes(resource_lime.file.toPath()))
        val mapped = mapper.readValue(json, JsonNode::class.java)
        println(mapped)
    }

    @Test
    fun contextLoadsPony(){
        val json = String(Files.readAllBytes(resource_pony.file.toPath()))
        val mapped = mapper.readValue(json, JsonNode::class.java)
        println(mapped)
    }

    @Test
    fun contextLoadsVoi(){
        val json = String(Files.readAllBytes(resource_voi.file.toPath()))
        val mapped = mapper.readValue(json, JsonNode::class.java)
        println(mapped)
    }
}

