package com.fiap.helplink.service;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.model.*;
import com.fiap.helplink.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoacaoService {

    @Autowired private DoacaoRepository doacaoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private InstituicaoRepository instituicaoRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private DoacaoItemRepository doacaoItemRepository; // não é usado diretamente, mas pode ser útil
    @Autowired private ImpactoRepository impactoRepository;        // idem

    // LISTAR TODAS
    @Transactional(readOnly = true)
    public List<DoacaoDTO> listarTodas() {
        return doacaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public DoacaoDTO encontrarPorId(Long id) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada com ID: " + id));
        return toDTO(doacao);
    }

    // CRIAR DOAÇÃO
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

        // segurança extra: se por algum motivo vier nulo, inicializa
        if (doacao.getDoacaoItens() == null) {
            doacao.setDoacaoItens(new java.util.ArrayList<>());
        }

        // adicionar itens (cascade ALL salva junto)
        dto.getIdItens().forEach(idItem -> {
            Item item = itemRepository.findById(idItem)
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com ID: " + idItem));

            DoacaoItem doacaoItem = DoacaoItem.builder()
                    .doacao(doacao)
                    .item(item)
                    .qtde(1.0) // evita ORA-01400 em QTDE
                    .build();

            doacao.getDoacaoItens().add(doacaoItem);
        });

        // impacto padrão (cascade ALL salva junto)
        Impacto impacto = Impacto.builder()
                .doacao(doacao)
                .pontuacao(10.0)
                .observacao("impacto padrão")
                .build();
        doacao.setImpacto(impacto);

        Doacao doacaoSalva = doacaoRepository.save(doacao);
        return toDTO(doacaoSalva);
    }

    // ATUALIZAR STATUS
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
            // opcional: ao voltar para ABERTA, zera confirmação
            doacao.setDtConfirmacao(null);
        }

        return toDTO(doacaoRepository.save(doacao));
    }

    // DELETAR DOAÇÃO
    @Transactional
    public void deletar(Long id) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada com ID: " + id));

        // força carregamento das coleções/associações para o Hibernate aplicar o cascade/orphanRemoval
        doacao.getDoacaoItens().size();
        doacao.getAgendamentos().size();
        if (doacao.getImpacto() != null) {
            doacao.getImpacto().getPontuacao();
        }

        doacaoRepository.delete(doacao);
    }

    // CONVERSÃO PARA DTO (itens/impacto podem ser mapeados depois, se desejar)
    private DoacaoDTO toDTO(Doacao doacao) {
        return DoacaoDTO.builder()
                .idDoacao(doacao.getIdDoacao())
                .status(doacao.getStatus())
                .dtSolicitacao(doacao.getDtSolicitacao())
                .dtConfirmacao(doacao.getDtConfirmacao())
                .usuarioId(doacao.getUsuario().getIdUsuario())
                .usuarioNome(doacao.getUsuario().getNome())
                .instituicaoId(doacao.getInstituicao().getIdInstituicao())
                .instituicaoNome(doacao.getInstituicao().getNome())
                .impacto(null) // mantenho null para não expandir aqui
                .build();
    }
}
