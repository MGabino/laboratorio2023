package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;

public interface ProfesorService {
    public Profesor buscarProfesor(long id);

    Profesor crearProfesor(ProfesorDto profesor);

    Profesor findProfesor(int idProfesor);

    Profesor deleteProfesor(int idProfesor);
}
