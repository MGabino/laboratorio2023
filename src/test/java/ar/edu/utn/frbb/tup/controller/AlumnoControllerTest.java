package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.controller.validator.AlumnoValidator;
import ar.edu.utn.frbb.tup.controller.validator.AsignaturaValidator;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
public class AlumnoControllerTest {

    @InjectMocks
    private AlumnoController alumnoController;

    @Mock
    private AlumnoService alumnoService;

    @Mock
    private AlumnoValidator alumnoValidator;

    @Mock
    private AsignaturaValidator asignaturaValidator;

    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();
    }


    @Test
    void testCrearAlumno_ok() throws Exception {
        AlumnoDto dto = new AlumnoDto();
        dto.setNombre("Juan");
        dto.setApellido("Pérez");

        Alumno alumno = new Alumno();
        alumno.setId(1L);

        Mockito.doNothing().when(alumnoValidator).validarAlumno(any());
        Mockito.when(alumnoService.crearAlumno(any())).thenReturn(alumno);

        mockMvc.perform(MockMvcRequestBuilders.post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCrearAlumno_badRequest() throws Exception {
        AlumnoDto dto = new AlumnoDto();

        Mockito.doThrow(new IllegalArgumentException("error"))
                .when(alumnoValidator).validarAlumno(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testBuscarAlumno_ok() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setId(5L);

        Mockito.when(alumnoService.buscarAlumno(5L)).thenReturn(alumno);

        mockMvc.perform(MockMvcRequestBuilders.get("/alumno/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));
    }


    @Test
    void testEliminarAlumno_ok() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setId(3L);

        Mockito.when(alumnoService.eliminarAlumno(3)).thenReturn(alumno);

        mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L));
    }


    @Test
    void testActualizarAlumno_ok() throws Exception {
        AlumnoDto dto = new AlumnoDto();
        dto.setNombre("Carlos");

        Alumno alumno = new Alumno();
        alumno.setId(99L);

        Mockito.doNothing().when(alumnoValidator).validarAlumno(any());
        Mockito.when(alumnoService.actualizarAlumnoPorId(eq(99L), any(AlumnoDto.class)))
                .thenReturn(alumno);

        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99L));
    }

    @Test
    void testActualizarAlumno_badRequest() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Error"))
                .when(alumnoValidator).validarAlumno(any());

        AlumnoDto dto = new AlumnoDto();

        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }



}
