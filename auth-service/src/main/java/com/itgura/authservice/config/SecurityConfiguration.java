package com.itgura.authservice.config;


import com.itgura.authservice.entity.Role;
import com.itgura.authservice.services.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;
    @Autowired
    private CustomOAuth2UserService CustomOAuth2UserService;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {





        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->request.requestMatchers(
                                "/api/v1/auth-service/**",  // Your specific API access,
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**")

                    .permitAll()
                    .requestMatchers("/api/v1/admin/**").hasAnyAuthority(Role.ADMIN.name())
                    .requestMatchers("/api/v1/student/**").hasAnyAuthority(Role.STUDENT.name())
                    .requestMatchers("/api/v1/teacher/**").hasAnyAuthority(Role.TEACHER.name())
                    .anyRequest().authenticated())

                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)

                // Enabling OAuth2 login and defining success handler
                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login") // Custom login page if any
//                        .defaultSuccessUrl("https://github.com") // URL after successful login
                        .successHandler(customOAuth2SuccessHandler) // Custom success handler
                        .failureHandler(customOAuth2FailureHandler) // Custom failure handler
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(CustomOAuth2UserService))) // Custom OAuth2 user service


                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) ;


        return http.build();
    }




}
