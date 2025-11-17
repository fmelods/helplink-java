package com.fiap.helplink.service;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.dto.ImpactoDTO;
import com.fiap.helplink.model.*;
import com.fiap.helplink.repository.*;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final DoacaoItemRepository doacaoItemRepository;
    private final ImpactoRepository impactoRepository;

    public DoacaoService(DoacaoRepository doacaoRepository,
                         UsuarioRepository usuarioRepository,
                         InstituicaoRepository instituicaoRepository,
                         DoacaoItemRepository doacaoItemRepository,
                         ImpactoRepository impactoRepository) {

        this.doacaoRepository = doacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.doacaoItemRepository = doacaoItemRepository;
        this.impactoRepository = impactoRepository;
    }

    // ======================================================
    // LISTAR TODAS (API)
    // ======================================================
    @Transactional(readOnly = true)
    public List<DoacaoDTO> listarTodas() {
        return doacaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ======================================================
    // COUNT POR USUÁRIO (DASHBOARD)
    // ======================================================
    @Transactional(readOnly = true)
    public long countByUsuario(Long usuarioId) {
        return doacaoRepository.countByUsuario_IdUsuario(usuarioId);
    }

    // ======================================================
    // BUSCAR DOAÇÃO
    // ======================================================
    @Transactional(readOnly = true)
    public DoacaoDTO buscar(Long id) {
        Doacao d = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

        d.getDoacaoItens().size();
        if (d.getImpacto() != null) d.getImpacto().getPontuacao();

        return toDTO(d);
    }

    // ======================================================
    // LISTAR POR USUÁRIO
    // ======================================================
    @Transactional(readOnly = true)
    public List<DoacaoDTO> listarPorUsuario(Long usuarioId) {
        return doacaoRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ======================================================
    // CRIAR DOAÇÃO
    // ======================================================
    @Transactional
    public DoacaoDTO criar(Long usuarioId, DoacaoCreateDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(dto.getIdInstituicao())
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));

        Doacao doacao = new Doacao();
        doacao.setUsuario(usuario);
        doacao.setInstituicao(instituicao);
        doacao.setStatus("ABERTA");
        doacao.setDtSolicitacao(LocalDateTime.now());

        // ITEM
        DoacaoItem item = new DoacaoItem();
        item.setDoacao(doacao);
        item.setDescricao(dto.getItemDescricao());
        item.setQtde(1.0);

        doacao.setDoacaoItens(new ArrayList<>(List.of(item)));

        // IMPACTO
        Impacto impacto = new Impacto();
        impacto.setDoacao(doacao);
        impacto.setPontuacao(10.0);
        impacto.setObservacao("impacto padrão");

        doacao.setImpacto(impacto);

        doacaoRepository.save(doacao);
        return toDTO(doacao);
    }

    // ======================================================
    // ATUALIZAR STATUS (API)
    // ======================================================
    @Transactional
    public DoacaoDTO atualizar(Long id, String status) {

        Doacao d = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

        String st = status.toUpperCase();

        if (!List.of("ABERTA", "CONCLUIDA", "CANCELADA").contains(st)) {
            throw new IllegalArgumentException("Status inválido");
        }

        d.setStatus(st);

        if (st.equals("CONCLUIDA")) {
            d.setDtConfirmacao(LocalDateTime.now());
        } else if (st.equals("ABERTA")) {
            d.setDtConfirmacao(null);
        }

        return toDTO(doacaoRepository.save(d));
    }

    // ======================================================
    // ATUALIZAR DOAÇÃO (SITE)
    // ======================================================
    @Transactional
    public DoacaoDTO atualizarDoacao(Long id, DoacaoDTO dto) {

        Doacao d = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

        if (dto.getInstituicaoId() != null) {
            Instituicao inst = instituicaoRepository.findById(dto.getInstituicaoId())
                    .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));
            d.setInstituicao(inst);
        }

        if (dto.getItemDescricao() != null && !dto.getItemDescricao().isBlank()) {

            if (d.getDoacaoItens().isEmpty()) {

                DoacaoItem novo = new DoacaoItem();
                novo.setDoacao(d);
                novo.setDescricao(dto.getItemDescricao());
                novo.setQtde(1.0);

                d.getDoacaoItens().add(novo);

            } else {
                d.getDoacaoItens().get(0).setDescricao(dto.getItemDescricao());
            }
        }

        return toDTO(doacaoRepository.save(d));
    }

    // ======================================================
    // EXCLUIR
    // ======================================================
    @Transactional
    public void excluir(Long id) {

        Doacao d = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

        d.getDoacaoItens().size();
        d.getAgendamentos().size();
        if (d.getImpacto() != null) d.getImpacto().getPontuacao();

        doacaoRepository.delete(d);
    }

    // ======================================================
    // DTO
    // ======================================================
    private DoacaoDTO toDTO(Doacao d) {

        DoacaoDTO dto = new DoacaoDTO();

        dto.setIdDoacao(d.getIdDoacao());
        dto.setStatus(d.getStatus());
        dto.setDtSolicitacao(d.getDtSolicitacao());
        dto.setDtConfirmacao(d.getDtConfirmacao());

        dto.setUsuarioId(d.getUsuario().getIdUsuario());
        dto.setUsuarioNome(d.getUsuario().getNome());

        dto.setInstituicaoId(d.getInstituicao().getIdInstituicao());
        dto.setInstituicaoNome(d.getInstituicao().getNome());

        if (!d.getDoacaoItens().isEmpty()) {
            dto.setItemDescricao(d.getDoacaoItens().get(0).getDescricao());
        }

        if (d.getImpacto() != null) {
            ImpactoDTO imp = new ImpactoDTO();
            imp.setPontuacao(d.getImpacto().getPontuacao());
            imp.setObservacao(d.getImpacto().getObservacao());
            dto.setImpacto(imp);
        }

        return dto;
    }
}
