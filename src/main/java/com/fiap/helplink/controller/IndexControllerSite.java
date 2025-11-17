package com.fiap.helplink.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControllerSite {

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(HttpSession session) {

        if (session.getAttribute("usuarioLogado") == null)
            return "redirect:/auth/login";

        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}
