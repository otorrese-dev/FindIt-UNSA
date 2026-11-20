package finditunsa.interfaz.componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Componentes reutilizables
//DESCRIPCION: Tarjeta visual utilizada en InicioPanel para mostrar indicadores numéricos
//             (reportes, solicitudes, devoluciones o coincidencias) con una franja de color lateral.

public class TarjetaEstadistica extends JPanel {

    // Referencia directa a la etiqueta del número para permitir actualizar su valor dinámicamente
    private final JLabel etiquetaValor;

    public TarjetaEstadistica(String titulo, int valor, Color color) {
        // BorderLayout separa la franja vertical de color (WEST) del contenido textual (CENTER)
        setLayout(new BorderLayout(10, 0));
        setBackground(Estilos.BLANCO);
        // Borde compuesto: línea gris externa de 1px + margen interno de 15px en los cuatro lados
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 234)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Franja vertical decorativa de 5px de ancho que identifica la categoría de la métrica
        JPanel indicador = new JPanel();
        indicador.setBackground(color);
        indicador.setPreferredSize(new Dimension(5, 0));

        // Título descriptivo superior en fuente pequeña
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(Estilos.TEXTO_PEQUENO);
        tituloLabel.setForeground(Estilos.GRIS);

        // Número estadístico principal resaltado en tamaño grande y con el color asignado
        etiquetaValor = new JLabel(String.valueOf(valor));
        etiquetaValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        etiquetaValor.setForeground(color);

        // Contenedor interno que apila el título arriba (NORTH) y el contador abajo (CENTER)
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(tituloLabel, BorderLayout.NORTH);
        centro.add(etiquetaValor, BorderLayout.CENTER);

        // Ensamblaje final de la tarjeta
        add(indicador, BorderLayout.WEST);
        add(centro, BorderLayout.CENTER);
    }

    // Permite modificar el valor numérico de la tarjeta sin necesidad de recrear el componente
    public void setValor(int valor) {
        etiquetaValor.setText(String.valueOf(valor));
    }
}