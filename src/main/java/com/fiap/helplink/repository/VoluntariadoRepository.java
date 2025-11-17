package com.fiap.helplink.repository;

import com.fiap.helplink.model.Voluntariado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntariadoRepository extends JpaRepository<Voluntariado, Long> {

    List<Voluntariado> findByUsuario_IdUsuario(Long usuarioId);

    List<Voluntariado> findByInstituicao_IdInstituicao(Long instituicaoId);

    // DASHBOARD
    long countByUsuario_IdUsuario(Long usuarioId);
}
