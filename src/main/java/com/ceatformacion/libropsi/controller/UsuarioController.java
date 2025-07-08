package com.ceatformacion.libropsi.controller;

import com.ceatformacion.libropsi.modell.Usuario;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String mostrarlogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @GetMapping("/altaUsuario")
    public String altaUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "altaUsuario";
    }

    @PostMapping("/registro")
    public String addUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            // Verificar si el usuario ya existe
            if (usuarioRepository.findByUsername(usuario.getUsuario()).isPresent()) {
                model.addAttribute("error", "El usuario ya existe");
                return "altaUsuario";
            }

            // Encriptar contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            // Asignar rol por defecto si no tiene
            if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
                usuario.setRol("USER");
            }

            usuarioRepository.save(usuario);
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "altaUsuario";
        }
    }

    // Método alternativo más completo
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioRepository.findByUsername(usuario.getUsuario()).isEmpty()) {
            Usuario user = new Usuario();
            user.setUsuario(usuario.getUsuario());
            user.setPassword(passwordEncoder.encode(usuario.getPassword()));
            user.setRol(usuario.getRol() != null ? usuario.getRol() : "USER");
            usuarioRepository.save(user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario ya existente");
            return "altaUsuario";
        }
    }
}
