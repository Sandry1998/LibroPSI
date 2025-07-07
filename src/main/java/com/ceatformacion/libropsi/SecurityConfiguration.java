package com.ceatformacion.libropsi;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.GET,"/","/index","/images/**").permitAll().requestMatchers(HttpMethod.GET,"/crud").hasAnyRole("ADMIN","USER").requestMatchers(HttpMethod.POST,"/crud").permitAll().requestMatchers(HttpMethod.GET,"/altaUsuario","/formulario").permitAll().requestMatchers(HttpMethod.POST,"/guardarUsuario").permitAll().requestMatchers("/editar/**","/borrar/**").permitAll().anyRequest().authenticated()).formLogin(form->form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/crud",true).permitAll()).logout(LogoutConfigurer::permitAll);

        return http.build();
    }


}
