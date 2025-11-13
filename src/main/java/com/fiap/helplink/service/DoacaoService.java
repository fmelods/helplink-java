package com.fiap.helplink.service;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.model.*;
import com.fiap.helplink.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final ItemRepository itemRepository;
    private final DoacaoItemRepository doacaoItemRepository;
    private final ImpactoRepository impactoRepository;

    // ============================
    // LISTAR TODAS (CORRIGIDO)
    // ============================
    @Transactional(readOnly = true)
    public List<DoacaoDTO> listarTodas() {
        // Agora findAll usa @EntityGraph automaticamente
        return doacaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================
    // BUSCAR POR ID (CORRIGIDO)
    // ============================
    @Transactional(readOnly = true)
    public DoacaoDTO encontrarPorId(Long id) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada com ID: " + id));

        // força carregar usuário e instituição antes de fechar a sessão
        doacao.getUsuario().getNome();
        doacao.getInstituicao().getNome();

        return toDTO(doacao);
    }

    // ============================
    // CRIAR DOAÇÃO
    // ============================
    @Transactional
    public DoacaoDTO criar(Long usuarioId, DoacaoCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        Instituicao instituicao = instituicaoRepository.findById(dto.getIdInstituicao())
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + dto.getIdInstituicao()));

        Doacao doacao = Doacao.builder()
                .usuario(usuario)
                .instituicao(instituicao)
                .status("ABERTA")
                .dtSolicitacao(LocalDateTime.now())
                .build();

        // segurança extra contra listas nulas
        if (doacao.getDoacaoItens() == null) {
            doacao.setDoacaoItens(new java.util.ArrayList<>());
        }

        // adicionar itens
        dto.getIdItens().forEach(idItem -> {
            Item item = itemRepository.findById(idItem)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com ID: " + idItem));

            DoacaoItem di = DoacaoItem.builder()
                    .doacao(doacao)
                    .item(item)
                    .qtde(1.0)
                    .build();

            doacao.getDoacaoItens().add(di);
        });

        // impacto padrão
        Impacto impacto = Impacto.builder()
                .doacao(doacao)
                .pontuacao(10.0)
                .observacao("impacto padrão")
                .build();
        doacao.setImpacto(impacto);

        Doacao salva = doacaoRepository.save(doacao);
        return toDTO(salva);
    }

    // ============================
    // ATUALIZAR STATUS
    // ============================
    @Transactional
    public DoacaoDTO atualizar(Long id, String novoStatus) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada com ID: " + id));

        String statusUpper = novoStatus.toUpperCase();
        if (!List.of("ABERTA", "CONCLUIDA", "CANCELADA").contains(statusUpper)) {
            throw new IllegalArgumentException("Status inválido. Use ABERTA, CONCLUIDA ou CANCELADA.");
        }

        doacao.setStatus(statusUpper);
        if ("CONCLUIDA".equals(statusUpper)) {
            doacao.setDtConfirmacao(LocalDateTime.now());
        } else if ("ABERTA".equals(statusUpper)) {
            doacao.setDtConfirmacao(null);
        }

        return toDTO(doacaoRepository.save(doacao));
    }

    // ============================
    // DELETAR
    // ============================
    @Transactional
    public void deletar(Long id) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada com ID: " + id));

        // força carregar associações para permitir cascade delete
        doacao.getDoacaoItens().size();
        doacao.getAgendamentos().size();
        if (doacao.getImpacto() != null) {
            doacao.getImpacto().getPontuacao();
        }

        doacaoRepository.delete(doacao);
    }

    // ============================
    // CONVERSÃO PARA DTO
    // ============================
    private DoacaoDTO toDTO(Doacao d) {
        return DoacaoDTO.builder()
                .idDoacao(d.getIdDoacao())
                .status(d.getStatus())
                .dtSolicitacao(d.getDtSolicitacao())
                .dtConfirmacao(d.getDtConfirmacao())
                .usuarioId(d.getUsuario().getIdUsuario())
                .usuarioNome(d.getUsuario().getNome())
                .instituicaoId(d.getInstituicao().getIdInstituicao())
                .instituicaoNome(d.getInstituicao().getNome())
                .impacto(null) // pode mapear depois se quiser
                .build();
    }
}
