package com.mynt.banking.config;

import com.mynt.banking.auth.AuthenticationFailureHandler;
import com.mynt.banking.auth.AuthenticationFilter;
import com.mynt.banking.auth.ForbiddenAccessHandler;
import com.mynt.banking.auth.JwtUserDetails;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/auth/sdk**",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
    };

    private final AuthenticationFilter jwtAuthFilter;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final ForbiddenAccessHandler forbiddenAccessHandler;
    private final UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService(CurrencyCloudRepository currencyCloudRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new JwtUserDetails(user, currencyCloudRepository);
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(req -> req
                        .requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .requestMatchers("/api/v1/currency-cloud/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/demo-controller/**").hasRole("USER")
                        .requestMatchers("/api/v1/flutterwave/**").hasRole("USER")
                        .requestMatchers("/api/v1/transaction/**").hasRole("USER")
                        .requestMatchers("api/v1/flutterwave/**").hasRole("USER")
                        .requestMatchers("api/v1/rates/**").hasRole("USER")
                        .requestMatchers("/api/v1/users/**").hasRole("USER")
                        .requestMatchers("/api/v1/beneficiary/**").hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(forbiddenAccessHandler)
                        .authenticationEntryPoint(authenticationFailureHandler)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}
