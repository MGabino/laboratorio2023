package ar.edu.utn.frbb.tup.controller.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsignaturaValidatorTest {

    AsignaturaValidator asignaturaValidator;

    @BeforeEach
    public void setUp() {
        asignaturaValidator = new AsignaturaValidator();
    }

    @Test
    public void testAsignaturaValidatorSuccess(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setEstado(EstadoAsignatura.valueOf("CURSADA"));
        asignaturadto.setNota(7);

        assertDoesNotThrow(() -> asignaturaValidator.validarAsignatura(asignaturadto));
    }

    @Test
    public void testAsignaturaValidatorSinEstadoAsignatura(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setNota(7);

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }

    @Test
    public void testAsignaturaValidatorNotaNegativa(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setEstado(EstadoAsignatura.valueOf("NO_CURSADA"));
        asignaturadto.setNota(-5);

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }

    @Test
    public void testAsignaturaValidatorNotaMayorADiez(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setEstado(EstadoAsignatura.valueOf("CURSADA"));
        asignaturadto.setNota(12);

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }
}