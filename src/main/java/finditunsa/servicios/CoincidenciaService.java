package finditunsa.servicios;

import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;
import java.util.ArrayList;
import java.util.List;

// MODULO: Persistencia e Integracion de Coincidencias
// DESCRIPCION: Calcula coincidencias y conserva sus resultados en el archivo .dat.
public class CoincidenciaService {
    // Puntaje mínimo requerido para considerar que dos reportes podrían ser el mismo objeto
    private static final int PUNTAJE_MINIMO = 3;

    private final ReporteService reporteService;
    private final List<Coincidencia> coincidencias;
    private final DatosSistema datosSistema;

    // Inicializa el servicio enlazando la lista de coincidencias del contenedor principal del sistema.
    public CoincidenciaService(ReporteService reporteService, DatosSistema datos) {
        this.reporteService = reporteService;
        this.datosSistema = datos;
        this.coincidencias = datos.coincidencias;
    }

    // Recalcula todas las posibles coincidencias entre reportes perdidos y encontrados activos,
    // persiste los resultados en disco y devuelve la lista actualizada.
    public List<Coincidencia> generarCoincidencias() {
        coincidencias.clear();
        int secuenciaId = 1;

        for (ReportePerdido p : reporteService.listarPerdidos()) {
            for (ReporteEncontrado e : reporteService.listarEncontrados()) {
                // Omite reportes que ya fueron devueltos
                if (p.getEstado() == EstadoReporte.DEVUELTO || e.getEstado() == EstadoReporte.DEVUELTO) continue;

                // Evita emparejar reportes creados por el mismo usuario
                if (p.getUsuario().getId() == e.getUsuario().getId()) continue;

                ResultadoCoincidencia res = evaluar(p, e);
                if (res.puntaje >= PUNTAJE_MINIMO) {
                    coincidencias.add(new Coincidencia(secuenciaId++, p, e, res.puntaje, new ArrayList<>(res.caracteristicas)));
                }
            }
        }

        // Guarda las coincidencias generadas en el archivo binario de objetos (.dat)
        GestorArchivosObjetos.guardar(datosSistema);
        return new ArrayList<>(coincidencias);
    }

    /**
     * Devuelve una copia de la lista actual de coincidencias registradas.
     */
    public List<Coincidencia> listarCoincidencias() { return new ArrayList<>(coincidencias); }

    // Compara los atributos de los objetos de ambos reportes y calcula un puntaje de similitud.
    private ResultadoCoincidencia evaluar(ReportePerdido perdido, ReporteEncontrado encontrado) {
        Objeto a = perdido.getObjeto();
        Objeto b = encontrado.getObjeto();
        int puntaje = 0;
        List<String> caracteristicas = new ArrayList<>();

        // Ponderación de criterios de coincidencia
        if (sonSimilares(a.getNombre(), b.getNombre())) { 
            puntaje += 3; caracteristicas.add("Nombre similar"); 
        }
        if (a.getCategoria() != null && b.getCategoria() != null && a.getCategoria() == b.getCategoria()) { 
            puntaje += 2; caracteristicas.add("Misma categoría"); 
        }
        if (sonIguales(a.getColor(), b.getColor())) { 
            puntaje++; caracteristicas.add("Mismo color"); 
        }
        if (sonIguales(a.getMarca(), b.getMarca())) { 
            puntaje++; caracteristicas.add("Misma marca"); 
        }
        if (sonSimilares(a.getUbicacion(), b.getUbicacion())) { 
            puntaje++; caracteristicas.add("Ubicación similar"); 
        }
        if (sonSimilares(a.getCaracteristicas(), b.getCaracteristicas())) { 
            puntaje++; caracteristicas.add("Características similares"); 
        }

        return new ResultadoCoincidencia(puntaje, caracteristicas);
    }

    // Verifica igualdad exacta entre dos cadenas tras normalizarlas (sin tildes, espacios extra ni mayúsculas).
    private boolean sonIguales(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && na.equals(nb);
    }

    // Verifica si una cadena normalizada está contenida dentro de la otra.
    private boolean sonSimilares(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && (na.contains(nb) || nb.contains(na));
    }

    // Clase interna auxiliar para encapsular el resultado de la evaluación entre dos reportes.
    private static class ResultadoCoincidencia {
        private final int puntaje;
        private final List<String> caracteristicas;
        private ResultadoCoincidencia(int p, List<String> c) { puntaje = p; caracteristicas = c; }
    }
}
