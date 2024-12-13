package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alumno")
public class AlumnoController {
    @Autowired
    MateriaService materiaService;

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {

        return alumnoService.crearAlumno(alumnoDto);

    }
    @GetMapping("/{apellido}")
    public Alumno buscarAlumno(@PathVariable String apellido) {

       return alumnoService.buscarAlumno(apellido);

    }



    @DeleteMapping("/{idAlumno}")
    public Alumno eliminarAlumno(@PathVariable Integer idAlumno) {
        return alumnoService.eliminarAlumno(idAlumno);
    }
}
