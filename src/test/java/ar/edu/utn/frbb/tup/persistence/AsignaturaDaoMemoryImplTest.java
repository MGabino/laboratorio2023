package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsignaturaDaoMemoryImplTest {

    private AsignaturaDaoMemoryImpl asignaturaDao;

    @BeforeEach
    void setUp() {
        asignaturaDao = new AsignaturaDaoMemoryImpl();
        //asignaturaDao.clear();

        // hay q limpiar el repositorio estatico antes de cada test
        // y usando reflection porque el map del repositorio es static
        var repositorio = getRepositorioAsignaturas();
        repositorio.clear();
    }

    // metodo auxiliar
    @SuppressWarnings("unchecked")
    private static java.util.Map<Long, Asignatura> getRepositorioAsignaturas() {
        try {
            var field = AsignaturaDaoMemoryImpl.class.getDeclaredField("repositorioAsignaturas");
            field.setAccessible(true);
            return (java.util.Map<Long, Asignatura>) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void testSaveAsignatura_ok() {
        Materia materia = new Materia();
        materia.setNombre("Matematica");

        Asignatura a = asignaturaDao.saveAsignatura(materia);

        assertNotNull(a);
        assertEquals("Matematica", a.getMateria().getNombre());

        assertFalse(getRepositorioAsignaturas().isEmpty());
    }


    @Test
    void testMateriaToAsignatura_ok() {
        Materia m1 = new Materia();
        m1.setNombre("Algoritmos");

        Materia m2 = new Materia();
        m2.setNombre("Base de Datos");

        asignaturaDao.materiaToAsignatura(List.of(m1, m2));

        assertEquals(2, getRepositorioAsignaturas().size());
    }


    @Test
    void testGetListaAsignaturas_ok() {
        Materia m1 = new Materia();
        m1.setNombre("Algebra");

        Materia m2 = new Materia();
        m2.setNombre("Laboratorio");

        asignaturaDao.saveAsignatura(m1);
        asignaturaDao.saveAsignatura(m2);

        List<Asignatura> lista = asignaturaDao.getListaAsignaturas();

        assertEquals(2, lista.size());
    }


    @Test
    void testGetAsignaturaById_ok() throws Exception {
        Materia materia = new Materia();
        materia.setNombre("Prog I");

        Asignatura asignatura = asignaturaDao.saveAsignatura(materia);

        Asignatura encontrada =
                asignaturaDao.getAsignaturabyId(asignatura.getAsignaturaId());

        assertNotNull(encontrada);
        assertEquals("Prog I", encontrada.getMateria().getNombre());
    }
    @Test
    void testGetAsignaturaById_notFound() {
        AsignaturaNoExisteException ex = assertThrows(
                AsignaturaNoExisteException.class,
                () -> asignaturaDao.getAsignaturabyId(999L)
        );

        assertTrue(ex.getMessage().contains("999"));
    }


    @Test
    void testPutAsignatura_ok() throws Exception {
        Materia materia = new Materia();
        materia.setNombre("Programacion");

        Asignatura asignatura = asignaturaDao.saveAsignatura(materia);

        // Simular actualización
        asignatura.getMateria().setNombre("Programacion");

        asignaturaDao.putAsignatura(asignatura);

        Asignatura guardada =
                asignaturaDao.getAsignaturabyId(asignatura.getAsignaturaId());

        assertEquals("Programacion", asignaturaDao
                .getAsignaturabyId(asignatura.getAsignaturaId())
                .getNombreAsignatura());
    }
    @Test
    void testPutAsignatura_notFound() {
        Asignatura asignatura = new Asignatura();
        asignatura.setAsignaturaId(123L);

        AsignaturaNoExisteException ex = assertThrows(
                AsignaturaNoExisteException.class,
                () -> asignaturaDao.putAsignatura(asignatura)
        );

        assertTrue(ex.getMessage().contains("123"));
    }
}
