package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;

import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{apellido}/asignatura/{idAsignatura}")
    public ResponseEntity<Asignatura> cursarAsignaturaAlumnoById(@PathVariable String apellido, @PathVariable long idAsignatura, @RequestBody AsignaturaDto asignaturaDto)
            throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AsignaturaInexistenteException, CorrelatividadException {
        try {
            Asignatura asignatura = alumnoService.cursarAsignaturaAlumnoById(apellido, idAsignatura, asignaturaDto);
            return ResponseEntity.ok(asignatura);
        } catch (AlumnoNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EstadoIncorrectoException e) {
            return ResponseEntity.badRequest().build(); // Manejar otra excepción si es necesario
        }
    }
}
