package com.gestion.test.service;

import com.gestion.test.entity.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Mocks {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mocks.class);
    /*Aparte de los mocks, tambien existen los spy, estos realizaran el metodo original del objeto,
    * sin necesidad de especificarle como a un mock*/
    @Mock //Se le agrega la anotacion que una clase sera mockeada
    PacienteService pacienteService;

    @BeforeEach
    void initilize_service() {
        MockitoAnnotations.openMocks(this); //Manera de instanciar las clases con la anotacion @Mock
    }

    @Test
    @DisplayName("Testing if the service get a correct patient")
    void test_get_patient_by_id() {
        Paciente mockPaciente = new Paciente(3,"Alfonso",(short)13);
        //Se especifica el comportamiento del metodoy que debera retornar
        when(pacienteService.getPacienteById(3)).thenReturn(mockPaciente);

        when(pacienteService.getPacienteById(5)).thenReturn(new Paciente());

        Paciente result = pacienteService.getPacienteById(3);
        assertAll(() -> assertNotNull(result),
                () -> assertEquals("Alfonso",result.getNombre()),
                () -> assertEquals((short)13,result.getEdad()),
                () -> assertEquals(3,result.getId()));

        verify(pacienteService,atMostOnce()).getPacienteById(3); //Verificar que aunque sea el .getPacienteById(3) se use 1 vez
    }

}
