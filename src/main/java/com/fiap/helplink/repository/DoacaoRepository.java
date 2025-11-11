package com.fiap.helplink.repository;

import com.fiap.helplink.model.Doacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
    Page<Doacao> findByUsuarioIdUsuario(Long usuarioId, Pageable pageable);
    Page<Doacao> findByInstituicaoIdInstituicao(Long instituicaoId, Pageable pageable);
    List<Doacao> findByStatus(String status);
}
