package com.fiap.helplink.repository;

import com.fiap.helplink.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Lista todos os agendamentos de uma doação específica
    List<Agendamento> findByDoacao_IdDoacao(Long doacaoId);
}
