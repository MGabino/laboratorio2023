package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;

import java.util.*;

@Service
public class MateriaDaoMemoryImpl implements MateriaDao {

    private static final Map<Integer, Materia> repositorioMateria = new HashMap<>();
    @Override
    public Materia save(Materia materia, int[] correlatividades) throws MateriaNotFoundException, YaExisteException {
        yaExisteMateria(materia);
        Random random = new Random();
        materia.setMateriaId(random.nextInt(1,9999));
        repositorioMateria.put(materia.getMateriaId(), materia);
        final List<Materia> listaCorrelatividades = new ArrayList<>();
        for (Integer i : correlatividades) {
            Materia materia2 = findById(i);
            listaCorrelatividades.add(materia2);
        }
        materia.setCorrelatividades(listaCorrelatividades);
        return materia;
    }

    @Override
    public List<Materia> getAllMaterias() {
        final List<Materia> materiaList = new ArrayList<>();
        for (Materia materia : repositorioMateria.values()){
            materiaList.add(materia);
        }
        System.out.println(materiaList);
        return materiaList;
    }

    @Override
    public Materia findById(int idMateria) throws MateriaNotFoundException {
        for (Materia m:
             repositorioMateria.values()) {
            if (idMateria == m.getMateriaId()) {
                return m;
            }
        }
        throw new MateriaNotFoundException("No se encontró la materia con id " + idMateria);
    }
    private boolean yaExisteMateria(final Materia materia) throws YaExisteException {
        for (Materia materia1: repositorioMateria.values()){
            if (materia1.getNombre().equals(materia.getNombre())){
                throw new YaExisteException("Ya existe una materia con el nombre " + materia.getNombre());
            }
        }
        return true;
    }

}
