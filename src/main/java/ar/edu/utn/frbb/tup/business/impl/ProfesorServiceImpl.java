package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.ProfesorService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.dto.ProfesorDto;
import ar.edu.utn.frbb.tup.persistence.*;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDaoMemoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private static final ProfesorDao profesorDao = new ProfesorDaoMemoryImpl();
    @Autowired
    private ProfesorDao dao;

    @Override
    public Profesor buscarProfesor(long id) {
        return dao.findProfesor(id);
    }

    @Override
    public Profesor crearProfesor(ProfesorDto profesorDto) {
        Profesor profesor = new Profesor();
        profesor.setTitulo(profesorDto.getTitulo());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setNombre(profesorDto.getNombre());
        Random random = new Random();
        profesor.setId(random.nextLong());
        profesorDao.saveProfesor(profesor);
        return profesor;
    }

    @Override
    public Profesor findProfesor(int idProfesor) {
        return profesorDao.findProfesor(idProfesor);
    }

    @Override
    public Profesor deleteProfesor(int idProfesor) {
        return profesorDao.deleteProfesor(idProfesor);
    }


}
