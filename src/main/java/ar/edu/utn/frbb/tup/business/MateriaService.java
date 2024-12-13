package ar.edu.utn.frbb.tup.business;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MateriaService {
    Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException;

    List<Materia> getAllMaterias();

    Materia getMateriaById(int idMateria) throws MateriaNotFoundException;
}
