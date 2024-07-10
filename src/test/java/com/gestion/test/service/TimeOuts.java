package com.gestion.test.service;

import com.gestion.test.entity.Paciente;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//El metodo de ordenacion sera por las anotaciones @Order
public class TimeOuts {
    /*Los timeouts dentro de las pruebas unitarias, son una cantidad de tiempo que esperaremos a que
    * la prueba se realice, si en ese lapso de tiempo especificado la prueba no se a ejecutado, soltara
    * una excepcion e invalidara la ejecucion de esa prueba unitaria en concreto (funcion) sin afectar a las
    * demas.*/
    ServiceExample serviceExample;
    List<Paciente> pacientes;

    @BeforeEach
    void setServiceExample() {
        serviceExample = new ServiceExample();
        pacientes = serviceExample.getAll();
    }

    //Hagamos un ejemplo, tengo dos pruebas:
    @Tag("error-case")
    @Test
    @DisplayName("Obtencion de nombre e ID de un usuario")
    void get_name_and_id_from_user() {
        for(Paciente paciente: pacientes) {
           assertNotNull(paciente);
        }
    }

    @Test
    @Timeout(value = 1,unit = TimeUnit.NANOSECONDS) //Solo esperaremos 1 nanosegundo, esto para provocar un error
    //Como la prueba tarda mas de 1 nanosegundo, terminara su espera y lanzara una excepcion
    void get_all_users() {
        int currentID = 1;

        while(currentID < pacientes.size()) {
            assertEquals(currentID,serviceExample.getPatientBy_ID(currentID).getId());

            currentID++;
        }
    }

    //Importancia de ejecucion
    /*La anotacion @Order indica en que orden se iran ejecutando las pruebas unitarias, todos tendran por defecto 0, es decir
    * se ejecutaran de manera aleatoria, entre mas alto su valor dentro del parametro de la anotacion sea, mas importancia tendra
    * esa prueba, por lo que se ejecutara antes, aquella con un valor mayor. 2 pruebas pueden tener un mismo orden*/
    @Order(1)
    @Test
    @DisplayName("Valida el tamanio de la lista")
    void assert_size_list() {
        assertThat(pacientes,hasSize(8));
    }
}
