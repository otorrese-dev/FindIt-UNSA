package finditunsa.modelos;

import java.time.LocalDateTime;

// RESPONSABLE: INTEGRANTE 3
// MODULO: Búsqueda, coincidencias y devoluciones
// DESCRIPCION: Registra la devolución de un objeto recuperado.
public class Devolucion {
    // Identificador único de la devolución en el sistema
    private int id;

    // Referencia al reporte del objeto que fue extraviado
    private ReportePerdido reportePerdido;

    // Referencia al reporte del objeto hallado
    private ReporteEncontrado reporteEncontrado;

    // Usuario
    private Usuario registradoPor;

    // Marca de tiempo con la fecha y hora exacta en que se registró la entrega
    private LocalDateTime fechaDevolucion;

    // Notas adicionales sobre la entrega
    private String observaciones;

    // Constructor principal para registrar una entrega/devolución.
    public Devolucion(int id, ReportePerdido reportePerdido,
                      ReporteEncontrado reporteEncontrado,
                      Usuario registradoPor, String observaciones) {
        this.id = id;
        this.reportePerdido = reportePerdido;
        this.reporteEncontrado = reporteEncontrado;
        this.registradoPor = registradoPor;

        // Asigna de forma automática la fecha y hora actual del sistema al momento de la entrega
        this.fechaDevolucion = LocalDateTime.now();

        // Protección contra NullPointer: si no se pasan observaciones, se asigna una cadena vacía
        this.observaciones = observaciones != null ? observaciones : "";
    }

    // Getters
    public int getId() {
        return id;
    }

    public ReportePerdido getReportePerdido() {
        return reportePerdido;
    }

    public ReporteEncontrado getReporteEncontrado() {
        return reporteEncontrado;
    }

    public Usuario getRegistradoPor() {
        return registradoPor;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    @Override
    public String toString() {
        return "Devolucion #" + id +
                " | Perdido: " + reportePerdido.getId() +
                " | Encontrado: " + reporteEncontrado.getId() +
                " | Registrado por: " + (registradoPor != null ? registradoPor.getNombre() : "N/A");
    }
}
