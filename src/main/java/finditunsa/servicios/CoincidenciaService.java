package finditunsa.servicios;

import finditunsa.modelos.*;
import finditunsa.utilidades.Validador;

import java.util.ArrayList;
import java.util.List;

//RESPONSABLE: INTEGRANTE 3
//MODULO: Búsqueda, coincidencias y devoluciones
//DESCRIPCION: Calcula posibles coincidencias entre reportes perdidos y encontrados.
public class CoincidenciaService {
    // Umbral mínimo de puntos requerido para considerar que dos reportes hacen "match"
    private static final int PUNTAJE_MINIMO = 3;

    // Servicio inyectado para acceder a las listas de reportes del sistema
    private final ReporteService reporteService;

    // Lista interna para almacenar el historial de coincidencias generadas
    private final List<Coincidencia> coincidencias = new ArrayList<>();

    // Autoincremental para generar identificadores únicos (ID) para cada coincidencia
    private int secuenciaId = 1;

    // Constructor del servicio de coincidencias.
    public CoincidenciaService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    //Recorre todos los reportes activos (no devueltos) y los compara cruzadamente 
    //(todos contra todos) para identificar coincidencias que superen el puntaje mínimo.
    public List<Coincidencia> generarCoincidencias() {
        // Limpia el historial de coincidencias anteriores antes de calcular las nuevas
        coincidencias.clear();

        // Bucle anidado para comparar cada reporte perdido contra cada reporte encontrado
        for (ReportePerdido perdido : reporteService.listarPerdidos()) {

            for (ReporteEncontrado encontrado : reporteService.listarEncontrados()) {
                // Regla de negocio: Si alguno de los dos reportes ya fue devuelto a su dueño, se ignora la comparación
                if (perdido.getEstado() == EstadoReporte.DEVUELTO
                        || encontrado.getEstado() == EstadoReporte.DEVUELTO) {
                    continue;
                }

                // Evalúa la similitud entre los objetos del reporte perdido y encontrado
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

        // Retorna una copia defensiva de la lista de coincidencias para proteger la lista interna
        return new ArrayList<>(coincidencias);
    }

    public List<Coincidencia> listarCoincidencias() {
        return new ArrayList<>(coincidencias);
    }

    //Aplica la ponderación del algoritmo evaluando atributo por atributo de ambos objetos 
    // Ponderaciones:
    // -Nombre similar: +3 pts
    // -Misma categoría: +2 pts
    // -Mismo color, marca, ubicación o características similares: +1 pt c/u
    private ResultadoCoincidencia evaluar(ReportePerdido perdido, ReporteEncontrado encontrado) {
        Objeto a = perdido.getObjeto();
        Objeto b = encontrado.getObjeto();

        int puntaje = 0;
        List<String> caracteristicas = new ArrayList<>();
        
        // Criterios
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

    // Verifica si dos cadenas son exactamente iguales tras normalizarlas (sin tildes/mayúsculas).
    private boolean sonIguales(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && na.equals(nb);
    }

    // Verifica si una cadena contiene a la otra tras normalizarlas.
    private boolean sonSimilares(String a, String b) {
        String na = Validador.normalizar(a);
        String nb = Validador.normalizar(b);
        return !na.isEmpty() && !nb.isEmpty() && (na.contains(nb) || nb.contains(na));
    }

    //Clase interna contenedora (Data Transfer Object local) para encapsular
    //de forma temporal el puntaje y las razones de la coincidencia durante la evaluación.
    private static class ResultadoCoincidencia {
        private final int puntaje;
        private final List<String> caracteristicas;

        private ResultadoCoincidencia(int puntaje, List<String> caracteristicas) {
            this.puntaje = puntaje;
            this.caracteristicas = caracteristicas;
        }
    }
}
