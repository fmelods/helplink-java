package com.fiap.helplink.repository;

import com.fiap.helplink.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUsuarioIdUsuario(Long usuarioId);
    Page<Item> findByCategoriaIdCategoria(Long categoriaId, Pageable pageable);
}
