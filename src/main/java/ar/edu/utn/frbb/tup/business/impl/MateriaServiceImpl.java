package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.MateriaService;
import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.model.dto.MateriaDto;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDaoMemoryImpl;
import ar.edu.utn.frbb.tup.persistence.exception.MateriaNotFoundException;
import ar.edu.utn.frbb.tup.persistence.exception.YaExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    private MateriaDao dao;

    @Autowired
    private ProfesorService profesorService;

    @Override                              // materia
    public Materia crearMateria(MateriaDto materiadto) throws IllegalArgumentException, MateriaNotFoundException, YaExisteException {
        Materia m = new Materia();
        m.setNombre(materiadto.getNombre());
        m.setAnio(materiadto.getAnio());
        m.setCuatrimestre(materiadto.getCuatrimestre());
        try {
            m.setProfesor(profesorService.buscarProfesor(materiadto.getProfesorId()));
        } catch (ResponseStatusException e) {
            throw new MateriaNotFoundException("Profesor no encontrado");
        }
        dao.save(m, materiadto.getCorrelatividades());
        return m;
    }

    @Override
    public List<Materia> getAllMaterias() {
        return dao.getAllMaterias();
    }

    @Override
    public Materia getMateriaById(int idMateria) throws MateriaNotFoundException {
        return dao.findById(idMateria);
    }
}
