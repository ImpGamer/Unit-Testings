package com.gestion.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nombre;
    short edad;

    public Paciente(String nombre,short edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    public Paciente(Integer id) {
        this.id = id;
    }

}
