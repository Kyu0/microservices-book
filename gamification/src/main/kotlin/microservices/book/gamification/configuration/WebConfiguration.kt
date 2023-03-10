package microservices.book.gamification.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfiguration: WebMvcConfigurer {

    /**
     * CORS(Cross-Origin Resource Sharing) 설정
     * 자세한 정보 : http://docs.spring.io/docs/current/spring-framework-reference/html/cors.html
     * @param registry
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
    }
}