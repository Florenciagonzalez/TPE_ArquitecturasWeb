package com.tpe.usuarios.repository;


import com.tpe.usuarios.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, String> {
}
