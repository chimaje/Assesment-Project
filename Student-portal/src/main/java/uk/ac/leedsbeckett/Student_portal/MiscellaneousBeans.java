package uk.ac.leedsbeckett.Student_portal;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MiscellaneousBeans {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();  // Create and return a RestTemplate instance
    }
}
