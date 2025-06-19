package ar.edu.utn.frbb.tup.controller.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frbb.tup.model.dto.MateriaDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MateriaValidatorTest {

    MateriaValidator MateriaValidator;

    @BeforeEach
    public void setUp() {
        MateriaValidator = new MateriaValidator();
    }

    @Test
    public void testMateriaValidatorSuccess(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(2);
        materiadto.setCuatrimestre(1);
        materiadto.setCorrelatividades(null);

        assertDoesNotThrow(() -> MateriaValidator.validarMateria(materiadto));
    }


    @Test
    public void testMateriaValidatorSinNombre(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(2);
        materiadto.setCuatrimestre(1);
        materiadto.setCorrelatividades(null);

        assertThrows(IllegalArgumentException.class,(() -> MateriaValidator.validarMateria(materiadto)));
    }


    @Test
    public void testMateriaValidatorSinAnio(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(0);
        materiadto.setCuatrimestre(1);
        materiadto.setCorrelatividades(null);

        assertThrows(IllegalArgumentException.class,(() -> MateriaValidator.validarMateria(materiadto)));
    }

    @Test
    public void testMateriaValidatorAnioNegativo(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(-5);
        materiadto.setCuatrimestre(1);
        materiadto.setCorrelatividades(null);

        assertThrows(IllegalArgumentException.class,(() -> MateriaValidator.validarMateria(materiadto)));
    }


    @Test
    public void testMateriaValidatorSinCuatrimestre(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(2);
        materiadto.setCuatrimestre(0);
        materiadto.setCorrelatividades(null);

        assertThrows(IllegalArgumentException.class,(() -> MateriaValidator.validarMateria(materiadto)));
    }

    @Test
    public void testMateriaValidatorCuatrimestreNegativo(){
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorId(5555);
        materiadto.setAnio(2);
        materiadto.setCuatrimestre(-1);
        materiadto.setCorrelatividades(null);

        assertThrows(IllegalArgumentException.class,(() -> MateriaValidator.validarMateria(materiadto)));
    }
}
