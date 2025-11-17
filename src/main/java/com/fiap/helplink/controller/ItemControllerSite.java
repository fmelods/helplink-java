package com.fiap.helplink.controller;

import com.fiap.helplink.dto.ItemDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.service.CategoriaService;
import com.fiap.helplink.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itens")
public class ItemControllerSite {

    private final ItemService itemService;
    private final CategoriaService categoriaService;

    private Usuario validarSessao(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    // LISTAR
    @GetMapping
    public String listar(Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("itens",
                itemService.listarPorUsuario(usuario.getIdUsuario()));

        return "itens/list";
    }

    // CADASTRAR NOVO
    @GetMapping("/novo")
    public String novo(Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("item", new ItemDTO());

        return "itens/form";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        ItemDTO item = itemService.buscar(id);

        model.addAttribute("item", item);
        model.addAttribute("categorias", categoriaService.listar());

        return "itens/form";
    }

    // SALVAR / ATUALIZAR
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("item") ItemDTO dto,
                         HttpSession session,
                         Model model) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        // Validação
        if (dto.getCategoriaId() == null) {
            model.addAttribute("erro", "Selecione uma categoria.");
            model.addAttribute("categorias", categoriaService.listar());
            return "itens/form";
        }

        // NOVO ITEM
        if (dto.getIdItem() == null) {

            itemService.criar(usuario.getIdUsuario(), dto);

        } else { // ATUALIZAÇÃO

            itemService.atualizar(
                    dto.getIdItem(),          // ID do item
                    usuario.getIdUsuario(),   // ID do usuário logado
                    dto                       // Dados do formulário
            );
        }

        return "redirect:/itens";
    }

    // DETALHES
    @GetMapping("/{id}")
    public String detalhes(@PathVariable Long id,
                           Model model,
                           HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        model.addAttribute("item", itemService.buscar(id));
        return "itens/detalhes";
    }

    // EXCLUIR
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {

        Usuario usuario = validarSessao(session);
        if (usuario == null) return "redirect:/auth/login";

        itemService.excluir(id);

        return "redirect:/itens";
    }
}
