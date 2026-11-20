package finditunsa.servicios;

import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;

import java.util.ArrayList;

// RESPONSABLE: INTEGRANTE 3
// MODULO: Búsqueda, coincidencias y devoluciones
// DESCRIPCION: Calcula posibles coincidencias entre reportes perdidos y encontrados.
public class CoincidenciaService {
    private static final int PUNTAJE_MINIMO = 3;

    private final ReporteService reporteService;
    private final ArrayList<Coincidencia> coincidencias = new ArrayList<>();
    private int secuenciaId = 1;

    public CoincidenciaService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Cruza todos los reportes perdidos y encontrados activos para detectar posibles emparejamientos
    // que superen el umbral mínimo de puntuación.
    public ArrayList<Coincidencia> generarCoincidencias() {
        coincidencias.clear();
        secuenciaId = 1;

        for (ReportePerdido perdido : reporteService.listarPerdidos()) {
            for (ReporteEncontrado encontrado : reporteService.listarEncontrados()) {
                // Omite reportes que ya fueron cerrados/devueltos
                if (perdido.getEstado() == EstadoReporte.DEVUELTO
                        || encontrado.getEstado() == EstadoReporte.DEVUELTO) {
                    continue;
                }

                // Evita emparejar reportes creados por el mismo usuario
                if (perdido.getUsuario().getId() == encontrado.getUsuario().getId()) {
                    continue;
                }

                ResultadoCoincidencia resultado = evaluar(perdido, encontrado);
                if (resultado.puntaje >= PUNTAJE_MINIMO) {
                    coincidencias.add(new Coincidencia(
                            secuenciaId++,
                            perdido,
                            encontrado,
                            resultado.puntaje,
                            resultado.caracteristicas
                    ));
                }
            }
        }

        return new ArrayList<>(coincidencias);
    }

    public ArrayList<Coincidencia> listarCoincidencias() {
        return new ArrayList<>(coincidencias);
    }

    // Calcula el puntaje de similitud ponderando atributos clave de ambos objetos
    // (nombre: +3, categoría: +2, color/marca/ubicación/características: +1 c/u).
    private ResultadoCoincidencia evaluar(ReportePerdido perdido, ReporteEncontrado encontrado) {
        Objeto a = perdido.getObjeto();
        Objeto b = encontrado.getObjeto();

        int puntaje = 0;
        ArrayList<String> caracteristicas = new ArrayList<>();

        if (sonSimilares(a.getNombre(), b.getNombre())) {
            puntaje += 3;
            caracteristicas.add("Nombre similar");
        }

        if (a.getCategoria() != null && b.getCategoria() != null
                && a.getCategoria() == b.getCategoria()) {
            puntaje += 2;
            caracteristicas.add("Misma categoría");
        }

        if (sonIguales(a.getColor(), b.getColor())) {
            puntaje += 1;
            caracteristicas.add("Mismo color");
        }

        if (sonIguales(a.getMarca(), b.getMarca())) {
            puntaje += 1;
            caracteristicas.add("Misma marca");
        }

        if (sonSimilares(a.getUbicacion(), b.getUbicacion())) {
            puntaje += 1;
            caracteristicas.add("Ubicación similar");
        }

        if (sonSimilares(a.getCaracteristicas(), b.getCaracteristicas())) {
            puntaje += 1;
            caracteristicas.add("Características similares");
        }

        return new ResultadoCoincidencia(puntaje, caracteristicas);
    }

    // Compara coincidencia exacta tras normalizar mayúsculas, tildes y espacios.
    private boolean sonIguales(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && na.equals(nb);
    }

    // Verifica inclusión parcial entre cadenas normalizadas (ej. "mochila" en "mochila negra").
    private boolean sonSimilares(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && (na.contains(nb) || nb.contains(na));
    }


    // Estructura interna auxiliar para transportar el puntaje y criterios acertados en cada evaluación.
    private static class ResultadoCoincidencia {
        private final int puntaje;
        private final ArrayList<String> caracteristicas;

        private ResultadoCoincidencia(int puntaje, ArrayList<String> caracteristicas) {
            this.puntaje = puntaje;
            this.caracteristicas = caracteristicas;
        }
    }
}
