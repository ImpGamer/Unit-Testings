package com.gestion.test.repository;

import com.gestion.test.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {
    @Query(value = "SELECT p FROM Paciente p WHERE p.nombre=:nombre")
    Optional<Paciente> findByName(@Param("nombre")String nombre);
}
