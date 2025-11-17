package com.fiap.helplink.repository;

import com.fiap.helplink.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // LISTAR ITENS DO USUÁRIO
    List<Item> findByUsuario_IdUsuario(Long usuarioId);

    // PAGINAÇÃO POR CATEGORIA (API)
    Page<Item> findByCategoria_IdCategoria(Long categoriaId, Pageable pageable);

    // CONTAGEM PARA DASHBOARD
    long countByUsuario_IdUsuario(Long usuarioId);
}
