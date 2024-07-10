package com.gestion.test.service;

import com.gestion.test.entity.Paciente;

import java.util.List;

public class ServiceExample {

    List<Paciente> pacientes = List.of(new Paciente(1,"David",(short)18),
            new Paciente(2,"Jose",(short)20),
            new Paciente(3,"Antonio",(short)22),
            new Paciente(4,"Eduardo",(short)30),
            new Paciente(5,"Mateo",(short)33),
            new Paciente(6,"Diego",(short)15),
            new Paciente(7,"Maria",(short)31),
            new Paciente(8,"Josefina",(short)27));

    //Metodos simulando el Repository
    Paciente getPatient(Integer id, String nombre, short edad) {
        Paciente pacienteReturn = null;

        for(Paciente paciente: pacientes) {
            if(paciente.getId().equals(id) && paciente.getNombre().equals(nombre) && paciente.getEdad() == edad) {
                pacienteReturn = paciente;
                break;
            }
        }
        if(pacienteReturn == null) {
            pacienteReturn = new Paciente();
        }

        return pacienteReturn;
    }
    Paciente getPatientBy_ID(Integer id) {
        Paciente paciente = null;
        for(Paciente paciente1:pacientes) {
            if(paciente1.getId().equals(id)) {
                paciente = paciente1;
            }
        }

        return paciente==null?new Paciente():paciente;
    }
    Paciente save(Paciente paciente) {
        pacientes.add(paciente);
        return paciente;
    }
    List<Paciente> getAll() {
        return pacientes;
    }
}
