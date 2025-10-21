package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.*;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import ar.edu.utn.frbb.tup.persistence.exception.DaoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.TypeDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
// @DependsOn("materiaService")
public class AsignaturaServiceImpl implements AsignaturaService {
    @Autowired
    MateriaService materiaService;
    //@Autowired
    //MateriaDao dao;
    @Autowired
    AsignaturaDao asignaturaDao;
    @Override
    public Asignatura getAsignatura(int materiaId, long dni) {
        return null;
    }  // acomodar para que devuelva

    @Override
    public void actualizarAsignatura(Asignatura a) throws AsignaturaNoExisteException {
        asignaturaDao.putAsignatura(a);
    }
    //@Override
    //public List<Asignatura> asignaturaList(){
    //    final List<Materia> materiaList = materiaService.getAllMaterias();
    //    asignaturaDao.materiaToAsignatura(materiaList);   // aca se crean las asignaturas desde las materias ya creada
    //    return asignaturaDao.getListaAsignaturas();
    //}

    @Override
    public List<Asignatura> asignaturaList(){
        final List<Materia> materiaList = materiaService.getAllMaterias();

        List<Asignatura> asignaturas = new ArrayList<>();

        for (Materia materia : materiaList) {
            Asignatura asignatura = new Asignatura(materia, generarIdUnico());
            asignaturas.add(asignatura);
        }

        return asignaturas;
    }

    private long generarIdUnico() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
