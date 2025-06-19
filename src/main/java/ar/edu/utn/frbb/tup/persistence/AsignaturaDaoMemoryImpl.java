package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import java.util.*;
@Service
public class AsignaturaDaoMemoryImpl implements AsignaturaDao {
    private static final Map<Long, Asignatura> repositorioAsignaturas  = new HashMap<>();
    @Override
    public void materiaToAsignatura(List<Materia> materiasList) {
        final List<Asignatura> listaAsignaturas = new ArrayList<>();
        for (Materia materia : materiasList){
            saveAsignatura(materia);
        }
    }

    @Override
    public Asignatura saveAsignatura(Materia materia){
        Random random = new Random();
        Asignatura asignatura = new Asignatura(materia, random.nextLong(1, 99999));
        repositorioAsignaturas.put(asignatura.getAsignaturaId(), asignatura);

        return asignatura;
    }

    @Override
    public List<Asignatura> getListaAsignaturas() {
        final List<Asignatura> listaAsignaturas = new ArrayList<>();
        for (Asignatura asignatura : repositorioAsignaturas.values()){
            listaAsignaturas.add(asignatura);
        }
        return listaAsignaturas;
    }

    public Asignatura getAsignaturabyId(long idAsingatura) throws AsignaturaNoExisteException{
        Asignatura asignatura = repositorioAsignaturas.get(idAsingatura);
        if (asignatura == null){
            throw new AsignaturaNoExisteException("No se encuentra ninguna asignatura con el ID: " + idAsingatura);
        }
        return asignatura;
    }

    @Override
    public void putAsignatura(final Asignatura asignatura) throws AsignaturaNoExisteException {
        Asignatura newAsignatura = getAsignaturabyId(asignatura.getAsignaturaId());
        repositorioAsignaturas.put(newAsignatura.getAsignaturaId(), newAsignatura);
    }
}
