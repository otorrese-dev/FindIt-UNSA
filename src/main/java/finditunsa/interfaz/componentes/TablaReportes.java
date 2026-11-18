package finditunsa.interfaz.componentes;

import finditunsa.modelos.Reporte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Visualización de reportes
//DESCRIPCION: Componente reutilizable que encapsula una tabla (JTable) para mostrar listas de reportes de manera estandarizada.

public class TablaReportes extends JPanel {

    // Modelo de datos: Es el "cerebro" de la tabla. Guarda la información y avisa a la vista cuando cambia.
    private final DefaultTableModel modelo;

    // Componente visual: Es la tabla que el usuario ve en pantalla.
    private final JTable tabla;

    // Lista interna para mantener referencia a los objetos reales y poder saber cuál fila se seleccionó.
    private List<Reporte> datos;

    public TablaReportes() {
        // Usamos BorderLayout para que el JScrollPane ocupe todo el espacio disponible.
        setLayout(new BorderLayout());
        setBackground(Estilos.BLANCO);

        // Inicializamos el Modelo de la tabla.
        // Recibe un arreglo con los nombres de las columnas y 0 filas iniciales.
        modelo = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Nombre", "Categoría", "Ubicación", "Fecha", "Estado", "Reportado por"},
                0
        ) {
            // Sobrescribimos este método para hacer que las celdas sean de solo lectura.
            // Si no hacemos esto, el usuario podría hacer doble clic y editar los datos por error.
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Inicializamos la Tabla pasándole el modelo que creamos arriba.
        tabla = new JTable(modelo);

        // Configuraciones visuales (UX/UI) para que la tabla se vea moderna y legible.
        tabla.setRowHeight(26); // Altura de cada fila
        tabla.setFont(Estilos.TEXTO);
        tabla.setGridColor(new Color(230, 233, 238)); // Color de las líneas divisorias
        tabla.getTableHeader().setFont(Estilos.SUBTITULO); // Fuente de las cabeceras
        tabla.getTableHeader().setBackground(Estilos.AZUL_CLARO); // Fondo de las cabeceras
        tabla.setSelectionBackground(Estilos.AZUL_CLARO); // Color al seleccionar una fila
        tabla.setSelectionForeground(Estilos.TEXTO_OSCURO); // Color del texto seleccionado

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

     //Recibe una lista de reportes y actualiza la tabla visualmente.
     //reportes La lista de objetos Reporte (Perdidos o Encontrados) a mostrar.

    public void cargar(List<Reporte> reportes) {
        this.datos = reportes; // Guardamos la referencia
        modelo.setRowCount(0); // Limpiamos la tabla antes de cargar nuevos datos

        // Recorremos la lista y agregamos cada reporte como una nueva fila en el modelo.
        for (Reporte r : reportes) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getTipoReporte(),
                    r.getObjeto().getNombre(),
                    r.getObjeto().getCategoria(),
                    r.getObjeto().getUbicacion(),
                    // Operador ternario: si la fecha no es nula, la muestra; si es nula, deja un texto vacío.
                    r.getObjeto().getFechaEvento() != null ? r.getObjeto().getFechaEvento().toString() : "",
                    r.getEstado(),
                    r.getUsuario() != null ? r.getUsuario().getNombre() : ""
            });
        }
    }

     //Devuelve el objeto Reporte real que está seleccionado en la fila de la tabla.
     //Es útil para acciones como "Ver detalle" o "Seleccionar para Devolución".

    public Reporte getReporteSeleccionado() {
        int fila = tabla.getSelectedRow(); // Obtiene el índice de la fila seleccionada por el usuario

        // Validaciones: si la fila es -1 (nada seleccionado), o los datos son nulos, o la fila excede el tamaño.
        if (fila < 0 || datos == null || fila >= datos.size()) {
            return null;
        }

        // Retornamos el objeto de la lista que coincide con la posición de la fila visual.
        return datos.get(fila);
    }

     //Getter público por si algún panel externo necesita acceder directamente al JTable

    public JTable getTabla() {
        return tabla;
    }
}