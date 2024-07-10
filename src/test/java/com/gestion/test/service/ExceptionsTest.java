package com.gestion.test.service;

import com.gestion.test.entity.Paciente;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
public class ExceptionsTest {
    Paciente[] pacientes = {
            new Paciente(1,"David",(short)18),
            new Paciente(2,"Jose",(short)20),
            new Paciente(3,"Antonio",(short)22),
            new Paciente(4,"Eduardo",(short)30),
            new Paciente(5,"Mateo",(short)33),
            new Paciente(6,"Diego",(short)15),
            new Paciente(7,"Maria",(short)31),
            new Paciente(8,"Josefina",(short)27)
    };

    @Disabled //Anotacion para indicarle que esta prueba sera ignorada
    @Test
    void if_the_exception_of_method_is_right() {
        assertThrows(RuntimeException.class,() -> getPatient_ID(90));
    }

    private Paciente getPatient_ID(Integer id)throws PatientNotFoundException {
        Paciente pacienteDB = Arrays.stream(pacientes)
                .filter(paciente -> paciente.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(pacienteDB == null) {
            throw new PatientNotFoundException("Paciente con ID "+id+" no fue encontrado");
        }
        return pacienteDB;
    }
}

class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}