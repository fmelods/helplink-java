package com.fiap.helplink.repository;

import com.fiap.helplink.model.Voluntariado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntariadoRepository extends JpaRepository<Voluntariado, Long> {
    List<Voluntariado> findByUsuarioIdUsuario(Long usuarioId);
    List<Voluntariado> findByInstituicaoIdInstituicao(Long instituicaoId);
}
