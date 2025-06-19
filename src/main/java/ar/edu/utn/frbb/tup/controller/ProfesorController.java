package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;
import ar.edu.utn.frbb.tup.controller.validator.ProfesorValidator;

@RestController
@RequestMapping("profesor")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private ProfesorValidator profesorValidator;

    @PostMapping("/")
    public Profesor crearProfesor(@RequestBody ProfesorDto profesorDto) throws YaExisteException {
        profesorValidator.validarProfesor(profesorDto);
        return profesorService.crearProfesor(profesorDto);
    }

    @GetMapping("/{idProfesor}")
    public Profesor findProfesor(@PathVariable int idProfesor) {
        return profesorService.findProfesor(idProfesor);
    }

    @DeleteMapping("/{idProfesor}")
    public Profesor deleteProfesor(@PathVariable int idProfesor) {
        return profesorService.deleteProfesor(idProfesor);
    }

}
