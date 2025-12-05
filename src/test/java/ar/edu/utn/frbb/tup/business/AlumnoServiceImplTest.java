package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.impl.AlumnoServiceImpl;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AlumnoServiceImplTest {
    @InjectMocks
    AlumnoServiceImpl alumnoService;

    @Mock
    AlumnoDao alumnoDao;
    @Mock
    AsignaturaService asignaturaService;
    @Test
    public void testCrearAlumnoSuccess() {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("John");
        alumnoDto.setApellido("Doe");
        alumnoDto.setDni(12345678);

        when(asignaturaService.asignaturaList()).thenReturn(new ArrayList<>());

        Alumno result = alumnoService.crearAlumno(alumnoDto);

        assertNotNull(result);
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(12345678, result.getDni());
    }

    @Test
    public void testBuscarAlumnoId_NoFeliz_AlumnoNoEncontrado() throws ResponseStatusException {
        Long idInexistente = 44444444L;
        when(alumnoDao.findAlumno(idInexistente)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen alumnos con esos datos."));

        assertThrows(ResponseStatusException.class, () -> alumnoService.buscarAlumno(idInexistente));
    }
    @Test
    void testBuscarAlumno_ok() {
        Alumno alumno = new Alumno();
        alumno.setId(55L);

        when(alumnoDao.findAlumno(55L)).thenReturn(alumno);

        Alumno result = alumnoService.buscarAlumno(55L);

        assertEquals(55L, result.getId());
        verify(alumnoDao, times(1)).findAlumno(55L);
    }

    @Test
    void testEliminarAlumno_ok() {
        Alumno alumno = new Alumno();
        alumno.setId(8L);

        when(alumnoDao.deleteAlumno(8)).thenReturn(alumno);

        Alumno result = alumnoService.eliminarAlumno(8);

        assertEquals(8L, result.getId());
        verify(alumnoDao).deleteAlumno(8);
    }

    @Test
    void testActualizarAlumno_ok() throws AlumnoNotFoundException {
        AlumnoDto dto = new AlumnoDto();
        dto.setNombre("Carlos");
        dto.setApellido("Lopez");
        dto.setDni(789L);

        Alumno alumno = new Alumno();
        alumno.setId(99L);

        when(alumnoDao.findAlumno(99L)).thenReturn(alumno);

        Alumno result = alumnoService.actualizarAlumnoPorId(99L, dto);

        assertEquals("Carlos", result.getNombre());
        assertEquals("Lopez", result.getApellido());
        verify(alumnoDao).update(eq(99L), any(Alumno.class));
    }


    @Test
    void testCursarAsignatura_noExisteAsignatura() {
        Alumno alumno = new Alumno();
        alumno.setAsignaturas(Collections.emptyList());

        when(alumnoDao.findAlumno(1L)).thenReturn(alumno);

        AsignaturaDto dto = new AsignaturaDto();
        dto.setEstado(EstadoAsignatura.CURSADA);

        assertThrows(AsignaturaNoExisteException.class,
                () -> alumnoService.cursarAsignaturaAlumnoById(1L, 99L, dto));
    }
}
