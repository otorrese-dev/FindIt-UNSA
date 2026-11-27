package finditunsa.modelos;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Devolucion implements Serializable {
    // Atributos
    private int id;
    private ReportePerdido reportePerdido;
    private ReporteEncontrado reporteEncontrado;
    private Usuario solicitante;
    private EstadoDevolucion estado;
    private String observaciones;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaEntrega;

    // Constructor sobrecargado
    public Devolucion(int id, ReportePerdido reportePerdido, ReporteEncontrado reporteEncontrado,
                      Usuario solicitante, String observaciones) {
        this.id = id;
        this.reportePerdido = reportePerdido;
        this.reporteEncontrado = reporteEncontrado;
        this.solicitante = solicitante;
        this.observaciones = observaciones != null ? observaciones : "";
        this.estado = EstadoDevolucion.SOLICITADA;
        this.fechaSolicitud = LocalDateTime.now();
    }

    // Getters y Setters
    public int getId() {
        return id; 
    }
    public ReportePerdido getReportePerdido() { 
        return reportePerdido; 
    }
    public ReporteEncontrado getReporteEncontrado() { 
        return reporteEncontrado; 
    }
    public Usuario getSolicitante() { 
        return solicitante; 
    }
    public EstadoDevolucion getEstado() { 
        return estado; 
    }
    public void setEstado(EstadoDevolucion estado) { 
        this.estado = estado; 
    }
    public String getObservaciones() { 
        return observaciones; 
    }
    public LocalDateTime getFechaSolicitud() { 
        return fechaSolicitud; 
    }
    public LocalDateTime getFechaEntrega() { 
        return fechaEntrega; 
    }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { 
        this.fechaEntrega = fechaEntrega; 
    }
}
