package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.controller.handler.UtnResponseEntityExceptionHandler;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.controller.validator.ProfesorValidator;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class ProfesorControllerTest {

    @InjectMocks
    ProfesorController profesorController;

    @Mock
    ProfesorService profesorService;

    @Mock
    ProfesorValidator profesorValidator;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(profesorController)
                .setControllerAdvice(UtnResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    public void buscarProfesorPorIdCorrecto() throws Exception {
        Profesor profesor = new Profesor();
        profesor.setId(1);
        profesor.setNombre("Juan");
        profesor.setApellido("Pérez");
        profesor.setTitulo("Licenciado");
        profesor.setMateriasDictadas(List.of("Matemática"));

        when(profesorService.findProfesor(anyInt())).thenReturn(profesor);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profesor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(profesor)))
                .andReturn();

        assertEquals(profesor, mapper.readValue(result.getResponse().getContentAsString(), Profesor.class));
    }
    @Test
    void testFindProfesor_notFound() throws Exception {
        when(profesorService.findProfesor(9))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND
                ));

        mockMvc.perform(MockMvcRequestBuilders.get("/profesor/9"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testCrearProfesor_ok() throws Exception {
        ProfesorDto dto = new ProfesorDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setTitulo("Licenciado");

        Profesor profesor = new Profesor();
        profesor.setId(10L);

        doNothing().when(profesorValidator).validarProfesor(any());
        when(profesorService.crearProfesor(any())).thenReturn(profesor);

        mockMvc.perform(MockMvcRequestBuilders.post("/profesor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L));
    }
    @Test
    void testCrearProfesor_badRequest_porValidator() throws Exception {
        ProfesorDto dto = new ProfesorDto();

        doThrow(new IllegalArgumentException("Error en datos"))
                .when(profesorValidator).validarProfesor(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/profesor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }


    @Test
    void testDeleteProfesor_ok() throws Exception {
        Profesor profesor = new Profesor();
        profesor.setId(7);

        when(profesorService.deleteProfesor(7)).thenReturn(profesor);

        mockMvc.perform(MockMvcRequestBuilders.delete("/profesor/7"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(profesor)));
    }


    @Test
    void testDeleteProfesor_notFound() throws Exception {
        when(profesorService.deleteProfesor(100))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND
                ));

        mockMvc.perform(MockMvcRequestBuilders.delete("/profesor/100"))
                .andExpect(status().isNotFound());
    }

}
