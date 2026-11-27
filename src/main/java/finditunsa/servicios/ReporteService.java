package finditunsa.servicios;

import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.archivos.GestorArchivosTexto;
import finditunsa.modelos.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Persistencia de Reportes y Archivos de Texto
//DESCRIPCION: Servicio de reportes conectado a persistencia de objetos y texto.
 
public class ReporteService {
    private final List<Reporte> reportes;
    private final DatosSistema datosSistema;

    // El servicio ahora recibe el contenedor global DatosSistema
    public ReporteService(DatosSistema datos) {
        this.datosSistema = datos;
        this.reportes = datos.reportes;
    }

    // Calcula el siguiente ID basándose en los datos cargados del archivo
    private int getSiguienteId() {
        int max = 0;
        for (Reporte r : reportes) if (r.getId() > max) max = r.getId();
        return max + 1;
    }

    public ReportePerdido registrarReportePerdido(Usuario usuario, Objeto objeto) {
        ReportePerdido r = new ReportePerdido(getSiguienteId(), usuario, objeto, LocalDateTime.now(), EstadoReporte.REGISTRADO);
        reportes.add(r);
        persistirReporte("Nuevo PERDIDO: " + objeto.getNombre()); // <--- Guarda en archivos
        return r;
    }

    public ReporteEncontrado registrarReporteEncontrado(Usuario usuario, Objeto objeto) {
        ReporteEncontrado r = new ReporteEncontrado(getSiguienteId(), usuario, objeto, LocalDateTime.now(), EstadoReporte.REGISTRADO);
        reportes.add(r);
        persistirReporte("Nuevo ENCONTRADO: " + objeto.getNombre()); // <--- Guarda en archivos
        return r;
    }

    //Método centralizado para guardar el estado del sistema en los archivos cada vez que se registra o modifica un reporte.
    
    private void persistirReporte(String actividad) {
        GestorArchivosObjetos.guardar(datosSistema);          // Guarda el .dat
        GestorArchivosTexto.registrarActividad(actividad);    // Añade al historial .txt
        GestorArchivosTexto.exportarReportes(reportes);       // Sobrescribe la lista legible .txt
    }

    public List<Reporte> listarReportes() { return new ArrayList<>(reportes); }

    public List<ReportePerdido> listarPerdidos() {
        List<ReportePerdido> l = new ArrayList<>();
        for (Reporte r : reportes) if (r instanceof ReportePerdido) l.add((ReportePerdido) r);
        return l;
    }

    public List<ReporteEncontrado> listarEncontrados() {
        List<ReporteEncontrado> l = new ArrayList<>();
        for (Reporte r : reportes) if (r instanceof ReporteEncontrado) l.add((ReporteEncontrado) r);
        return l;
    }

    public Optional<Reporte> buscarPorId(int id) {
        for (Reporte r : reportes) if (r.getId() == id) return Optional.of(r);
        return Optional.empty();
    }

    public Reporte actualizarEstado(int id, EstadoReporte nuevoEstado) {
        Reporte r = buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("No existe reporte con ID " + id));
        r.setEstado(nuevoEstado);
        
        // Persistencia al cambiar de estado (ej. cuando se devuelve un objeto)
        GestorArchivosObjetos.guardar(datosSistema);
        GestorArchivosTexto.exportarReportes(reportes);
        return r;
    }

    public List<Reporte> listarPorUsuario(Usuario usuario) {
        List<Reporte> l = new ArrayList<>();
        for (Reporte r : reportes) if (r.getUsuario().getId() == usuario.getId()) l.add(r);
        return l;
    }
}
