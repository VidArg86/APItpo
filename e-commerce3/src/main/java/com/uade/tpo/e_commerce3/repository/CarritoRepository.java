package com.uade.tpo.e_commerce3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.e_commerce3.model.Carrito;

@Repository
public interface CarritoRepository  extends JpaRepository<Carrito, Long> {
    //findAll() ya está implementado por JpaRepository, no es necesario definirlo aquí
    // select * from productos
}
