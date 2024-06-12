package in.kshitij.notes_service.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import io.micrometer.observation.ObservationRegistry;

@Configuration
public class BasicConfigs {
	
	@Autowired
	ObservationRegistry observationRegistry;
	
	@LoadBalanced
	@Bean
	RestClient.Builder webClientBuilder() {
		return RestClient.builder()
				.observationRegistry(observationRegistry);
	}

}
