package com.uade.tpo.e_commerce3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.e_commerce3.model.Producto;
import com.uade.tpo.e_commerce3.model.enums.Categoria;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Categoria categoria);
}