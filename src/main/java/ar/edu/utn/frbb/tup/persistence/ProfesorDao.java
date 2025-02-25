package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

public interface ProfesorDao {
    // public Profesor get(long id);

    Profesor saveProfesor(Profesor p) throws YaExisteException;

    Profesor findProfesor(long idProfesor);

    Profesor deleteProfesor(int idProfesor);
}
