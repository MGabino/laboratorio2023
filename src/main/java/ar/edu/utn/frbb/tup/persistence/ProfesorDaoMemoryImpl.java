package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao{
    private static Map<Long, Profesor> repositorioProfesores = new HashMap<>();


    @Override
    public Profesor saveProfesor(Profesor profesor) throws YaExisteException {
        profesorYaCreado(profesor);
        Random random = new Random();
        profesor.setId(random.nextLong(1, 999999));
        return repositorioProfesores.put(profesor.getId(), profesor);
    }

    @Override
    public Profesor findProfesor(long idProfesor) {
        for (Profesor p: repositorioProfesores.values()) {
            if (p.getId() == idProfesor){
                return p;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen profesores con esos datos."
        );
    }

    @Override
    public Profesor deleteProfesor(int idProfesor) {
        for (Profesor p: repositorioProfesores.values()) {
            if (idProfesor == p.getId()){
                repositorioProfesores.values().remove(p);
                System.out.println("profesor eliminado");
                System.out.println(repositorioProfesores);
                return p;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen profesores con esos datos para eliminarlo."
        );
    }

    //@Override
    //public Profesor get(long id) {
    //        return new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación");
    //}
    private boolean profesorYaCreado(final Profesor profesor) throws YaExisteException {
        for (Profesor profesor1 : repositorioProfesores.values()) {
            if (profesor.getNombre().equals(profesor1.getNombre()) &&
                    profesor.getApellido().equals(profesor1.getApellido())) {
                throw new YaExisteException("Ya existe un profesor con los datos ingresados [" +
                        profesor.getNombre() + " " + profesor.getApellido() + "].");
            }
        }
        return false;
    }

    @Override
    public void update(final Long id, final Profesor p) {
        repositorioProfesores.put(id, p);
    }

    public void clear() {
        repositorioProfesores.clear();
    }

}
