package com.itgura.lmsgateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;


@SpringBootApplication
@EnableDiscoveryClient
//@OpenAPIDefinition(info = @Info(title = "LMS API Gateway", version = "1.0", description = "Documentation API Gateway v1.0"))
public class LmsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsGatewayApplication.class, args);
	}

//	@Bean
//	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//		return builder
//				.routes()
//				.route(r -> r.path("/dms-mediator/v1/api-docs").and().method(HttpMethod.GET).uri("lb://dms-mediator"))
//
//
//    .build();
//	}


}
