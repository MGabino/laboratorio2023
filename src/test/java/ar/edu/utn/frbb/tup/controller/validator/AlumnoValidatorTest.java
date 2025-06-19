package ar.edu.utn.frbb.tup.controller.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlumnoValidatorTest {

    AlumnoValidator alumnoValidator;

    @BeforeEach
    public void setUp() {
        alumnoValidator = new AlumnoValidator();
    }

    @Test
    public void testAlumnoValidatorSuccess(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni(12345678);
        alumnodto.setNombre("Gabino");
        alumnodto.setApellido("Mangas");

        assertDoesNotThrow(() -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinDni(){
        AlumnoDto alumnodto = new AlumnoDto();
        //alumnoDto.setDni(12345678);
        alumnodto.setNombre("Gabino");
        alumnodto.setApellido("Mangas");

        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorDniCeroOMenos(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni(-12345678);
        alumnodto.setNombre("Gabino");
        alumnodto.setApellido("Mangas");


        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorDniFalso(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni(1234567899);
        alumnodto.setNombre("Gabino");
        alumnodto.setApellido("Mangas");


        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinNombre(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni(12345678);
        alumnodto.setNombre("");
        alumnodto.setApellido("Mangas");

        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinApellido(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni(12345678);
        alumnodto.setNombre("Gabino");
        alumnodto.setApellido("");
        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));

    }
}
