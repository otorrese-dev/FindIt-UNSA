package finditunsa.modelos;

import java.time.LocalDateTime;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Clase abstracta base para reportes perdidos y encontrados.

public abstract class Reporte {
    private int id;
    private Usuario usuario;
    private Objeto objeto;
    private LocalDateTime fechaRegistro;
    private EstadoReporte estado;

    public Reporte() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoReporte.REGISTRADO;
    }

    public Reporte(int id, Usuario usuario, Objeto objeto,
                   LocalDateTime fechaRegistro, EstadoReporte estado) {
        this.id = id;
        this.usuario = usuario;
        this.objeto = objeto;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDateTime.now();
        this.estado = estado != null ? estado : EstadoReporte.REGISTRADO;
    }

    public abstract String getTipoReporte();

    public String getDescripcionCorta() {
        return getTipoReporte() + " #" + id + " - " + (objeto != null ? objeto.getNombre() : "Sin objeto");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void setEstado(EstadoReporte estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return getDescripcionCorta() + " | Estado: " + estado;
    }
}