package finditunsa.modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


// RESPONSABLE: INTEGRANTE 3
// MODULO: Búsqueda, coincidencias y devoluciones
// DESCRIPCION: Representa una posible coincidencia entre un reporte perdido y uno encontrado.
public class Coincidencia {
    // Identificador único de la coincidencia en el sistema
    private int id;

    // Referencia al reporte del usuario que perdió el objeto
    private ReportePerdido reportePerdido;

    // Referencia al reporte del usuario/administrador que encontró el objeto
    private ReporteEncontrado reporteEncontrado;

    // Nivel o porcentaje de similitud calculado entre ambos reportes
    private int puntaje;

    // Lista de rasgos específicos que coinciden
    private List<String> caracteristicasCoincidentes;

    // Marca de tiempo que registra cuándo se detectó la coincidencia
    private LocalDateTime fechaDeteccion;

    // Estado de validación (true si el usuario o admin confirmó que sí se trata del mismo objeto)
    private boolean confirmada;

    //Constructor principal de la clase Coincidencia.
    public Coincidencia(int id, ReportePerdido reportePerdido,
                        ReporteEncontrado reporteEncontrado,
                        int puntaje, List<String> caracteristicasCoincidentes) {
        this.id = id;
        this.reportePerdido = reportePerdido;
        this.reporteEncontrado = reporteEncontrado;
        this.puntaje = puntaje;
        
        // Copia defensiva: Evita guardar directamente la referencia de la lista externa (previene Modifiable-State bug)
        this.caracteristicasCoincidentes = caracteristicasCoincidentes != null
                ? new ArrayList<>(caracteristicasCoincidentes)
                : new ArrayList<>();

        // Se asigna la fecha y hora actual del sistema al momento de instanciar
        this.fechaDeteccion = LocalDateTime.now();
        
        // Toda nueva coincidencia inicia por defecto en estado no confirmada (false)
        this.confirmada = false;
    }

    public int getId() {
        return id;
    }

    public ReportePerdido getReportePerdido() {
        return reportePerdido;
    }

    public ReporteEncontrado getReporteEncontrado() {
        return reporteEncontrado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public List<String> getCaracteristicasCoincidentes() {
        return new ArrayList<>(caracteristicasCoincidentes);
    }

    public LocalDateTime getFechaDeteccion() {
        return fechaDeteccion;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void confirmar() {
        this.confirmada = true;
    }

    @Override
    public String toString() {
        return "Coincidencia #" + id + " | Puntaje: " + puntaje;
    }
}
