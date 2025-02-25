package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.business.impl.ProfesorServiceImpl;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
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
}
