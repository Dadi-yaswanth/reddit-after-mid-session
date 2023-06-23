package com.reddit.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity()
@RequiredArgsConstructor()
public class SecurityConfiguration {
    public static final String[] ALLOWED_PATHS = new String[]
            {
                    "/",
                    "/search",
                    "/home",
                    "/login",
                    "/newRegister",
                    "/addUser",
                    "/search/**",
                    "/{type}/{username}/comments/{id}",
                    "/view-community/**",
                    "/view-profile",
                    "/images/**",
                    "/uploads/**",
                    "/media/**"
            };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                        request
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(ALLOWED_PATHS).permitAll()
                                .anyRequest().authenticated()
                ).userDetailsService(userDetailServiceJPA)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        http
                .formLogin(login->
                        login
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home",true))
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/home"))
                .exceptionHandling(exception->
                        exception
                                .accessDeniedPage("/access-denied"));

        return http.build();
    }
    private final UserDetailServiceJPA userDetailServiceJPA;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}