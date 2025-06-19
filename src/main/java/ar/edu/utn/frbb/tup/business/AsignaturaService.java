package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;

import java.util.List;

public interface AsignaturaService {
    Asignatura getAsignatura(int materiaId, long dni);

    void actualizarAsignatura(Asignatura a) throws AsignaturaNoExisteException;
    List<Asignatura> asignaturaList();
}
