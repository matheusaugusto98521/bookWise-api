package br.com.mtech.bookWise.infra.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigurations{

    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //admin
                        .requestMatchers(HttpMethod.PUT, "/access/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/access/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/access/**").hasAnyRole("ADMIN", "USER")
                        //category
                        .requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                        //book
                        .requestMatchers(HttpMethod.POST, "/book/lend-book").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/book/return-book").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/book/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/book/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/book/**").hasRole("ADMIN")
                        //author
                        .requestMatchers(HttpMethod.POST, "/author/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/author/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/author/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
