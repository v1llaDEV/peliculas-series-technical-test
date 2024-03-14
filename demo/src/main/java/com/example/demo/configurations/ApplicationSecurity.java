package com.example.demo.configurations;

import com.example.demo.enums.Roles;
import com.example.demo.jwt.JwtTokenFilter;
import com.example.demo.repositories.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Configuration
public class ApplicationSecurity {

    private final EmpleadoRepository empleadoRepository;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return empleadoRepository.findByEmail(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("Usuario con email" + username + " no encontrado"));
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/auth/login", "/docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/api/v1/empleados/**").hasAnyAuthority(Roles.ADMIN.name())
                .antMatchers("/api/v1/roles/**").hasAnyAuthority(Roles.ADMIN.name())
                .antMatchers("/api/v1/tiposprogramador/**").hasAnyAuthority(Roles.ADMIN.name())
                .antMatchers("/api/v1/puntuaciones/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.EMPLEADO.name())
                .antMatchers("/api/v1/peliculasseries/**").hasAnyAuthority(Roles.ADMIN.name(),
                                                                        Roles.EMPLEADO.name(), Roles.AMIGO.name())
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                );

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
