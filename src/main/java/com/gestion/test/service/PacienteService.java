package com.gestion.test.service;

import com.gestion.test.entity.Paciente;
import com.gestion.test.repository.PacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PacienteService {
    @Autowired
    private PacienteRepository repository;

    public Paciente getPacienteById(Integer id) {
        Optional<Paciente> pacienteBD = repository.findById(id);
        return pacienteBD.orElseGet(Paciente::new);
    }
    public List<Paciente> getAllPacients() {
        return repository.findAll();
    }
    public Paciente update(Integer id,Paciente paciente) {
        Optional<Paciente> pacienteBD = repository.findById(id);
        if(pacienteBD.isPresent()) {
            Paciente pacienteSv = pacienteBD.get();
            pacienteSv.setNombre(paciente.getNombre());
            pacienteSv.setEdad(paciente.getEdad());
            return repository.save(pacienteSv);
        }
        return new Paciente();
    }
    public String delete(Integer id) {
        Optional<Paciente> pacienteDL = repository.findById(id);
        if(pacienteDL.isPresent()) {
            repository.delete(pacienteDL.get());
            return "Paciente eliminado correctamente";
        }
        return "No se encontro ningun paciente con el ID: "+id;
    }

    public Paciente savePatient(Paciente paciente)throws Exception {
        Optional<Paciente> pacienteDB = repository.findByName(paciente.getNombre());
        if(pacienteDB.isPresent()) {
            throw new Exception("El paciente "+paciente.getNombre()+" ya se encuentra registrado");
        }
        return repository.save(paciente);
    }
    public Paciente getPatientByName(String name)throws Exception {
        Optional<Paciente> pacienteDB = repository.findByName(name);
        if(pacienteDB.isEmpty()) {
            throw new ClassNotFoundException("Paciente con nombre "+name+" no existe");
        }
        if(!pacienteDB.get().getNombre().equals(name)) {
            throw new Exception("El nombre del paciente no coincide con el esperado");
        }
        return pacienteDB.get();
    }
}