package ar.edu.utn.frbb.tup.model;




import ar.edu.utn.frbb.tup.model.exception.AsignaturaInexistenteException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadException;
import ar.edu.utn.frbb.tup.model.exception.CorrelatividadesNoAprobadasException;
import ar.edu.utn.frbb.tup.model.exception.EstadoIncorrectoException;

import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private long id;
    private String nombre;
    private String apellido;
    private long dni;
    private List<Asignatura> asignaturas;


    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }  // modi 1

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }  // .

    public Alumno() {
    }
    public Alumno(String nombre, String apellido, long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        asignaturas = new ArrayList<>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public long getDni() {
        return dni;
    }

    public void agregarAsignatura(Asignatura a){
        this.asignaturas.add(a);
    }

    public List<Asignatura> obtenerListaAsignaturas(){
        return this.asignaturas;
    }

    public void cursarAsignatura (Asignatura asignatura) throws CorrelatividadException /* throws CambiarEstadoAsignaturaException, AsignaturaNotFoundException, CorrelatividadesNoAprobadasException */{
        existeAsignatura(asignatura);
        for (Materia correlativa :
                asignatura.getCorrelatividades()) {
            chequearCorrelatividad(correlativa, asignatura);
        }
        asignatura.cursarAsignatura();
    }

    private void existeAsignatura(Asignatura asignatura) {
        if(!asignaturas.contains(asignatura)) {
            System.out.println("El alumno " + this.nombre + " " + this.apellido + " (ID: " + this.id + "), no tiene " +
                    "a la asignatura: " + asignatura.getNombreAsignatura());
        }
    }
    public void aprobarAsignatura(Asignatura asignatura, int nota) throws CorrelatividadException, EstadoIncorrectoException {
        existeAsignatura(asignatura);
        for (Materia correlativa :
                asignatura.getCorrelatividades()) {
            chequearCorrelatividad(correlativa, asignatura);
        }
        asignatura.aprobarAsignatura(nota);
    }

    private void chequearCorrelatividad(Materia correlativa, Asignatura asignatura) throws CorrelatividadException {
        for (Asignatura a:
                asignaturas) {
            if (correlativa.getNombre().equals(a.getNombreAsignatura())) {
                if (!EstadoAsignatura.APROBADA.equals(a.getEstado())) {
                    throw new CorrelatividadException("La asignatura " + correlativa.getNombre() + " [ID: " + a.getAsignaturaId() + "] debe estar aprobada para cursar/aprobar " + asignatura.getNombreAsignatura());
                }
            }
        }
    }

    private Asignatura getAsignaturaAAprobar(Asignatura asignatura) throws AsignaturaInexistenteException {

        for (Asignatura a: asignaturas) {
            if (asignatura.getNombreAsignatura().equals(a.getNombreAsignatura())) {
                return a;
            }
        }
        throw new AsignaturaInexistenteException("No se encontró la materia");
    }

    public boolean puedeAprobar(Asignatura asignatura) {
        return true;
    }


    public void actualizarAsignatura(Asignatura asignatura) {  // si la asig pasa como cursada no se establece la nota, si para como aprobada si
        for (Asignatura a:
                asignaturas) {
            if (a.getNombreAsignatura().equals(asignatura.getNombreAsignatura())) {
                if (asignatura.getNota().isPresent() || asignatura.getEstado().equals(EstadoAsignatura.APROBADA)){
                    a.setNota(asignatura.getNota().get());
                }
                a.setEstado(asignatura.getEstado());
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
