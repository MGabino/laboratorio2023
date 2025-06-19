package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.stereotype.Component;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;

@Component
public class AsignaturaValidator {

    public void validarAsignatura(AsignaturaDto asignaturadto) {

        if (asignaturadto.getEstado() == null) {
            throw new IllegalArgumentException("El estado del alumno en la asignatura es obligatorio");
        }

        if (asignaturadto.getNota() < 0) {
            throw new IllegalArgumentException("La nota del alumno en la asignatura no puede ser negativa");
        }

        if (asignaturadto.getNota() > 10) {
            throw new IllegalArgumentException("La nota del alumno en la asignatura no puede ser mayor a 10");
        }

    }
}