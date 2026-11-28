package finditunsa.servicios;

import finditunsa.archivos.GestorArchivosBinarios;
import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.archivos.GestorArchivosTexto;
import finditunsa.modelos.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MODULO: Persistencia Binaria e Integracion
 * DESCRIPCION: Servicio encargado de gestionar el ciclo de vida de las devoluciones
 * (solicitud, verificación, autorización y entrega) integrando persistencia en .dat, .txt y .bin.
 */
public class DevolucionService {
    private final ReporteService reporteService;
    private final List<Devolucion> devoluciones;
    private final DatosSistema datosSistema;

    // Inicializa el servicio enlazando la lista de devoluciones del contenedor principal del sistema.
    public DevolucionService(ReporteService reporteService, DatosSistema datos) {
        this.reporteService = reporteService;
        this.datosSistema = datos;
        this.devoluciones = datos.devoluciones;
    }

    // Registra una nueva solicitud de devolución vinculando un reporte perdido con uno encontrado.
    public Devolucion solicitar(int idPerdido, int idEncontrado, Usuario solicitante, String obs) {
        Reporte p = reporteService.buscarPorId(idPerdido).orElseThrow(() -> new IllegalArgumentException("ID perdido no existe"));
        Reporte e = reporteService.buscarPorId(idEncontrado).orElseThrow(() -> new IllegalArgumentException("ID encontrado no existe"));

        // Valida que cada ID corresponda efectivamente a su subtipo de reporte
        if (!(p instanceof ReportePerdido) || !(e instanceof ReporteEncontrado)) {
            throw new IllegalArgumentException("Los IDs no corresponden al tipo correcto.");
        }

        // Genera un ID autoincremental basado en el mayor ID registrado
        int id = 1;
        for (Devolucion d : devoluciones) if (d.getId() >= id) id = d.getId() + 1;

        Devolucion dev = new Devolucion(id, (ReportePerdido) p, (ReporteEncontrado) e, solicitante, obs);
        devoluciones.add(dev);

        // Guarda el estado actualizado en disco y registra el evento en el log
        GestorArchivosObjetos.guardar(datosSistema);
        GestorArchivosTexto.registrarActividad("Solicitud de devolución ID: " + id);
        return dev;
    }

    // Cambia el estado de una devolución de SOLICITADA a VERIFICADA tras validar la propiedad.
    public void verificar(int id) {
        Devolucion d = buscarPorId(id);
        if (d.getEstado() != EstadoDevolucion.SOLICITADA) throw new IllegalStateException("Estado incorrecto.");
        d.setEstado(EstadoDevolucion.VERIFICADA);

        GestorArchivosObjetos.guardar(datosSistema);
        GestorArchivosTexto.registrarActividad("Devolución verificada ID: " + id);
    }


    // Aprueba una devolución previamente verificada, dejándola lista para entrega física.
    public void autorizar(int id) {
        Devolucion d = buscarPorId(id);
        if (d.getEstado() != EstadoDevolucion.VERIFICADA) throw new IllegalStateException("Estado incorrecto.");
        d.setEstado(EstadoDevolucion.AUTORIZADA);

        GestorArchivosObjetos.guardar(datosSistema);
        GestorArchivosTexto.registrarActividad("Devolución autorizada ID: " + id);
    }

    // Concreta la entrega del objeto, cierra ambos reportes asociados y actualiza las estadísticas binarias.
    public void entregar(int id) {
        Devolucion d = buscarPorId(id);
        if (d.getEstado() != EstadoDevolucion.AUTORIZADA) throw new IllegalStateException("Estado incorrecto.");

        d.setEstado(EstadoDevolucion.ENTREGADA);
        d.setFechaEntrega(LocalDateTime.now());

        // Marca tanto el reporte de pérdida como el de hallazgo como devueltos
        reporteService.actualizarEstado(d.getReportePerdido().getId(), EstadoReporte.DEVUELTO);
        reporteService.actualizarEstado(d.getReporteEncontrado().getId(), EstadoReporte.DEVUELTO);

        // Persiste en los tres formatos: objetos (.dat), bitácora (.txt) y contadores (.bin)
        GestorArchivosObjetos.guardar(datosSistema);
        GestorArchivosTexto.registrarActividad("Devolución ENTREGADA ID: " + id);
        GestorArchivosBinarios.guardarEstadisticas(datosSistema.usuarios.size(), datosSistema.reportes.size(), devoluciones.size());
    }

    // Busca una devolución por su identificador único.

    public Devolucion buscarPorId(int id) {
        for (Devolucion d : devoluciones) if (d.getId() == id) return d;
        throw new IllegalArgumentException("Devolución no existe");
    }

    // Devuelve una copia de todas las devoluciones registradas en el sistema.
    public List<Devolucion> listarTodas() { return new ArrayList<>(devoluciones); }

    // Filtra y devuelve las devoluciones realizadas por un usuario específico.
    public List<Devolucion> listarPorSolicitante(Usuario u) {
        List<Devolucion> l = new ArrayList<>();
        for (Devolucion d : devoluciones) if (d.getSolicitante().getId() == u.getId()) l.add(d);
        return l;
    }
}
