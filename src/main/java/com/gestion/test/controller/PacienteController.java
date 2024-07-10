package com.gestion.test.controller;

import com.gestion.test.entity.Paciente;
import com.gestion.test.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;

    @GetMapping
    List<Paciente> listarPacientes() {
        return service.getAllPacients();
    }
    @GetMapping("/{id}")
    ResponseEntity<Paciente> obtenerPaciente_id(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getPacienteById(id));
    }
    @PatchMapping("/edit/{id}")
    Paciente actualizarPaciente(Integer id,Paciente paciente) {
        return service.update(id,paciente);
    }
    @DeleteMapping("/{id}")
    String eliminarPaciente(@PathVariable Integer id) {
        return service.delete(id);
    }
    @PostMapping("/add")
    ResponseEntity<Paciente> guardar_paciente(@RequestBody Paciente paciente)throws Exception {
        return new ResponseEntity<>(service.savePatient(paciente), HttpStatus.CREATED);
    }
}
