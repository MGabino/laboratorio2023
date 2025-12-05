package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.impl.ProfesorServiceImpl;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProfesorServiceImplTest {
    @InjectMocks
    ProfesorServiceImpl profesorService;

    @Mock
    ProfesorDao dao;

    @Mock
    MateriaDao materiaDao;

    @Test
    public void crearProfesor() throws YaExisteException   { //DatoInvalidoException
        ProfesorDto profesorDto = new ProfesorDto();
        profesorDto.setNombre("Gabino");
        profesorDto.setApellido("Mangas");
        profesorDto.setTitulo("Tecnico");

        Profesor profesor = new Profesor("Gabino", "Mangas", "Tecnico");
        when(dao.saveProfesor(profesor)).thenReturn(profesor);

        Profesor profesorCreado = profesorService.crearProfesor(profesorDto);

        assertEquals(profesorCreado.getNombre(), "Gabino");
        assertEquals(profesorCreado.getApellido(), "Mangas");
    }


    @Test
    void testBuscarProfesor() {
        Profesor profesor = new Profesor();
        profesor.setId(10L);
        profesor.setNombre("Carlos");

        when(dao.findProfesor(10L)).thenReturn(profesor);

        Profesor result = profesorService.buscarProfesor(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Carlos", result.getNombre());

        verify(dao).findProfesor(10L);
    }


    @Test
    void testFindProfesor_OrdenaMateriasDictadas() {

        Profesor profesor = new Profesor();
        profesor.setId(1);
        profesor.setNombre("Juan");
        profesor.setApellido("Gomez");
        profesor.setTitulo("Licenciado");
        profesor.setMateriasDictadas(new ArrayList<>(List.of(
                "Laboratorio",
                "Algebra",
                "Matematica"
        )));

        when(dao.findProfesor(1)).thenReturn(profesor);

        Profesor result = profesorService.findProfesor(1);

        assertEquals(3, result.getMateriasDictadas().size());

        assertEquals(List.of("Algebra", "Matematica", "Laboratorio"), result.getMateriasDictadas());
    }


    @Test
    void testDeleteProfesor() {
        Profesor profesor = new Profesor();
        profesor.setId(20L);

        when(dao.deleteProfesor(20)).thenReturn(profesor);

        Profesor result = profesorService.deleteProfesor(20);

        assertNotNull(result);
        assertEquals(20L, result.getId());

        verify(dao).deleteProfesor(20);
    }


    @Test
    void testActualizarProfesorPorId() {
        Profesor prof = new Profesor();
        prof.setId(30L);
        prof.setNombre("Viejo");
        prof.setApellido("Profesor");
        prof.setTitulo("Lic.");

        ProfesorDto dto = new ProfesorDto();
        dto.setNombre("Nuevo");
        dto.setApellido("Actualizado");
        dto.setTitulo("Dr.");

        when(dao.findProfesor(30L)).thenReturn(prof);

        Profesor result = profesorService.actualizarProfesorPorId(30L, dto);

        assertEquals("Nuevo", result.getNombre());
        assertEquals("Actualizado", result.getApellido());
        assertEquals("Dr.", result.getTitulo());
        assertEquals(30L, result.getId());

        verify(dao).update(30L, result);
    }

    //@Test
    //public void buscarProfesorPorIdIncorrecto() throws ProfesorNotFoundException {
    //    Profesor profesor = new Profesor("Gabino", "Mangas", "Tecnico");
    //    when(profesorDao.findProfesor(1)).thenThrow(new ProfesorNotFoundException("No se pudo encontrar un profesor con el ID: 1."));//    assertThrows(ProfesorNotFoundException.class, () -> {
    //        profesorService.findProfesor(1L);
    //    });
    //}
    //@Test
    //public void borrarProfesorPorId() throws ProfesorNotFoundException, ProfesorEliminadoCorrectamente {
    //    List<Materia> materiasDictadas = new ArrayList<>();
    //    materiasDictadas.add(new Materia("Materia 1", 1, 1, new Profesor()));
    //    when(profesorDao.getMateriasDictadas(1L)).thenReturn(materiasDictadas);

    //    profesorService.deleteProfesor(1L);

    //    for (Materia materia : materiasDictadas) {
    //        verify(materiaDao, times(1)).deleteMateriaById(materia.getMateriaId());
    //    }

    //    verify(profesorDao, times(1)).deleteProfesorById(1L);
    //}
}
