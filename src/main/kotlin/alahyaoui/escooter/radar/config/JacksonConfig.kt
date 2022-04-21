package alahyaoui.escooter.radar.config

import com.bedatadriven.jackson.datatype.jts.JtsModule
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    fun jtsModule(): JtsModule {
        return JtsModule()
    }
}