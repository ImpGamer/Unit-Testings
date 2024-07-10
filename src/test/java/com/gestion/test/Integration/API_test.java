package com.gestion.test.Integration;

import com.gestion.test.entity.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Le declaro que levante mi aplicacion en un puerto aleatorio
public class API_test {
    /*De esta manera podemos realizar pruebas a una endpoint, es decir tomara el rol
    * de una prueba de integracion que testeara varios procesos, clases y metodos para validar
    * un resultado.*/
    private MockMvc mockMvc; //Nos permite hacer requests a un endpoint
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp(WebApplicationContext applicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    void should_get_a_patient_from_my_BD()throws Exception {
        mockMvc.perform(get("/pacientes/1").contentType("application/json")) //contenido esperado (JSON)
                .andExpect(status().is2xxSuccessful()); //Codigo de respuesta HTTP esperado
    }

    /*Para estas pruebas, se necesita la aplicacion levantada, ya que interactuaremos con la aplicacion construida sin simulacion
    * por mocks*/
    @Test
    void save_a_patient() {
        Paciente paciente = new Paciente(1,"Jose Alfredo",(short)33);

        //Mandaremos la URL completa del endpoint, el body y la clase a que pertenece dicho body
        ResponseEntity<Paciente> response = testRestTemplate.postForEntity("http://localhost:8080/pacientes/add",paciente,Paciente.class);
        Paciente pacienteDB = response.getBody();

        assertAll(() -> assertEquals(HttpStatus.CREATED,response.getStatusCode()),
                () -> assertNotNull(pacienteDB),
                () -> assertEquals(1,pacienteDB.getId()),
                () -> assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType()));
    }
}
