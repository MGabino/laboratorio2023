package ar.edu.utn.frbb.tup.business;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.business.impl.MateriaServiceImpl;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class MateriaServiceImplTest {

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Mock
    private MateriaDao dao;

    @Mock
    private ProfesorService profesorService;

    private MateriaDto materiaDto;
    private Materia materia;
    private Profesor profesor;

    @BeforeEach
    void setUp() {
        profesor = new Profesor();
        profesor.setId(1L);
        profesor.setNombre("Juan");
        profesor.setApellido("Pérez");
        profesor.setTitulo("Licenciado");

        materiaDto = new MateriaDto();
        materiaDto.setNombre("Programación");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setProfesorId(1);
        materiaDto.setCorrelatividades(List.of());

        materia = new Materia();
        materia.setMateriaId(1);
        materia.setNombre("Programación");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setProfesor(profesor);
    }

    @Test
    void testCrearMateria_Exito() throws Exception {
        when(profesorService.buscarProfesor(1L)).thenReturn(profesor);

        Materia resultado = materiaService.crearMateria(materiaDto);

        assertNotNull(resultado);
        assertEquals("Programación", resultado.getNombre());
        assertEquals(profesor, resultado.getProfesor());

        verify(profesorService, times(1)).buscarProfesor(1L);
        verify(dao, times(1)).save(any(Materia.class), eq(List.of()));
    }

    @Test
    void testCrearMateria_ProfesorNoEncontrado() throws Exception {
        doThrow(new MateriaNotFoundException("Profesor no encontrado"))
                .when(profesorService).buscarProfesor(1L);

        assertThrows(MateriaNotFoundException.class,
                () -> materiaService.crearMateria(materiaDto));

        verify(dao, never()).save(any(), any());
    }

    @Test
    void testGetAllMaterias_Exito() {
        List<Materia> lista = List.of(materia);
        when(dao.getAllMaterias()).thenReturn(lista);

        List<Materia> resultado = materiaService.getAllMaterias();

        assertEquals(1, resultado.size());
        assertEquals("Programación", resultado.get(0).getNombre());
        verify(dao, times(1)).getAllMaterias();
    }

    @Test
    void testGetMateriaById_Exito() throws Exception {
        when(dao.findById(1)).thenReturn(materia);

        Materia resultado = materiaService.getMateriaById(1);

        assertNotNull(resultado);
        assertEquals("Programación", resultado.getNombre());
        verify(dao, times(1)).findById(1);
    }

    @Test
    void testGetMateriaById_NoEncontrada() throws Exception {
        when(dao.findById(99)).thenThrow(new MateriaNotFoundException("Materia no encontrada"));

        assertThrows(MateriaNotFoundException.class,
                () -> materiaService.getMateriaById(99));

        verify(dao, times(1)).findById(99);
    }
}
