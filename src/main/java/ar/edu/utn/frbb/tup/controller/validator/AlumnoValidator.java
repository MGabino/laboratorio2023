package ar.edu.utn.frbb.tup.controller.validator;

import org.springframework.stereotype.Component;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;

@Component
public class AlumnoValidator {
    public void validarAlumno(AlumnoDto alumnodto) {

        if (alumnodto.getDni() <= 0) {
            throw new IllegalArgumentException("El dni del alumno no puede ser negativo o nulo");
        }

        if (alumnodto.getDni() < 10000000 || alumnodto.getDni() > 99999999) {
            throw new IllegalArgumentException("El dni del alumno debe ser de 8 digitos");
        }

        if (alumnodto.getNombre().isEmpty() || alumnodto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del alumno es obligatorio");
        }

        if (alumnodto.getApellido().isEmpty() || alumnodto.getApellido() == null) {
            throw new IllegalArgumentException("El apellido del alumno es obligatorio");
        }
    }
}
