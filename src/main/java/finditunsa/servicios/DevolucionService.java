package finditunsa.servicios;

import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;

import java.util.ArrayList;
import java.util.List;


// RESPONSABLE: INTEGRANTE 3
// MODULO: Búsqueda, coincidencias y devoluciones
// DESCRIPCION: Registra devoluciones y actualiza estados de los reportes.
public class DevolucionService {

    // Servicio inyectado para buscar y modificar los reportes registrados
    private final ReporteService reporteService;

    // Lista interna que almacena el historial de devoluciones concretadas
    private final List<Devolucion> devoluciones = new ArrayList<>();

    // Contador autoincremental para asignar un ID único a cada devolución
    private int secuenciaId = 1;

    // Constructor principal del servicio de devoluciones.
    public DevolucionService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }


    // Procesa y registra formalmente la devolución de un objeto a su dueño.
    // Realiza cinco validaciones críticas antes de confirmar la transacción y cambiar estados.
    public Devolucion registrarDevolucion(int idPerdido, int idEncontrado,
                                          Usuario responsable, String observaciones) {
        // 1. Validación de seguridad: verifica que exista un usuario responsable autenticado/válido
        Validador.validarNoNulo(responsable, "responsable");

        // 2. Búsqueda de existencia: obtiene los reportes o lanza excepción si los IDs no existen (uso de Optional)
        Reporte reportePerdido = reporteService.buscarPorId(idPerdido)
                .orElseThrow(() -> new IllegalArgumentException("No existe el reporte perdido con ID " + idPerdido));

        Reporte reporteEncontrado = reporteService.buscarPorId(idEncontrado)
                .orElseThrow(() -> new IllegalArgumentException("No existe el reporte encontrado con ID " + idEncontrado));

        // 3. Validación de polimorfismo/tipo: asegura que el primer ID corresponda realmente a un ReportePerdido
        if (!(reportePerdido instanceof ReportePerdido)) {
            throw new IllegalArgumentException("El ID " + idPerdido + " no corresponde a un reporte perdido.");
        }

        // 4. Validación de polimorfismo/tipo: asegura que el segundo ID corresponda realmente a un ReporteEncontrado
        if (!(reporteEncontrado instanceof ReporteEncontrado)) {
            throw new IllegalArgumentException("El ID " + idEncontrado + " no corresponde a un reporte encontrado.");
        }

        // 5. Validación de estado de negocio: impide procesar reportes que ya fueron cerrados/entregados previamente
        if (reportePerdido.getEstado() == EstadoReporte.DEVUELTO
                || reporteEncontrado.getEstado() == EstadoReporte.DEVUELTO) {
            throw new IllegalArgumentException("Uno de los reportes ya fue devuelto.");
        }

        // Creación del registro de devolución realizando el casteo seguro de tipos
        Devolucion devolucion = new Devolucion(
                secuenciaId++,
                (ReportePerdido) reportePerdido,
                (ReporteEncontrado) reporteEncontrado,
                responsable,
                observaciones
        );

        // Actualización de estados
        reportePerdido.setEstado(EstadoReporte.DEVUELTO);
        reporteEncontrado.setEstado(EstadoReporte.DEVUELTO);

        // Almacena el registro en la lista histórica y lo devuelve
        devoluciones.add(devolucion);
        return devolucion;
    }

    // Obtiene el historial completo de devoluciones registradas.
    // Una copia defensiva de la lista de devoluciones para proteger la colección interna.
    public List<Devolucion> listarDevoluciones() {
        return new ArrayList<>(devoluciones);
    }
}
