package com.gestion.test.service;

import com.gestion.test.entity.Paciente;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@Tags(@Tag("service"))
//Anotacion para cambiar los nombres de las pruebas
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) //En este caso elimina los guiones bajo
class PacienteServiceTest {
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

    /*Anotaciones de repeticion. En ocasiones debemos ahorrar perfomance o solamente lineas, por lo que
    * existen anotaciones que se ejecutara en determinados momentos antes o depues de las pruebas.
    * @BeforeEach -> Anotacion que se le coloca a una funcion, para marcar que se ejecutara antes de cada prueba.
    * @AfterEach -> Similar al anterior, pero se ejecutara cada vez que termine una prueba.
    * @BeforeAll -> Anotacion que indica que la funcion se ejecutara 1 sola vez antes de todas las pruebas.
    * @AfterAll -> Funcion que se ejecutara una vez terminada todas las pruebas*/

    @Tag("success-case")
    @DisplayName("Deberia validar si el ID del usuario recuperado es 1")
    @Test
    void get_patient_id_test() {
        //Then (Espera de un resultado en concreto de la funcion o metodo)
        //assertEquals(1,pacienteDB.getId());

        List<Paciente> pacientes = getAllPatients();
        Paciente pacienteBD = getPatient(1,"David",(short)18);
        /*Un assertAll() a comparacion de asserts continuos, esque ira realizando las pruebas, y al momento que
        * una falle no se detendra y continuara con las siguiente, al finalizar todos los asserts puestos, nos dara
        * cuales fallaron y cuales no, de esa manera tenemos en panorama todo los posibles errores al momento */
        assertAll(() -> assertNotNull(pacientes),
                //Valida si se encuentra en el arreglo
                () -> assertThat(getPatient(1,"David",(short)18),in(pacientes)),
                () -> assertEquals(1,getPatientBy_ID(1).getId()),
                () -> assertEquals(8,pacientes.size()),
                //Validamos si el primer paciente posee la propiedad "id"
                () -> assertThat(pacientes.get(0), hasProperty("id")));
    }

    //Funciones que simulan operaciones del repositorio o servicio
    private Paciente getPatient(Integer id,String nombre,short edad) {
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
    private Paciente getPatientBy_ID(Integer id) {
        Paciente paciente = null;
        for(Paciente paciente1:pacientes) {
            if(paciente1.getId().equals(id)) {
                paciente = paciente1;
            }
        }

        return paciente==null?new Paciente():paciente;
    }
    private List<Paciente> getAllPatients() {
        return Arrays.stream(pacientes).toList();
    }

    @Tag("error-case")
    @Test
    void test_of_error() {
        for(int i=0;i<5;i++) {
            System.out.println("Error N. "+(i+1));
        }
    }

}