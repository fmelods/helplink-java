package com.fiap.helplink.repository;

import com.fiap.helplink.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByDoacaoIdDoacao(Long doacaoId);
}
