package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.model.dto.MateriaDto;

@Component
public class MateriaValidator {
    public void validarMateria(MateriaDto materiadto) {

        if (materiadto.getNombre().isEmpty() || materiadto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre de la materia es obligatorio");
        }

        if (materiadto.getProfesorId() <= 0) {
            throw new IllegalArgumentException("El idProfesor no puede ser negativo o nulo");

        }

        if (materiadto.getAnio() <= 0) {
            throw new IllegalArgumentException("El año no puede ser negativo o nulo");

        }

        if (materiadto.getCuatrimestre() <= 0) {
            throw new IllegalArgumentException("El cuatrimestre no puede ser negativo o nulo");

        }
    }
}
