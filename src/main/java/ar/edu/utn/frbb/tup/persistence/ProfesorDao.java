package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Profesor;

public interface ProfesorDao {
    // public Profesor get(long id);

    Profesor saveProfesor(Profesor p);

    Profesor findProfesor(long idProfesor);

    Profesor deleteProfesor(int idProfesor);
}
