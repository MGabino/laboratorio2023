package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Profesor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ProfesorDaoMemoryImpl implements ProfesorDao{
    private static Map<Long, Profesor> repositorioProfesores = new HashMap<>();
    @Override
    public Profesor saveProfesor(Profesor profesor) {
        Random random = new Random();
        profesor.setId(random.nextLong(1, 999999));
        return repositorioProfesores.put(profesor.getId(), profesor);
    }

    @Override
    public Profesor findProfesor(int idProfesor) {
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
                repositorioProfesores.remove(p);
                System.out.println("El alumno fue eliminado exitosamente");
                System.out.println(repositorioProfesores);
                return p;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No existen profesor con esos datos para eliminarlo."
        );
    }
    @Override
    public Profesor get(long id) {
            return new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación");
    }


}
