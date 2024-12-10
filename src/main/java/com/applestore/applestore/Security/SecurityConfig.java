package com.applestore.applestore.Security;

import com.applestore.applestore.Security.Oauth2.CustomOAuth2UserService;
import com.applestore.applestore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final CustomOAuth2UserService oAuth2UserService;

//
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, UserService userService, CustomOAuth2UserService oAuth2UserService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authz -> authz
//                                .requestMatchers("/", "/**")
                .requestMatchers("/login", "/register", "/register/admin", "/forgot-password", "/reset-password*", "/oauth2/**", "/static/**", "/assets/**", "/images/**", "/JS/**", "/user/", "/user/products", "/user/search", "/user/index", "/")
                .permitAll()
                .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                .requestMatchers("/user","/user/", "/user/**").hasRole("USER")
                .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(new CustomSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login(
                        oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                                .defaultSuccessUrl("/user/")
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
        ;

        return http.build();
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}