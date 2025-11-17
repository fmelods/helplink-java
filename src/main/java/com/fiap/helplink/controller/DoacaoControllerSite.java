package com.fiap.helplink.controller;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.DoacaoService;
import com.fiap.helplink.service.InstituicaoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doacoes")
public class DoacaoControllerSite {

    private final DoacaoService doacaoService;
    private final InstituicaoService instituicaoService;

    private Usuario validarSessao(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    // ============================================================
    // LISTAR DOAÇÕES DO USUÁRIO
    // ============================================================
    @GetMapping
    public String listar(Model model, HttpSession session) {
        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("doacoes", doacaoService.listarPorUsuario(usuario.getIdUsuario()));
        return "doacoes/list";
    }

    // ============================================================
    // NOVA DOAÇÃO
    // ============================================================
    @GetMapping("/nova")
    public String nova(Model model, HttpSession session) {
        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("instituicoes", instituicaoService.listar());
        model.addAttribute("form", new DoacaoCreateDTO());
        return "doacoes/form";
    }

    // ============================================================
    // SALVAR NOVA DOAÇÃO
    // ============================================================
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("form") DoacaoCreateDTO dto,
                         HttpSession session,
                         Model model) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        // Validação do campo itemDescrição
        if (dto.getItemDescricao() == null || dto.getItemDescricao().isBlank()) {
            model.addAttribute("erro", "Descreva o item que deseja doar.");
            model.addAttribute("instituicoes", instituicaoService.listar());
            return "doacoes/form";
        }

        doacaoService.criar(usuario.getIdUsuario(), dto);
        return "redirect:/doacoes";
    }

    // ============================================================
    // DETALHES DA DOAÇÃO
    // ============================================================
    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id, Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("doacao", doacaoService.buscar(id));
        return "doacoes/detalhes";
    }

    // ============================================================
    // EDITAR
    // ============================================================
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        DoacaoDTO dto = doacaoService.buscar(id);

        model.addAttribute("form", dto);
        model.addAttribute("instituicoes", instituicaoService.listar());

        return "doacoes/form-editar";
    }

    // ============================================================
    // ATUALIZAR DOAÇÃO
    // ============================================================
    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("form") DoacaoDTO dto,
                            HttpSession session,
                            Model model) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        // Validação
        if (dto.getItemDescricao() == null || dto.getItemDescricao().isBlank()) {
            model.addAttribute("erro", "A descrição do item não pode estar vazia.");
            model.addAttribute("instituicoes", instituicaoService.listar());
            return "doacoes/form-editar";
        }

        doacaoService.atualizarDoacao(id, dto);
        return "redirect:/doacoes";
    }

    // ============================================================
    // EXCLUIR
    // ============================================================
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        doacaoService.excluir(id);
        return "redirect:/doacoes";
    }
}
