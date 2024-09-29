package com.itgura.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itgura.authservice.dto.response.AuthenticationResponse;
import com.itgura.authservice.services.AuthenticationService;
import com.itgura.authservice.services.JwtService;
import com.itgura.dto.AppResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

//    @Value("${app.dashboard.url}")
//    private String DashboardUrl;


    private final AuthenticationService authenticationService;

    public CustomOAuth2SuccessHandler( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Get the authenticated user's details from OAuth2
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


        // Process user login/registration in your AuthenticationService
        AuthenticationResponse authResponse = authenticationService.processOAuthPostLogin(oAuth2User);

//      //Crate AppResponse
        AppResponse<AuthenticationResponse> appResponse = AppResponse.ok(authResponse);
        // Send the AppResponse in the response body as JSON
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(appResponse)  // Serialize to JSON using ObjectMapper
        );
        response.getWriter().flush();
//        String dashboardUrl = DashboardUrl;  // Or your actual dashboard URL
//
//// Perform the redirect
//        response.sendRedirect(dashboardUrl);
    }
}