package com.itgura.lmsgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class OAuth2PathRewriteFilter extends AbstractGatewayFilterFactory<OAuth2PathRewriteFilter.Config> {

    public static class Config {
        // Put any needed configuration properties here
    }

    public OAuth2PathRewriteFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerWebExchange modifiedExchange = exchange;

            // Get the original request path
            String path = exchange.getRequest().getURI().getPath();

            // Check if the request is for OAuth2 callback or authorization paths
            if (path.contains("/login/oauth2/code/google") || path.contains("/oauth2/authorization/google")) {
                // Rewrite the path by removing '/api/v1/auth-service'
                String newPath = path.replace("/api/v1/auth-service", "");

                // Mutate the request with the new path
                modifiedExchange = exchange.mutate().request(exchange.getRequest().mutate().path(newPath).build()).build();
            }

            // Proceed with the filter chain
            return chain.filter(modifiedExchange);
        };
    }
}
