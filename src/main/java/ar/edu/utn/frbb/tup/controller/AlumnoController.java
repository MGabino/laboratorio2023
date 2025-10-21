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
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frbb.tup.controller.validator.AlumnoValidator;
import ar.edu.utn.frbb.tup.controller.validator.AsignaturaValidator;

@RestController
@RequestMapping("alumno")
public class AlumnoController {
    @Autowired
    MateriaService materiaService;
    @Autowired
    private AlumnoValidator alumnoValidator;

    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private AsignaturaValidator asignaturaValidator;
    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {
        alumnoValidator.validarAlumno(alumnoDto);
        return alumnoService.crearAlumno(alumnoDto);
    }
    @GetMapping("/{idAlumno}")
    public Alumno buscarAlumno(@PathVariable Long idAlumno) {

       return alumnoService.buscarAlumno(idAlumno);

    }

    @DeleteMapping("/{idAlumno}")
    public Alumno eliminarAlumno(@PathVariable Integer idAlumno) {
        return alumnoService.eliminarAlumno(idAlumno);
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public ResponseEntity<Asignatura> cursarAsignaturaAlumnoById(@PathVariable Long idAlumno, @PathVariable long idAsignatura, @RequestBody AsignaturaDto asignaturaDto)
            throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AsignaturaInexistenteException, CorrelatividadException, AsignaturaNoExisteException {
        asignaturaValidator.validarAsignatura(asignaturaDto);
        try {
            Asignatura asignatura = alumnoService.cursarAsignaturaAlumnoById(idAlumno, idAsignatura, asignaturaDto);
            return ResponseEntity.ok(asignatura);
        } catch (AlumnoNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EstadoIncorrectoException e) {
            return ResponseEntity.badRequest().build(); // Manejar otra excepción si es necesario
        }
    }

    @PutMapping("/{idAlumno}")
    public Alumno actualizarAlumnoPorId(@PathVariable("idAlumno") Long idAlumno,
                                        @RequestBody AlumnoDto alumnoDto) throws AlumnoNotFoundException {
        alumnoValidator.validarAlumno(alumnoDto);
        return alumnoService.actualizarAlumnoPorId(idAlumno, alumnoDto);
    }
}
