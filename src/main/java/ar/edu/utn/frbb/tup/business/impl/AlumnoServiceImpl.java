package ar.edu.utn.frbb.tup.business.impl;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.business.AsignaturaService;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.dto.AlumnoDto;
import ar.edu.utn.frbb.tup.model.dto.AsignaturaDto;
import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.exception.AsignaturaNoExisteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.persistence.exception.AlumnoNotFoundException;

import java.util.List;
import java.util.Random;

@Service // ESTO ERAAAA LRPM AJNDFUSIRBNFA
public class AlumnoServiceImpl implements AlumnoService {
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private AsignaturaDao asignaturaDao;
    //private static final AsignaturaService asignaturaService = new AsignaturaServiceImpl();
    @Autowired
    private AsignaturaService asignaturaService;
    @Override  //sin uso
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException, AsignaturaNoExisteException {
        Asignatura a = asignaturaService.getAsignatura(materiaId, dni);
        for (Materia m:
                a.getMateria().getCorrelatividades()) {
            Asignatura correlativa = asignaturaService.getAsignatura(m.getMateriaId(), dni);
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("La materia " + m.getNombre() + " debe estar aprobada para aprobar " + a.getNombreAsignatura());
            }
        }
        a.aprobarAsignatura(nota);
        asignaturaService.actualizarAsignatura(a);
        Alumno alumno = alumnoDao.loadAlumno(dni);
        alumno.actualizarAsignatura(a);
        alumnoDao.saveAlumno(alumno);
    }

    @Override
    public Alumno crearAlumno(AlumnoDto alumno) {
        Alumno a = new Alumno();
        a.setNombre(alumno.getNombre());
        a.setApellido(alumno.getApellido());
        a.setDni(alumno.getDni());
        List<Asignatura> asignaturasList = asignaturaService.asignaturaList(); // llamo a las asignaturas para agregarselas a alumno
        a.setAsignaturas(asignaturasList);
        Random random = new Random();
        a.setId(random.nextLong());
        alumnoDao.saveAlumno(a);
        return a;
    }

    @Override
    public Alumno buscarAlumno(String apellido) {
        return alumnoDao.findAlumno(apellido);
    }

    @Override
    public Alumno eliminarAlumno(int idAlumno) {
        return alumnoDao.deleteAlumno(idAlumno);
    }


    @Override
    public Asignatura cursarAsignaturaAlumnoById(String apellidoAlumno, long idAsignatura, AsignaturaDto asignaturaDto) throws EstadoIncorrectoException, CorrelatividadException, AsignaturaNoExisteException
        /* CorrelatividadesNoAprobadasException, AlumnoNotFoundException, , AsignaturaNotFoundException, NotaNoValidaException, CambiarEstadoAsignaturaException */ {
        final Alumno alumno = alumnoDao.findAlumno(apellidoAlumno);
        final Asignatura asignatura = asignaturaDao.getAsignaturabyId(idAsignatura);
        if (asignaturaDto.getEstado().equals(EstadoAsignatura.APROBADA)){
            alumno.aprobarAsignatura(asignatura, asignaturaDto.getNota());
        }
        else if (asignaturaDto.getEstado().equals(EstadoAsignatura.CURSADA)){
            alumno.cursarAsignatura(asignatura);
        }
        else {
            // throw new CambiarEstadoAsignaturaException
            System.out.println("La condición de la materia solo puede ser cambiada a 'Cursada' o 'Aprobada'.");
        }
        asignaturaService.actualizarAsignatura(asignatura);
        alumno.actualizarAsignatura(asignatura);
        alumnoDao.saveAlumno(alumno);
        return asignatura;
    }

}
