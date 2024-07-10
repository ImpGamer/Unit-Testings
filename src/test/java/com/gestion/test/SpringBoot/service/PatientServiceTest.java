package com.gestion.test.SpringBoot.service;

import com.gestion.test.entity.Paciente;
import com.gestion.test.repository.PacienteRepository;
import com.gestion.test.service.PacienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    //Creamos la simulacion del repositorio
    @Mock
    private PacienteRepository repository;

    /*Inyectamos el repositorio al servicio, ya que el servicio depende del repositorio, entonces podriamos decir
    * que dentro de mi servicio inyectare un repositorio*/
    @InjectMocks
    private PacienteService service;

    private Paciente paciente;

    @Disabled
    @BeforeEach
    void setUp() {
        paciente = new Paciente("Cesar",(short)29);
        paciente.setId(9);
    }

    @Test
    void guardar_empleado()throws Exception {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        //Cuando se llame al metodo .save() del repositorio devuelveme ese mismo paciente del parametro
        when(repository.save(paciente)).thenReturn(paciente);

        Paciente pacienteGuardado = service.savePatient(paciente);
        assertNotNull(pacienteGuardado);
    }

    @DisplayName("Retorno de un Paciente diferente y esperamos una Excepcion")
    @Test
    void should_return_a_patient_totally_diferent_to_spected() {
        when(repository.findByName(anyString())).thenReturn(Optional.of(paciente));

        assertThrows(Exception.class,() -> service.getPatientByName("Lorenzo"));
    }
    @DisplayName("Retorno de un paciente vacio y espera de un ClassNotFoundException")
    @Test
    void should_return_a_void_patient() {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class,() -> service.getPatientByName("Lorenzo"));
        //Verificar que si por lo menos se uso 1 vez el metodo .findByName() dentro de la logica
        verify(repository,atMostOnce()).findByName(anyString());
        //Verificar que nunca se haya usado un .save() del repositorio mockeado, con parametro de algun objeto Paciente
        verify(repository,never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Obtener paciente por ID")
    void should_return_a_patient_with_ID_7() {
        final int ID = 9;
        paciente.setId(ID);
        when(repository.findById(ID)).thenReturn(Optional.of(paciente));

        Paciente pacienteBBD = service.getPacienteById(paciente.getId());

        assertAll(() -> assertNotNull(pacienteBBD),
                () -> assertNotNull(pacienteBBD.getId()),
                () -> assertEquals(ID,pacienteBBD.getId()));
    }

    @ParameterizedTest
    @DisplayName("Test para validar que el ID del paciente no sea 9")
    @MethodSource("get_random_numbers") //Se llama a la funcion estatica
    void update_data_from_patient(int ID) { //Cada ID o numero aleatorio del array
        final int default_ID = 9;
        paciente.setId(default_ID);

        assertNotEquals(ID,default_ID);
    }

    static Stream<Integer> get_random_numbers() {
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(10)) // Genera números aleatorios entre 0 y 10
                .limit(5) // Limita a 5 números
                .boxed(); // Convierte IntStream a Stream<Integer>
    }

}
