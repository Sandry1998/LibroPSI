package com.ceatformacion.libropsi;



import com.ceatformacion.libropsi.services.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;
    @Autowired
    private CustomSuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/registro", "/login", "/css/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registro").permitAll()

                        // Rutas solo para ADMIN
                        .requestMatchers(
                                "/libros/nuevo",
                                "/libros/guardar",
                                "/libros/editar/**",
                                "/libros/eliminar/**",
                                "/historial/admin",
                                "/historial/editar"
                        ).hasRole("ADMIN")
                                .requestMatchers(
                                        "/libros/todos"
                                ).hasAnyRole("USER", "ADMIN")

// Rutas exclusivas de USER
                                .requestMatchers(
                                        "/libros/reservar/**",
                                        "/historial/usuario",
                                        "/historial/eliminar/**"
                                ).hasRole("USER")

                        // Rutas solo para USER
                        .requestMatchers(
                                "/libros/reservar/**",
                                "/historial/usuario",
                                "/historial/eliminar/**"
                        ).hasRole("USER")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}