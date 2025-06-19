package ar.edu.utn.frbb.tup.controller.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfesorValidatorTest {

    ProfesorValidator profesorValidator;

    @BeforeEach
    public void setUp() {
        profesorValidator = new ProfesorValidator();
    }

    @Test
    public void testProfesorValidatorSuccess(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Gabino");
        profesordto.setApellido("Mangas");
        profesordto.setTitulo("Tecnico en Programacion");
        assertDoesNotThrow(() -> profesorValidator.validarProfesor(profesordto));

    }

    @Test
    public void testProfesorValidatorSinNombre(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("");
        profesordto.setApellido("Martinez");
        profesordto.setTitulo("Tecnico en Programacion");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }

    @Test
    public void testProfesorValidatorSinApellido(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Gabino");
        profesordto.setApellido("");
        profesordto.setTitulo("Tecnico en Programacion");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }

    @Test
    public void testProfesorValidatorSinTitulo(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Gabino");
        profesordto.setApellido("Martinez");
        profesordto.setTitulo("");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }
}
