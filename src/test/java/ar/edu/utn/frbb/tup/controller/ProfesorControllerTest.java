package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.controller.handler.UtnResponseEntityExceptionHandler;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ProfesorControllerTest {

    @InjectMocks
    ProfesorController profesorController;

    @Mock
    ProfesorService profesorService;

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

        Mockito.when(profesorService.findProfesor(Math.toIntExact(any(Long.class)))).thenReturn(profesor);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/profesor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(profesor)))
                .andReturn();

        assertEquals(profesor, mapper.readValue(result.getResponse().getContentAsString(), Profesor.class));
    }

}
