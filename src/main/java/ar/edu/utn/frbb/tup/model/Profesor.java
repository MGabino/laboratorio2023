package ar.edu.utn.frbb.tup.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Profesor {

    private long id;
    private String nombre;
    private String apellido;
    private String titulo;
    //private List<Materia> materiasProfesor;

    private List<String> materiasDictadas;

    public Profesor(String nombre, String apellido, String titulo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
    }

    public Profesor() {
        materiasDictadas = new ArrayList<>();
    }
    //public Profesor() {

    //}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido){this.apellido = apellido;}
    public String getApellido() {
        return apellido;
    }

    public void setTitulo(String titulo){this.titulo = titulo;}
    public String getTitulo() {
        return titulo;
    }

    public List<String> getMateriasDictadas() {
        return materiasDictadas;
    }

    public void setMateriasDictadas(String nuevaMateria) {
        this.materiasDictadas.add(nuevaMateria);
    }
}
