package ar.edu.utn.frbb.tup.model.dto;

import ar.edu.utn.frbb.tup.model.EstadoAsignatura;

public class AsignaturaDto {
    private Integer nota;
    private EstadoAsignatura estado;

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }
}
