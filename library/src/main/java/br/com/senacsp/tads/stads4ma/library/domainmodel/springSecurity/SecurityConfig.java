package br.com.senacsp.tads.stads4ma.library.domainmodel.springSecurity;

import com.querydsl.core.annotations.Config;
import org.springframework.context.annotation.Bean;

@Config
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    @Bean
    public SecuritFilterChain securitFilterChain(HttpSecurity http) throws{
        return
                http
                        .csrf(csrf -> csrf
                                .ignoringRequestMatchers("/console/**")
                                .disable()                        )
                        .headers(
                                headers -> headers
                        .frameOptions( frame -> frame.disable()))
                        .autorizeHttpRequests(
                                auth -> auth
                                        .requestMatchers(
                                                "/auth/**",
                                                "/auth",
                                                "/v3/api-docs",
                                                "/swagger-ui/**"
                                                "/console/**"
                                        )
                        )
    }
}
