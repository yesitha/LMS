package com.itgura.lmsgateway.filter;



import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth-service/authenticate",
            "/auth-service/register",
            "/auth-service/refresh",
            "/auth-service/validateToken",
            "/eureka",
            "/resource-management/public",
            "/auth-service/login/oauth2/code/google",
            "auth-service/oauth2/authorization/google"



    );

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
