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

import java.util.Collections;
import java.util.List;


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
    @Override
    public List<Asignatura> asignaturaList(){
        final List<Materia> materiaList = materiaService.getAllMaterias();
        asignaturaDao.materiaToAsignatura(materiaList);   // aca se crean las asignaturas desde las materias ya creada
        return asignaturaDao.getListaAsignaturas();
    }

    //@Override
    //public List<Asignatura> asignaturaList() {
    //    List<Materia> materiaList;
    //    try {
    //        materiaList = dao.getAllMaterias();
    //    } catch (NullPointerException e) {
    //        System.out.println("Error al obtener la lista de materias");  // Manejar el caso en el que materiaService o getAllMaterias() es nulo
    //        return Collections.emptyList(); // O lanzar una excepción personalizada
    //    }

    //    try {
    //        asignaturaDao.materiaToAsignatura(materiaList);
    //    } catch (Exception e) {
    //        // Manejar cualquier excepción que pueda ocurrir en materiaToAsignatura
    //        System.out.println("Error al convertir materias a asignaturas");
    //    }
    //    return asignaturaDao.getListaAsignaturas();
    //}
}
