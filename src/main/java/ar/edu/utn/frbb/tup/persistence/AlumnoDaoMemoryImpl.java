package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class AlumnoDaoMemoryImpl implements AlumnoDao {

    private static Map<Long, Alumno> repositorioAlumnos = new HashMap<>();

    @Override
    public Alumno saveAlumno(Alumno alumno) {
        Random random = new Random();
        alumno.setId(random.nextLong(1, 999999));
        return repositorioAlumnos.put(alumno.getId(), alumno);
    }

    @Override
    public Alumno findAlumno(final Long id) throws ResponseStatusException {
        Alumno alumno = repositorioAlumnos.get(id);
        if (alumno == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existen alumnos con esos datos.");
        }
        return alumno;
    }

    // throw new ResponseStatusException(
    //                HttpStatus.NOT_FOUND, "No existen alumnos con esos datos."
    //        );
    @Override
    public Alumno deleteAlumno(int idAlumno) {
        for (Alumno a: repositorioAlumnos.values()) {
            if (idAlumno == a.getId()){
                repositorioAlumnos.values().remove(a);
                System.out.println("El alumno fue eliminado exitosamente");
                System.out.println(repositorioAlumnos);
                return a;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen alumnos con esos datos para eliminarlo."
        );
    }

    @Override
    public Alumno loadAlumno(Long dni) {
        return null;
    }

    @Override
    public void update(final Long id, final Alumno alumno) {

        repositorioAlumnos.put(id, alumno);
    }

    public void clear() {
        repositorioAlumnos.clear();
    }
}
