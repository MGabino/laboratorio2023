package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoDaoMemoryImplTest {

    private AlumnoDaoMemoryImpl alumnoDao;

    @BeforeEach
    void setUp() throws Exception {
        alumnoDao = new AlumnoDaoMemoryImpl();
        alumnoDao.clear();
    }

    //private void resetRepositorio() throws Exception {
    //    Field field = AlumnoDaoMemoryImpl.class.getDeclaredField("repositorioAlumnos");
    //    field.setAccessible(true);
    //    ((Map<?, ?>) field.get(null)).clear();
    //}



    @Test
    void testSaveAlumno_ok() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");

        Alumno result = alumnoDao.saveAlumno(alumno);

        // put() devuelve el valor anterior, debe ser null
        assertNull(result);
        assertTrue(alumno.getId() > 0, "El ID debe generarse aleatoriamente");

        // está en el repositorio?
        assertDoesNotThrow(() -> alumnoDao.findAlumno(alumno.getId()));
    }


    @Test
    void testFindAlumno_ok() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Ana");
        alumnoDao.saveAlumno(alumno);

        Alumno encontrado = alumnoDao.findAlumno(alumno.getId());

        assertEquals("Ana", encontrado.getNombre());
    }
    @Test
    void testFindAlumno_notFound() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> alumnoDao.findAlumno(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("404 NOT_FOUND \"No existen alumnos con esos datos.\"", ex.getMessage());
    }


    @Test
    void testDeleteAlumno_ok() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Carlos");
        alumnoDao.saveAlumno(alumno);

        Alumno eliminado = alumnoDao.deleteAlumno((int) alumno.getId());

        assertEquals("Carlos", eliminado.getNombre());
    }
    @Test
    void testDeleteAlumno_notFound() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> alumnoDao.deleteAlumno(100)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("404 NOT_FOUND \"No existen alumnos con esos datos para eliminarlo.\"", ex.getMessage());
    }


    @Test
    void testUpdate_ok() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Pedro");
        alumnoDao.saveAlumno(alumno);

        alumno.setNombre("Pedro Actualizado");
        alumnoDao.update(alumno.getId(), alumno);

        Alumno actualizado = alumnoDao.findAlumno(alumno.getId());

        assertEquals("Pedro Actualizado", actualizado.getNombre());
    }


    @Test
    void testLoadAlumno_null() {
        assertNull(alumnoDao.loadAlumno(123L));
    }
}
