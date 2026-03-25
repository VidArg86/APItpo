package com.uade.tpo.e_commerce3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.e_commerce3.model.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //findAll() ya está implementado por JpaRepository, no es necesario definirlo aquí
    // select * from usuario
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
