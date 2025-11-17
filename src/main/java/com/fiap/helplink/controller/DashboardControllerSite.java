package com.fiap.helplink.controller;

import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.DoacaoService;
import com.fiap.helplink.service.InstituicaoService;
import com.fiap.helplink.service.ItemService;
import com.fiap.helplink.service.VoluntariadoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardControllerSite {

    private final ItemService itemService;
    private final DoacaoService doacaoService;
    private final InstituicaoService instituicaoService;
    private final VoluntariadoService voluntariadoService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        Long id = usuario.getIdUsuario();

        model.addAttribute("usuario", usuario);
        model.addAttribute("totalItens", itemService.countByUsuario(id));
        model.addAttribute("totalDoacoes", doacaoService.countByUsuario(id));
        model.addAttribute("totalVoluntariados", voluntariadoService.countByUsuario(id));
        model.addAttribute("totalInstituicoes", instituicaoService.countAll());

        return "dashboard";
    }
}
