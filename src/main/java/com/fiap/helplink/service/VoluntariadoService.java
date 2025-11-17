package com.fiap.helplink.service;

import com.fiap.helplink.dto.VoluntariadoDTO;
import com.fiap.helplink.model.Instituicao;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.model.Voluntariado;
import com.fiap.helplink.repository.InstituicaoRepository;
import com.fiap.helplink.repository.UsuarioRepository;
import com.fiap.helplink.repository.VoluntariadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoluntariadoService {

    private final VoluntariadoRepository voluntariadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstituicaoRepository instituicaoRepository;

    public VoluntariadoService(
            VoluntariadoRepository voluntariadoRepository,
            UsuarioRepository usuarioRepository,
            InstituicaoRepository instituicaoRepository
    ) {
        this.voluntariadoRepository = voluntariadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    // LISTAR POR USUÁRIO
    @Transactional(readOnly = true)
    public List<VoluntariadoDTO> listarPorUsuario(Long usuarioId) {
        return voluntariadoRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // CONTAR PARA DASHBOARD
    @Transactional(readOnly = true)
    public long countByUsuario(Long usuarioId) {
        return voluntariadoRepository.countByUsuario_IdUsuario(usuarioId);
    }

    // LISTAR POR INSTITUIÇÃO
    @Transactional(readOnly = true)
    public List<VoluntariadoDTO> listarPorInstituicao(Long instituicaoId) {
        return voluntariadoRepository.findByInstituicao_IdInstituicao(instituicaoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ======================================
    //              CRIAR NOVO
    // ======================================
    @Transactional
    public void criar(Long usuarioId, VoluntariadoDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Instituicao instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

        Voluntariado novo = new Voluntariado();
        novo.setHabilidades(dto.getHabilidades());
        novo.setHorasDedicadas(dto.getHorasDedicadas());

        // LocalDate (DTO) -> LocalDateTime (entidade)
        LocalDateTime dataCompleta = dto.getDtParticipacao().atStartOfDay();
        novo.setDtParticipacao(dataCompleta);

        novo.setUsuario(usuario);
        novo.setInstituicao(instituicao);

        voluntariadoRepository.save(novo);
    }

    // ======================================
    //              ATUALIZAR
    // ======================================
    @Transactional
    public VoluntariadoDTO atualizar(Long id, VoluntariadoDTO dto) {

        Voluntariado existente = voluntariadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntariado não encontrado"));

        existente.setHabilidades(dto.getHabilidades());
        existente.setHorasDedicadas(dto.getHorasDedicadas());

        if (dto.getDtParticipacao() != null) {
            existente.setDtParticipacao(dto.getDtParticipacao().atStartOfDay());
        }

        // se trocar a instituição
        if (dto.getInstituicaoId() != null &&
                !dto.getInstituicaoId().equals(existente.getInstituicao().getIdInstituicao())) {

            Instituicao instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                    .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

            existente.setInstituicao(instituicao);
        }

        Voluntariado salvo = voluntariadoRepository.save(existente);
        return toDTO(salvo);
    }

    // BUSCAR POR ID
    @Transactional(readOnly = true)
    public VoluntariadoDTO buscar(Long id) {
        Voluntariado v = voluntariadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voluntariado não encontrado"));
        return toDTO(v);
    }

    // DELETAR
    @Transactional
    public void excluir(Long id) {
        voluntariadoRepository.deleteById(id);
    }

    // ======================================
    //            CONVERSÃO PARA DTO
    // ======================================
    private VoluntariadoDTO toDTO(Voluntariado v) {
        VoluntariadoDTO dto = new VoluntariadoDTO();
        dto.setIdVoluntariado(v.getIdVoluntariado());
        dto.setHabilidades(v.getHabilidades());
        dto.setHorasDedicadas(v.getHorasDedicadas());

        if (v.getDtParticipacao() != null) {
            // LocalDateTime -> LocalDate
            dto.setDtParticipacao(v.getDtParticipacao().toLocalDate());
        }

        dto.setUsuarioId(v.getUsuario().getIdUsuario());
        dto.setUsuarioNome(v.getUsuario().getNome());
        dto.setInstituicaoId(v.getInstituicao().getIdInstituicao());
        dto.setInstituicaoNome(v.getInstituicao().getNome());
        return dto;
    }
}
