package com.ceatformacion.libropsi.controller;


import com.ceatformacion.libropsi.modell.Usuario;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Muestra el formulario de login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // Muestra el formulario de alta de usuario
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
                model.addAttribute("error", "El usuario ya existe");
                return "registro";
            }

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                usuario.setRol("ROLE_USER");
            }

            usuarioRepository.save(usuario);
            return "redirect:/login"; // redirige al login tras registrar
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "registro";
        }
    }
    // Opción alternativa para guardar usuarios (por si se usa desde otro formulario)
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                usuario.setRol("ROLE_USER");
            }

            usuarioRepository.save(usuario);
            return "redirect:/login";  // Redirige a login tras registro
        } else {
            model.addAttribute("error", "El usuario ya existe");
            model.addAttribute("usuario", usuario);  // Para mantener los datos ingresados
            return "registro";
        }
    }

}
