package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;

public interface AlumnoService {
    void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException;

    Alumno crearAlumno(AlumnoDto alumno);

    Alumno buscarAlumno(String apellidoAlumno);

    Alumno eliminarAlumno(int idAlumno);

    Asignatura cursarAsignaturaAlumnoById(String apellidoAlumno, long idAsignatura, AsignaturaDto asignaturaDto) throws CorrelatividadesNoAprobadasException,
            AlumnoNotFoundException, AsignaturaInexistenteException, CorrelatividadException, EstadoIncorrectoException;
}
