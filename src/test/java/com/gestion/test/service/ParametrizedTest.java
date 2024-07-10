package com.gestion.test.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class ParametrizedTest {
    /*Las pruebas parametrizadas, son pruebas para validar diferentes casos de un metodo, por ejemplo si
    * existen varias condicionales y muchos return dentro de un metodo, podemos validar o detectar que tipo de
    * returns,valores o datos se se realiza con cada condicional, es decir podemos tener en cuenta muchos posibles casos*/

    @ParameterizedTest
    //Los datos que vamos a pasar (como Array) para validar una misma funcion
    @ValueSource(strings = {"David","Maria"}) //Los datos explicitamente
        // En este metodo como pasamos solamente 2 parametros, la prueba se ejecutara 2 veces
    //La funcion se ejecuta la cantidad de parametros que se le pasa
    void get_name_by_id(String data) {

        ServiceExample service_example = new ServiceExample();
        assertAll(
                () -> assertNotNull(service_example),
                () -> assertEquals(data,service_example.getPatientBy_ID(1).getNombre()),
                () -> assertEquals("David",service_example.getPatientBy_ID(1).getNombre())
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/save-repository.csv")
    void read_data_from_file(String data) {

        ServiceExample service_example = new ServiceExample();

        assertAll(
                () -> assertNotNull(service_example),
                () -> assertEquals(data,service_example.getPatientBy_ID(1).getNombre()),
                () -> assertEquals("David",service_example.getPatientBy_ID(1).getNombre())
        );
    }
}
