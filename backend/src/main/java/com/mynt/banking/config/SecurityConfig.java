package com.mynt.banking.config;

import com.mynt.banking.auth.AuthenticationFailureHandler;
import com.mynt.banking.auth.AuthenticationFilter;
import com.mynt.banking.auth.ForbiddenAccessHandler;
import com.mynt.banking.auth.MyntUserDetails;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.user.Role;
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

    private final CurrencyCloudRepository currencyCloudRepository;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/auth/sdk**",
            "/v3/api-docs/**",
            "/actuator/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/api/v1/flutterwave/**"
    };

    private static final String[] PROTECTED_API_URL = {
            "/api/v1/flutterwave/**",
            "/api/v1/transaction/**",
            "/api/v1/rates/**",
            "/api/v1/users/**",
            "/api/v1/beneficiary/**",
            "/api/v1/transfer/**",
            "/api/v1/payments/**",
            "/api/v1/conversions/**"
    };

    private final AuthenticationFilter jwtAuthFilter;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final ForbiddenAccessHandler forbiddenAccessHandler;
    private final UserRepository userRepository;


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return (username) -> {
//            User user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmail(),
//                    user.getPassword(),
//                    user.getRole().getAuthorities()
//            );
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String uuid = currencyCloudRepository.findByUser(user)
                    .map(CurrencyCloudEntity::getUuid)
                    .orElseThrow(() -> new UsernameNotFoundException("Currency cloud UUID not found for user"));

            return new MyntUserDetails(
                    user.getEmail(),
                    user.getPassword(),
                    uuid,
                    user.getRole()
            );
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
                        .requestMatchers(PROTECTED_API_URL)
                        .hasAnyRole("USER", "ADMIN", "MANAGER")
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
