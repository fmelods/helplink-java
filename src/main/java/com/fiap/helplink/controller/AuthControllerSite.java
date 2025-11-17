package com.fiap.helplink.controller;

import com.fiap.helplink.dto.UsuarioCreateDTO;
import com.fiap.helplink.dto.UsuarioLoginDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthControllerSite {

    private final UsuarioService usuarioService;
    private static final Logger log = LoggerFactory.getLogger(AuthControllerSite.class);

    // LOGIN GET
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String registrado,
                        @RequestParam(required = false) String logout,
                        Model model) {

        model.addAttribute("login", new UsuarioLoginDTO());

        if (registrado != null)
            model.addAttribute("msg", "Cadastro realizado com sucesso! Faça login.");

        if (logout != null)
            model.addAttribute("msg", "Você saiu da conta.");

        return "login";
    }

    // LOGIN POST
    @PostMapping("/login")
    public String logar(@ModelAttribute("login") UsuarioLoginDTO loginDTO,
                        HttpSession session,
                        Model model) {

        try {
            log.info("Tentando login com email {}", loginDTO.getEmail());

            Usuario usuario = usuarioService.buscarModel(loginDTO.getEmail());

            if (!usuarioService.senhaConfere(loginDTO.getSenha(), usuario.getSenha())) {
                model.addAttribute("erro", "Senha incorreta");
                return "login";
            }

            session.setAttribute("usuarioLogado", usuario);

            // 🔥 AGORA VAI PARA A HOME /index
            return "redirect:/index";

        } catch (Exception e) {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login";
        }
    }

    // REGISTRO GET
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new UsuarioCreateDTO());
        return "register";
    }

    // REGISTRO POST
    @PostMapping("/register")
    public String registrar(@Valid @ModelAttribute("usuario") UsuarioCreateDTO dto,
                            Model model) {

        try {
            usuarioService.criar(dto);
            return "redirect:/auth/login?registrado=true";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "register";
        }
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout=true";
    }
}
