package com.gestion.test.SpringBoot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.test.controller.PacienteController;
import com.gestion.test.entity.Paciente;
import com.gestion.test.service.PacienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(PacienteController.class)
public class TestPacienteController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService service;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void test_to_save_a_patient()throws Exception {
        Paciente paciente = new Paciente(1,"Alejandro",(short)20);

        /*CUando el metodo del servicio ".savePatient()" sea llamado en cualquier parte, entonces retornara
        * el mismo objeto que se le paso como parametro simulando el guardado y devolucion de dicho elemento u objeto.*/
        when(service.savePatient(any(Paciente.class))).thenAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/pacientes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paciente)));

        response.andDo(print())
                .andExpect(status().isCreated())
                //Acceder a un atributo del objeto de respuesta
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Alejandro"))
                .andExpect(jsonPath("$.edad").value(20));
    }

    @Test
    @DisplayName("Obtener paciente por ID")
    void get_a_patient_by_ID()throws Exception {
        final int PATIENT_ID = 1;

        Paciente paciente = new Paciente(PATIENT_ID,"Federico",(short)31);
        when(service.getPacienteById(PATIENT_ID)).thenReturn(paciente);

        //Como segundo atributo le indicamos el valor de la variable de ruta
        ResultActions response = mockMvc.perform(get("/pacientes/{id}",PATIENT_ID));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PATIENT_ID))
                .andExpect(jsonPath("$.nombre").value(paciente.getNombre()));
    }


}
