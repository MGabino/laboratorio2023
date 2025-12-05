package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MateriaDaoMemoryImplTest {

    private MateriaDaoMemoryImpl materiaDao;

    @BeforeEach
    void setUp() {
        materiaDao = new MateriaDaoMemoryImpl();
        materiaDao.clear();     // limpia el repositorio antes de cada test
    }


    @Test
    void testSave_ok_sinCorrelatividades() throws Exception {
        Materia m1 = new Materia();
        m1.setNombre("Matematica");

        Materia result = materiaDao.save(m1, Collections.emptyList());

        assertNotNull(result.getMateriaId());
        assertEquals("Matematica", result.getNombre());
        assertTrue(result.getCorrelatividades().isEmpty());
    }


    @Test
    void testSave_ok_conCorrelatividades() throws Exception {
        // a
        Materia m1 = new Materia();
        m1.setNombre("Programacion I");
        materiaDao.save(m1, Collections.emptyList());

        // b
        Materia m2 = new Materia();
        m2.setNombre("Programacion II");

        // correlativa  ID de m1
        List<Integer> correlativas = List.of(m1.getMateriaId());

        Materia result = materiaDao.save(m2, correlativas);

        assertEquals(1, result.getCorrelatividades().size());
        assertEquals("Programacion I", result.getCorrelatividades().get(0).getNombre());
    }


    @Test
    void testSave_lanzaYaExisteException() throws Exception {
        Materia m1 = new Materia();
        m1.setNombre("Algebra");
        materiaDao.save(m1, Collections.emptyList());

        Materia m2 = new Materia();
        m2.setNombre("Algebra");

        assertThrows(
                YaExisteException.class,
                () -> materiaDao.save(m2, Collections.emptyList())
        );
    }


    @Test
    void testSave_lanzaMateriaNotFoundException_porCorrelativa() throws Exception {
        Materia m1 = new Materia();
        m1.setNombre("Base de Datos");

        // correlativa inexistente
        List<Integer> correlativas = List.of(999);

        assertThrows(
                MateriaNotFoundException.class,
                () -> materiaDao.save(m1, correlativas)
        );
    }


    @Test
    void testGetAllMaterias_ok() throws Exception {
        Materia m1 = new Materia();
        m1.setNombre("SO");
        materiaDao.save(m1, Collections.emptyList());

        Materia m2 = new Materia();
        m2.setNombre("IA");
        materiaDao.save(m2, Collections.emptyList());

        List<Materia> lista = materiaDao.getAllMaterias();

        assertEquals(2, lista.size());
    }


    @Test
    void testFindById_ok() throws Exception {
        Materia m1 = new Materia();
        m1.setNombre("Laboratorio");
        materiaDao.save(m1, Collections.emptyList());

        Materia encontrada = materiaDao.findById(m1.getMateriaId());

        assertEquals("Laboratorio", encontrada.getNombre());
    }
    @Test
    void testFindById_lanzaMateriaNotFound() {
        assertThrows(
                MateriaNotFoundException.class,
                () -> materiaDao.findById(12345)
        );
    }
}
