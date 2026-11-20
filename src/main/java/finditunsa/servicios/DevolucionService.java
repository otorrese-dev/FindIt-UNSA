package finditunsa.servicios;

import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Servicio que gestiona el ciclo de vida de las devoluciones:
// solicitud, verificación, autorización y entrega final.
public class DevolucionService {
    private final ReporteService reporteService;
    private final List<Devolucion> devoluciones = new ArrayList<>();
    private int secuenciaId = 1;

    public DevolucionService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Valida la existencia y el tipo de ambos reportes antes de registrar una nueva solicitud. 
    public Devolucion solicitar(int idPerdido, int idEncontrado, Usuario solicitante, String obs) {
        Validador.validarNoNulo(solicitante, "solicitante");
        Reporte perdido = reporteService.buscarPorId(idPerdido)
                .orElseThrow(() -> new IllegalArgumentException("No existe reporte perdido con ID " + idPerdido));
        Reporte encontrado = reporteService.buscarPorId(idEncontrado)
                .orElseThrow(() -> new IllegalArgumentException("No existe reporte encontrado con ID " + idEncontrado));

        // Asegura que los IDs correspondan exactamente a un reporte perdido y uno encontrado
        if (!(perdido instanceof ReportePerdido) || !(encontrado instanceof ReporteEncontrado)) {
            throw new IllegalArgumentException("Los IDs no corresponden al tipo de reporte correcto.");
        }

        Devolucion dev = new Devolucion(secuenciaId++, (ReportePerdido) perdido, (ReporteEncontrado) encontrado, solicitante, obs);
        devoluciones.add(dev);
        return dev;
    }

    public void verificar(int idDevolucion) {
        Devolucion dev = buscarPorId(idDevolucion);
        if (dev.getEstado() != EstadoDevolucion.SOLICITADA) throw new IllegalStateException("La devolución no está en estado SOLICITADA.");
        dev.setEstado(EstadoDevolucion.VERIFICADA);
    }

    public void autorizar(int idDevolucion) {
        Devolucion dev = buscarPorId(idDevolucion);
        if (dev.getEstado() != EstadoDevolucion.VERIFICADA) throw new IllegalStateException("La devolución no está en estado VERIFICADA.");
        dev.setEstado(EstadoDevolucion.AUTORIZADA);
    }

    // Finaliza la devolución registrando la fecha de entrega y cerrando ambos reportes vinculados.
    public void entregar(int idDevolucion) {
        Devolucion dev = buscarPorId(idDevolucion);
        if (dev.getEstado() != EstadoDevolucion.AUTORIZADA) throw new IllegalStateException("La devolución no está en estado AUTORIZADA.");
        
        dev.setEstado(EstadoDevolucion.ENTREGADA);
        dev.setFechaEntrega(LocalDateTime.now());
        
        // Marca tanto el reporte perdido como el encontrado como DEVUELTO
        reporteService.actualizarEstado(dev.getReportePerdido().getId(), EstadoReporte.DEVUELTO);
        reporteService.actualizarEstado(dev.getReporteEncontrado().getId(), EstadoReporte.DEVUELTO);
    }

    public Devolucion buscarPorId(int id) {
        for (Devolucion d : devoluciones) {
            if (d.getId() == id) return d;
        }
        throw new IllegalArgumentException("No existe devolución con ID " + id);
    }

    public List<Devolucion> listarTodas() { 
        return new ArrayList<>(devoluciones); 
    }

    // Filtra y retorna únicamente las solicitudes registradas por un usuario específico.
    public List<Devolucion> listarPorSolicitante(Usuario usuario) {
        List<Devolucion> resultado = new ArrayList<>();
        for (Devolucion d : devoluciones) {
            if (d.getSolicitante().getId() == usuario.getId()) {
                resultado.add(d);
            }
        }
        return resultado;
    }
}
