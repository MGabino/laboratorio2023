package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.impl.AlumnoServiceImpl;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
}
