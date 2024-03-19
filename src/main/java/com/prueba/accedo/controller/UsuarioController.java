package com.prueba.accedo.controller;

import com.prueba.accedo.entity.Usuario;
import com.prueba.accedo.repository.UsuarioRepository;
import com.prueba.accedo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/login?error")
    public String showLoginErrorPage() {
        return "loginError";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (usuarioService.authenticateUser(email, password)) {
            modelAndView.setViewName("redirect:/api/pokemons");
        } else {
            modelAndView.setViewName("redirect:/login");
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registerUser(@RequestBody Usuario usuario, HttpServletResponse response) {
        try {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El usuario ya está registrado");
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuarioRepository.save(usuario);
                String loginUrl = "/api/users/login";
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Usuario registrado correctamente. Redirigiendo a la página de inicio de sesión: " + loginUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud");
        }
    }
}