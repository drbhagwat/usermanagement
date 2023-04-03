package com.example.UserManagement.security;

import com.example.UserManagement.service.UserService;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Data
public class SecurityConfig {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    final String LOGIN = "/login";
    final String[] publicUrls = {"/", "/index", "/logout", LOGIN, "/register",
        "/css/**",
        "/js/**", "/images/**"};
    final String viewUsersUrl = "/api/v1/users";
    final String secret = "deepsecretoverridingspringdefault";
    final String[] cookies = {"remember-me", "JSESSIONID"};
    final int rememberMeValidityInSeconds = (int) TimeUnit.DAYS.toSeconds(21);

    http.authenticationProvider(daoAuthenticationProvider());
    return
        http.authorizeHttpRequests(
                authorize -> authorize
                    .requestMatchers(publicUrls).permitAll()
                    .anyRequest().authenticated())
            .formLogin()
            .loginPage(LOGIN)
            .defaultSuccessUrl(viewUsersUrl, true)
            .and()
            .rememberMe()
            .tokenValiditySeconds(rememberMeValidityInSeconds)
            .key(secret)
            .and()
            .logout()
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .logoutSuccessUrl(LOGIN)
            .deleteCookies(cookies)
            .and().build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider =
        new DaoAuthenticationProvider();
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    daoAuthenticationProvider.setUserDetailsService(userService);
    return daoAuthenticationProvider;
  }
}