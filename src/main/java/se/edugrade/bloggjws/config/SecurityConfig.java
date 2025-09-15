package se.edugrade.bloggjws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import se.edugrade.bloggjws.converters.JwtAuthConverter;

@Configuration
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                //De två endpoints som enbart kräver att man är authenticated.
                                .requestMatchers("/api/v2/posts", "/api/v2/post/**")
                                .authenticated()

                                //Endpointen som enbart kräver rollen user
                                .requestMatchers("/api/v2/newpost")
                                .hasRole("myClient_USER")

                                //Endpoints som kräver user OCH rätt roll
                                .requestMatchers(HttpMethod.PUT, "/api/v2/updatepost/**").hasAnyRole("myClient_USER", "myClient_ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/api/v2/deletepost", "/api/v2/deletepost/**")
                                .hasAnyRole("myClient_USER", "myClient_ADMIN")

                                //Admin endpoint
                                .requestMatchers(HttpMethod.GET,"/api/v2/count")
                                .hasRole("myClient_ADMIN")

                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                        );
        return http.build();
    }
}
