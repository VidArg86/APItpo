package com.uade.tpo.e_commerce3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.e_commerce3.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Busca productos que tengan una categoria con ese id
    @Query("SELECT p FROM Producto p JOIN p.categorias c WHERE c.id = :categoriaId")
    List<Producto> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    List<Producto> findAllByOrderByNombreAsc();
}
