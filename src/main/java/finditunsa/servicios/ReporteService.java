package finditunsa.servicios;

import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Gestiona reportes perdidos y encontrados en memoria.

public class ReporteService {
    // Lista en memoria donde se guardan todos los reportes.
    private final List<Reporte> reportes = new ArrayList<>();

    // Secuencia para asignar IDs únicos a cada reporte.
    private int secuenciaId = 1;

    // Registra un objeto perdido con estado inicial REGISTRADO.
    public ReportePerdido registrarReportePerdido(Usuario usuario, Objeto objeto) {
        // Validación defensiva: no se permite usuario u objeto nulo.
        Validador.validarNoNulo(usuario, "usuario");
        Validador.validarNoNulo(objeto, "objeto");

        // Se crea el reporte con el siguiente ID, fecha actual y estado inicial.
        ReportePerdido reporte = new ReportePerdido(
                secuenciaId++,
                usuario,
                objeto,
                LocalDateTime.now(),
                EstadoReporte.REGISTRADO
        );
        reportes.add(reporte);
        return reporte;
    }

    // Registra un objeto encontrado con estado inicial REGISTRADO.
    public ReporteEncontrado registrarReporteEncontrado(Usuario usuario, Objeto objeto) {
        Validador.validarNoNulo(usuario, "usuario");
        Validador.validarNoNulo(objeto, "objeto");

        ReporteEncontrado reporte = new ReporteEncontrado(
                secuenciaId++,
                usuario,
                objeto,
                LocalDateTime.now(),
                EstadoReporte.REGISTRADO
        );
        reportes.add(reporte);
        return reporte;
    }

    // Devuelve una copia de todos los reportes para no exponer la lista interna.
    public List<Reporte> listarReportes() {
        return new ArrayList<>(reportes);
    }

    // Filtra solo los reportes de tipo perdido usando instanceof.
    public List<ReportePerdido> listarPerdidos() {
        List<ReportePerdido> perdidos = new ArrayList<>();
        for (Reporte reporte : reportes) {
            if (reporte instanceof ReportePerdido perdido) {
                perdidos.add(perdido);
            }
        }
        return perdidos;
    }

    // Filtra solo los reportes de tipo encontrado usando instanceof.
    public List<ReporteEncontrado> listarEncontrados() {
        List<ReporteEncontrado> encontrados = new ArrayList<>();
        for (Reporte reporte : reportes) {
            if (reporte instanceof ReporteEncontrado encontrado) {
                encontrados.add(encontrado);
            }
        }
        return encontrados;
    }

    // Busca un reporte por ID; devuelve Optional vacío si no existe.
    public Optional<Reporte> buscarPorId(int id) {
        for (Reporte reporte : reportes) {
            if (reporte.getId() == id) {
                return Optional.of(reporte);
            }
        }
        return Optional.empty();
    }

    // Cambia el estado de un reporte existente.
    public Reporte actualizarEstado(int id, EstadoReporte nuevoEstado) {
        Validador.validarNoNulo(nuevoEstado, "estado");

        // Si el reporte no existe, se lanza excepción con mensaje claro.
        Reporte reporte = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un reporte con ID " + id));

        reporte.setEstado(nuevoEstado);
        return reporte;
    }

    // Lista los reportes creados por un usuario específico.
    public List<Reporte> listarPorUsuario(Usuario usuario) {
        List<Reporte> resultado = new ArrayList<>();
        for (Reporte reporte : reportes) {
            // Se compara por ID para evitar depender de equals de Usuario.
            if (reporte.getUsuario() != null && reporte.getUsuario().getId() == usuario.getId()) {
                resultado.add(reporte);
            }
        }
        return resultado;
    }
}