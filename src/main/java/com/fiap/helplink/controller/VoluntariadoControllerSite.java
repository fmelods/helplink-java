package com.fiap.helplink.controller;

import com.fiap.helplink.dto.VoluntariadoDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.InstituicaoService;
import com.fiap.helplink.service.VoluntariadoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voluntariado")
public class VoluntariadoControllerSite {

    private final VoluntariadoService voluntariadoService;
    private final InstituicaoService instituicaoService;

    private Usuario validarSessao(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("voluntariados",
                voluntariadoService.listarPorUsuario(usuario.getIdUsuario()));

        return "voluntariado/list";
    }

    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {
        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("instituicoes", instituicaoService.listar());
        model.addAttribute("voluntariado", new VoluntariadoDTO());

        return "voluntariado/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("voluntariado") VoluntariadoDTO dto,
                         HttpSession session, Model model) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        if (dto.getInstituicaoId() == null) {
            model.addAttribute("erro", "Selecione uma instituição.");
            model.addAttribute("instituicoes", instituicaoService.listar());
            return "voluntariado/form";
        }

        if (dto.getIdVoluntariado() == null) {
            voluntariadoService.criar(usuario.getIdUsuario(), dto);
        } else {
            voluntariadoService.atualizar(dto.getIdVoluntariado(), dto);
        }

        return "redirect:/voluntariado";
    }

    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id,
                           Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        VoluntariadoDTO dto = voluntariadoService.buscar(id);
        model.addAttribute("voluntariado", dto);

        return "voluntariado/detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id,
                         Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        VoluntariadoDTO dto = voluntariadoService.buscar(id);

        model.addAttribute("voluntariado", dto);
        model.addAttribute("instituicoes", instituicaoService.listar());

        return "voluntariado/form";
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        voluntariadoService.excluir(id);
        return "redirect:/voluntariado";
    }
}
