package com.itgura.lmsgateway.filter;

import com.itgura.lmsgateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;



@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

//    @Autowired
//    private RestTemplate template;
    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            //custom pre filter logic
            if(routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
               if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                   return exchange.getResponse().setComplete();
               }
               String authHeader = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
               if(authHeader!=null && authHeader.startsWith("Bearer")) {
                   String token = authHeader.substring(7);
//                   if(!Boolean.TRUE.equals(
//
//                           template.getForObject("http://auth-service/validateToken?token=" + token, Boolean.class)
//                   ) )


//                   {
//                       exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                       return exchange.getResponse().setComplete();
//                   }
                   try{
                          jwtUtil.validateToken(token);

                   }catch (Exception e){
                       exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                       return exchange.getResponse().setComplete();
                   }
               } else {
                   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                   return exchange.getResponse().setComplete();
               }
            }
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                // Put your custom post filter logic here
//            }));
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put configuration properties here
    }
}
