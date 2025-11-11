package com.fiap.helplink.repository;

import com.fiap.helplink.model.Impacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImpactoRepository extends JpaRepository<Impacto, Long> {
    Optional<Impacto> findByDoacaoIdDoacao(Long doacaoId);
}
