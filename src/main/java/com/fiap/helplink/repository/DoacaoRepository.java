package com.fiap.helplink.repository;

import com.fiap.helplink.model.Doacao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {

    @Override
    @EntityGraph(attributePaths = { "usuario", "instituicao" })
    List<Doacao> findAll();

    @EntityGraph(attributePaths = { "usuario", "instituicao" })
    List<Doacao> findByStatus(String status);

    List<Doacao> findByUsuario_IdUsuario(Long usuarioId);

    long countByUsuario_IdUsuario(Long usuarioId);
}
