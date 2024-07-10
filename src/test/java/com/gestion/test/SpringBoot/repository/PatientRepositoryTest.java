package com.gestion.test.SpringBoot.repository;

import com.gestion.test.entity.Paciente;
import com.gestion.test.repository.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Anotacion que indicara que haremos pruebas en la capa de persistencia o conexion con una DB
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PatientRepositoryTest {
    @Autowired
    private PacienteRepository repository;

    @DisplayName("Guardar paciente correctamente y validar su ID y nulabilidad")
    @Test
    void save_patient() {
        Paciente paciente1 = new Paciente(5,"Marco",(short)21);

        Paciente pacienteSave = repository.save(paciente1);
        assertAll(() -> assertNotNull(pacienteSave), //validar que no sea nulo
                () -> assertThat(pacienteSave.getId(),greaterThan(4))); //validar que el ID sea mayor a 5
    }

    @DisplayName("Listar todos los pacientes")
    @Test
    void list_patients() {
        List<Paciente> pacientes = repository.findAll();

        assertAll(() -> assertNotNull(pacientes),
                () -> {
            int id=1;
            for(Paciente paciente: pacientes) {
                assertEquals(id,paciente.getId());
                id++;
            }
                },
                () -> assertEquals(4,pacientes.size()));
    }
    @Test
    @DisplayName("Test para obtener un empleado por ID")
    void get_patient_by_id() {
        Paciente pacienteBD = repository.findById(2).orElse(null);

        assertAll(() -> assertNotNull(pacienteBD),
                () -> assertThat(pacienteBD,hasProperty("id")));
    }


}
