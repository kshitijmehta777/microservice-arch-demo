package in.kshitij.api_gateway_service.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfigs {

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("users-queries-service", p -> p.path("/usersQ/**").uri("lb://users-queries-service"))
				.route("users-command-service", p -> p.path("/usersC/**").uri("lb://users-command-service"))
				.route("notes-service", p -> p.path("/notes/**").uri("lb://notes-service"))
				.build();
	}
}
