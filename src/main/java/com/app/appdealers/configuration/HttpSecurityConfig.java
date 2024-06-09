package com.app.appdealers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.appdealers.configuration.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticacionFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(crsfConfig -> crsfConfig.disable())
            .sessionManagement(sessionMagConfig -> sessionMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authenticacionFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authConfig -> {
                authConfig.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                authConfig.requestMatchers(HttpMethod.POST, "/auth/register").permitAll();

                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/obtenerGrupoComercios").hasAuthority("ROLE_DEALER");
                authConfig.requestMatchers(HttpMethod.POST, "/api/v0/dealers/registrarVisita**").hasAuthority("ROLE_DEALER");
                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/cargarDataVisita**").hasAuthority("ROLE_DEALER");
                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/obtenerMetricasDealer**").hasAuthority("ROLE_DEALER");
                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/obtenerHistorialVisitas**").hasAuthority("ROLE_DEALER");

                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/obtenerDealers").hasAuthority("ROLE_ADMIN");
                authConfig.requestMatchers(HttpMethod.GET, "/api/v0/dealers/obtenerMetricasDealer**").hasAuthority("ROLE_ADMIN");

                authConfig.anyRequest().denyAll();
            });

        return http.build();
    }

}
