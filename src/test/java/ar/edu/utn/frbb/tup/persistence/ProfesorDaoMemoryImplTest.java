package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorDaoMemoryImplTest {

    private ProfesorDaoMemoryImpl profesorDao;

    @BeforeEach
    void setUp() {
        profesorDao = new ProfesorDaoMemoryImpl();
        profesorDao.clear();
    }

    // -------------------------------------------------------
    //  TEST: saveProfesor OK
    // -------------------------------------------------------
    @Test
    void testSaveProfesor_ok() throws YaExisteException {
        Profesor p = new Profesor();
        p.setNombre("Juan");
        p.setApellido("Perez");

        Profesor saved = profesorDao.saveProfesor(p);

        assertNotNull(saved);
        assertTrue(saved.getId() > 0);
    }

    // -------------------------------------------------------
    //  TEST: saveProfesor ERROR → YaExisteException
    // -------------------------------------------------------
    @Test
    void testSaveProfesor_yaExiste() throws YaExisteException {
        Profesor p1 = new Profesor();
        p1.setNombre("Juan");
        p1.setApellido("Perez");

        Profesor p2 = new Profesor();
        p2.setNombre("Juan");
        p2.setApellido("Perez");

        profesorDao.saveProfesor(p1);

        assertThrows(YaExisteException.class,
                () -> profesorDao.saveProfesor(p2)
        );
    }

    // -------------------------------------------------------
    //  TEST: findProfesor OK
    // -------------------------------------------------------
    @Test
    void testFindProfesor_ok() throws YaExisteException {
        Profesor p = new Profesor();
        p.setNombre("Ana");
        p.setApellido("Lopez");

        profesorDao.saveProfesor(p);

        Profesor result = profesorDao.findProfesor(p.getId());

        assertEquals("Ana", result.getNombre());
        assertEquals("Lopez", result.getApellido());
    }

    // -------------------------------------------------------
    //  TEST: findProfesor ERROR → NOT_FOUND
    // -------------------------------------------------------
    @Test
    void testFindProfesor_notFound() {
        assertThrows(ResponseStatusException.class,
                () -> profesorDao.findProfesor(999999)
        );
    }

    // -------------------------------------------------------
    //  TEST: deleteProfesor OK
    // -------------------------------------------------------
    @Test
    void testDeleteProfesor_ok() throws YaExisteException {
        Profesor p = new Profesor();
        p.setNombre("Carlos");
        p.setApellido("Sanchez");

        profesorDao.saveProfesor(p);

        Profesor deleted = profesorDao.deleteProfesor((int) p.getId());

        assertEquals("Carlos", deleted.getNombre());
    }

    // -------------------------------------------------------
    //  TEST: deleteProfesor ERROR → NOT_FOUND
    // -------------------------------------------------------
    @Test
    void testDeleteProfesor_notFound() {
        assertThrows(ResponseStatusException.class,
                () -> profesorDao.deleteProfesor(12345)
        );
    }

    // -------------------------------------------------------
    //  TEST: update OK
    // -------------------------------------------------------
    @Test
    void testUpdate_ok() throws YaExisteException {
        Profesor p = new Profesor();
        p.setNombre("Mario");
        p.setApellido("Gomez");

        profesorDao.saveProfesor(p);

        p.setNombre("Mariano");
        profesorDao.update(p.getId(), p);

        Profesor updated = profesorDao.findProfesor(p.getId());

        assertEquals("Mariano", updated.getNombre());
    }
}