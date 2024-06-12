package in.kshitij.users_queries_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsersQueriesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersQueriesServiceApplication.class, args);
	}

}
