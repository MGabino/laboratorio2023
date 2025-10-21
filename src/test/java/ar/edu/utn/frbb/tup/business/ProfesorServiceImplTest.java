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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProfesorServiceImplTest {
    @InjectMocks
    ProfesorServiceImpl profesorService;

    @Mock
    ProfesorDao profesorDao;

    @Mock
    MateriaDao materiaDao;

    @Test
    public void crearProfesor() throws YaExisteException   { //DatoInvalidoException
        ProfesorDto profesorDto = new ProfesorDto();
        profesorDto.setNombre("Gabino");
        profesorDto.setApellido("Mangas");
        profesorDto.setTitulo("Tecnico");

        Profesor profesor = new Profesor("Gabino", "Mangas", "Tecnico");
        when(profesorDao.saveProfesor(profesor)).thenReturn(profesor);

        Profesor profesorCreado = profesorService.crearProfesor(profesorDto);

        assertEquals(profesorCreado.getNombre(), "Gabino");
        assertEquals(profesorCreado.getApellido(), "Mangas");
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
