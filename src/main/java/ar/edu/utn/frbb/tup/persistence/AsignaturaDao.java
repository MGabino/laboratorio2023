package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;

import java.util.List;

public interface AsignaturaDao {
    void materiaToAsignatura(List<Materia> materiasList);
    List<Asignatura> getListaAsignaturas();
    Asignatura saveAsignatura(Materia m);
    Asignatura getAsignaturabyId(long idAsignatura);
}
