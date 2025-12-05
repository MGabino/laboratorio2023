package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.impl.AsignaturaServiceImpl;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
//import ar.edu.utn.frbb.tup.model.exception.AsignaturaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsignaturaServiceImplTest {

    @Mock
    MateriaService materiaService;

    @Mock
    AsignaturaDao asignaturaDao;

    @InjectMocks
    AsignaturaServiceImpl asignaturaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testActualizarAsignatura_ok() throws Exception {
        Asignatura asignatura = new Asignatura();

        doNothing().when(asignaturaDao).putAsignatura(asignatura);

        asignaturaService.actualizarAsignatura(asignatura);

        verify(asignaturaDao, times(1)).putAsignatura(asignatura);
    }


    @Test
    void testActualizarAsignatura_error() throws Exception {
        Asignatura a = new Asignatura();

        doThrow(new RuntimeException("DAO error"))
                .when(asignaturaDao).putAsignatura(a);

        assertThrows(
                AsignaturaNoExisteException.class,
                () -> asignaturaService.actualizarAsignatura(a)
        );
    }

    @Test
    void testAsignaturaList_ok() {
        Materia m1 = new Materia();
        m1.setNombre("Matemática");

        Materia m2 = new Materia();
        m2.setNombre("Programación");

        when(materiaService.getAllMaterias())
                .thenReturn(Arrays.asList(m1, m2));

        List<Asignatura> result = asignaturaService.asignaturaList();

        assertEquals(2, result.size());
        assertEquals("Matemática", result.get(0).getMateria().getNombre());
        assertEquals("Programación", result.get(1).getMateria().getNombre());

        // Los IDs deben ser distintos (uuid interno)
        assertNotEquals(result.get(0).getAsignaturaId(), result.get(1).getAsignaturaId());
    }

    @Test
    void testAsignaturaList_vacio() {
        when(materiaService.getAllMaterias()).thenReturn(List.of());

        List<Asignatura> result = asignaturaService.asignaturaList();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAsignatura_null() {
        Asignatura result = asignaturaService.getAsignatura(1, 123);
        assertNull(result);
    }
}
