package com.fiap.helplink.controller;

import com.fiap.helplink.dto.InstituicaoDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.InstituicaoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/instituicoes")
public class InstituicaoControllerSite {

    private final InstituicaoService instituicaoService;

    private Usuario validarSessao(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    // LISTA
    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (validarSessao(session) == null) return "redirect:/auth/login";

        model.addAttribute("instituicoes", instituicaoService.listar());
        return "instituicoes/list";
    }

    // FORM NOVA
    @GetMapping("/nova")
    public String nova(Model model, HttpSession session) {
        if (validarSessao(session) == null) return "redirect:/auth/login";

        model.addAttribute("inst", new InstituicaoDTO());
        return "instituicoes/form";
    }

    // FORM EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
                         RedirectAttributes ra, HttpSession session) {

        if (validarSessao(session) == null) return "redirect:/auth/login";

        InstituicaoDTO dto = instituicaoService.buscar(id);
        model.addAttribute("inst", dto);
        return "instituicoes/form";
    }

    // SALVAR (NOVO / EDITAR)
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("inst") InstituicaoDTO dto,
                         RedirectAttributes ra,
                         HttpSession session) {

        if (validarSessao(session) == null) return "redirect:/auth/login";

        if (dto.getIdInstituicao() == null) {
            instituicaoService.criar(dto);
            ra.addFlashAttribute("sucesso", "Instituição cadastrada com sucesso.");
        } else {
            instituicaoService.atualizar(dto.getIdInstituicao(), dto);
            ra.addFlashAttribute("sucesso", "Instituição atualizada com sucesso.");
        }

        return "redirect:/instituicoes";
    }

    // DETALHES
    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model,
                           HttpSession session) {

        if (validarSessao(session) == null) return "redirect:/auth/login";

        model.addAttribute("inst", instituicaoService.buscar(id));
        return "instituicoes/detalhes";
    }

    // EXCLUIR
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id,
                          RedirectAttributes ra,
                          HttpSession session) {

        if (validarSessao(session) == null) return "redirect:/auth/login";

        try {
            instituicaoService.deletar(id);
            ra.addFlashAttribute("sucesso", "Instituição excluída com sucesso.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("erro",
                    "Não é possível excluir esta instituição, pois existem registros vinculados.");
        }
        return "redirect:/instituicoes";
    }
}
