package com.itgura.lmsgateway.filter;



import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth-service/authenticate",
            "/api/v1/auth-service/register",
            "/api/v1/auth-service/refresh",
            "/eureka"


    );

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
