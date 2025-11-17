package com.fiap.helplink.repository;

import com.fiap.helplink.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    boolean existsByCnpjAndIdInstituicaoNot(String cnpj, Long idInstituicao);
}
