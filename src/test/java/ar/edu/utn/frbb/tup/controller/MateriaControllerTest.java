package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.App;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.controller.validator.MateriaValidator;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class MateriaControllerTest {

    @InjectMocks
    MateriaController materiaController;

    @Mock
    MateriaService materiaService;

    @Mock
    MateriaValidator materiaValidator;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(materiaController).build();
    }

    @Test
    public void crearMateriaTest() throws Exception {
        when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(2);
        materiaDto.setNombre("Laboratorio II");
        materiaDto.setProfesorId(345);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(new Materia(), mapper.readValue(result.getResponse().getContentAsString(), Materia.class));
    }

    @Test
    public void testCrearMateriaBadRequest() throws Exception {

        when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new Materia());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/materia/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\" : \"Laboratorio II\",\n" +
                                "    \"anio\" : \"segundo\", \n" +
                                "    \"cuatrimestre\" : 1,\n" +
                                "    \"profesorId\" : 2 \n"+
                                "}")
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();

    }


    @Test
    public void testGetAllMaterias_ok() throws Exception {

        Materia m1 = new Materia();
        m1.setMateriaId(1);
        m1.setNombre("Programacion");

        Materia m2 = new Materia();
        m2.setMateriaId(2);
        m2.setNombre("Laboratorio");

        List<Materia> materias = Arrays.asList(m1, m2);

        when(materiaService.getAllMaterias()).thenReturn(materias);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(mapper.writeValueAsString(materias),
                result.getResponse().getContentAsString());
    }


    @Test
    public void testGetMateriaById_notFound() throws Exception {

        when(materiaService.getMateriaById(22))
                .thenThrow(new MateriaNotFoundException("No existe"));

        mockMvc.perform(MockMvcRequestBuilders.get("/materia/22")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
