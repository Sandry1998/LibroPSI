package com.ceatformacion.libropsi;


import com.ceatformacion.libropsi.services.LibroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LibroService libroService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            if (libroService.obtenerTodos().isEmpty()) {
                response.sendRedirect("/libros/nuevo");
            } else {
                response.sendRedirect("/libros/todos");
            }
        } else {
            response.sendRedirect("/libros/todos");
        }
    }
}



