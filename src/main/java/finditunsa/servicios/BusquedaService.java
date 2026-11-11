package finditunsa.servicios;

import finditunsa.modelos.CategoriaObjeto;
import finditunsa.modelos.Objeto;
import finditunsa.modelos.Reporte;
import finditunsa.utilidades.Validador;

import java.util.ArrayList;
import java.util.List;

 // RESPONSABLE: INTEGRANTE 3
 // MODULO: Búsqueda, coincidencias y devoluciones
 // DESCRIPCION: Realiza búsquedas de reportes por texto, categoría y ubicación.
 
public class BusquedaService {
    // Inyección de dependencia para acceder al origen de datos de los reportes
    private final ReporteService reporteService;

    // Constructor principal del servicio de búsqueda.
    // reporteService Servicio base del cual se extraen los reportes registrados.
    public BusquedaService(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // Realiza una búsqueda avanzada de reportes evaluando texto, categoría y ubicación de forma conjunta
    public List<Reporte> buscarAvanzado(String texto, CategoriaObjeto categoria, String ubicacion) {
        // Lista donde se almacenarán únicamente los reportes que coincidan con los filtros
        List<Reporte> resultados = new ArrayList<>();
      
        // Normaliza los parámetros de entrada (quita acentos, convierte a minúsculas, elimina espacios extra)
        String textoNormalizado = Validador.normalizar(texto);
        String ubicacionNormalizada = Validador.normalizar(ubicacion);

        // Recorre todos los reportes disponibles en el sistema
        for (Reporte reporte : reporteService.listarReportes()) {
            Objeto objeto = reporte.getObjeto();

            // 1. Evaluación del criterio por TEXTO LIBRE:
            // Se valida si el texto de búsqueda está vacío (coincide por defecto) 
            // o si el término existe en alguno de los atributos relevantes del objeto.
            boolean coincideTexto = textoNormalizado.isEmpty()
                    || contiene(objeto.getNombre(), textoNormalizado)
                    || contiene(objeto.getDescripcion(), textoNormalizado)
                    || contiene(objeto.getCategoria() != null ? objeto.getCategoria().toString() : "", textoNormalizado)
                    || contiene(objeto.getUbicacion(), textoNormalizado)
                    || contiene(objeto.getCaracteristicas(), textoNormalizado)
                    || contiene(objeto.getMarca(), textoNormalizado)
                    || contiene(objeto.getColor(), textoNormalizado);

            // 2. Evaluación de CATEGORÍA:
            // Es válido si no se especificó categoría (null) o si coincide exactamente con la del objeto.
            boolean coincideCategoria = categoria == null || objeto.getCategoria() == categoria;

            // 3. Evaluación de UBICACIÓN:
            // Es válido si la ubicación a buscar está vacía o si está contenida dentro del atributo del objeto.
            boolean coincideUbicacion = ubicacionNormalizada.isEmpty()
                    || contiene(objeto.getUbicacion(), ubicacionNormalizada);

            // Si el reporte cumple con las tres condiciones al mismo tiempo, se agrega a los resultados
            if (coincideTexto && coincideCategoria && coincideUbicacion) {
                resultados.add(reporte);
            }
        }

        return resultados;
    }

    // Realiza una búsqueda simple únicamente por coincidencia de texto libre.
    public List<Reporte> buscar(String texto) {
        return buscarAvanzado(texto, null, null);
    }

    // Filtra los reportes de acuerdo a una categoría específica.
    public List<Reporte> buscarPorCategoria(CategoriaObjeto categoria) {
        return buscarAvanzado("", categoria, null);
    }

    // Filtra los reportes de acuerdo a una ubicación específica.
    public List<Reporte> buscarPorUbicacion(String ubicacion) {
        return buscarAvanzado("", null, ubicacion);
    }

    //Método auxiliar privado que comprueba si una cadena de texto contiene a otra.
    //Ambas cadenas son procesadas mediante la clase Validador para asegurar comparaciones 
    // que ignoren mayúsculas/minúsculas y tildes.
    private boolean contiene(String valor, String textoNormalizado) {
        return valor != null && Validador.normalizar(valor).contains(textoNormalizado);
    }
}
