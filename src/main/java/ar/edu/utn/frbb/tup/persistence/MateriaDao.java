package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

import java.util.List;

public interface MateriaDao {
    Materia save(Materia materia, List<Integer> correlatividades) throws MateriaNotFoundException, YaExisteException;

    List<Materia> getAllMaterias();

    Materia findById(int idMateria) throws MateriaNotFoundException;
}
