package com.ceatformacion.libropsi;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
    private CustomSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomSuccessHandler customSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Páginas públicas
                        .requestMatchers("/", "/registro", "/login", "/css/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registro", "/guardarUsuario").permitAll()

                        // Rutas solo para admin
                        .requestMatchers("/admin/**", "/editar/**", "/borrar/**", "/libros/cambiar-estado/**").hasRole("ADMIN")

                        // Rutas para usuarios y admin
                        .requestMatchers("/libros/**").hasAnyRole("USER", "ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());



        return http.build();
    }
}



