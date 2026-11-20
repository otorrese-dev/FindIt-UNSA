package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Coincidencia;
import finditunsa.modelos.Objeto;
import finditunsa.servicios.CoincidenciaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// MODULO: Búsqueda, coincidencias y devoluciones
// DESCRIPCION: Muestra posibles coincidencias entre perdidos y encontrados.
public class CoincidenciasPanel extends JPanel {

    private final CoincidenciaService coincidenciaService;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private List<Coincidencia> coincidencias;

    public CoincidenciasPanel(CoincidenciaService coincidenciaService) {
        this.coincidenciaService = coincidenciaService;

        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        add(crearEncabezado(), BorderLayout.NORTH);

        // Modelo de tabla de solo lectura para mostrar los resultados de emparejamiento
        modelo = new DefaultTableModel(
                new String[]{"ID", "Puntaje", "Perdido", "Encontrado", "Características coincidentes"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.setFont(Estilos.TEXTO);
        tabla.getTableHeader().setFont(Estilos.SUBTITULO);
        tabla.getTableHeader().setBackground(Estilos.AZUL_CLARO);

        JPanel cont = new JPanel(new BorderLayout(0, 15));
        cont.setOpaque(false);
        cont.add(new JScrollPane(tabla), BorderLayout.CENTER);
        cont.add(crearBotones(), BorderLayout.SOUTH);
        add(cont, BorderLayout.CENTER);

        // Carga inicial de coincidencias al abrir el panel
        refrescar();
    }

    private JPanel crearEncabezado() {
        JPanel enc = new JPanel();
        enc.setOpaque(false);
        enc.setLayout(new BoxLayout(enc, BoxLayout.Y_AXIS));
        enc.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titulo = new JLabel("Posibles coincidencias");
        titulo.setFont(Estilos.TITULO_GRANDE);
        titulo.setForeground(Estilos.TEXTO_OSCURO);

        JLabel sub = new JLabel("Reportes perdidos y encontrados con características similares");
        sub.setFont(Estilos.TEXTO);
        sub.setForeground(Estilos.GRIS);

        enc.add(titulo);
        enc.add(Box.createVerticalStrut(4));
        enc.add(sub);
        return enc;
    }

    private JPanel crearBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setOpaque(false);

        JButton detalle = new JButton("Ver detalle");
        detalle.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) { verDetalle(); }
        });

        JButton actualizar = new JButton("Recalcular");
        actualizar.setBackground(Estilos.AZUL_PRINCIPAL);
        actualizar.setForeground(Color.WHITE);
        actualizar.setFocusPainted(false);
        actualizar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) { refrescar(); }
        });

        p.add(detalle);
        p.add(actualizar);
        return p;
    }


    // Consulta el servicio para recalcular coincidencias y repuebla la tabla.
    public void refrescar() {
        coincidencias = coincidenciaService.generarCoincidencias();
        modelo.setRowCount(0);
        for (Coincidencia c : coincidencias) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getPuntaje(),
                    c.getReportePerdido().getObjeto().getNombre(),
                    c.getReporteEncontrado().getObjeto().getNombre(),
                    String.join(", ", c.getCaracteristicasCoincidentes())
            });
        }
    }


    // Valida la fila seleccionada y muestra un cuadro modal comparando ambos reportes.
    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || coincidencias == null || fila >= coincidencias.size()) {
            JOptionPane.showMessageDialog(this, "Seleccione una coincidencia.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Coincidencia c = coincidencias.get(fila);
        Objeto p = c.getReportePerdido().getObjeto();
        Objeto e = c.getReporteEncontrado().getObjeto();

        // Formatea la ficha comparativa entre el objeto perdido y el encontrado
        String detalle = String.format(
                "Coincidencia #%d - Puntaje: %d%n%n" +
                "PERDIDO #%d%n  %s%n  Categoría: %s%n  Ubicación: %s%n  Descripción: %s%n%n" +
                "ENCONTRADO #%d%n  %s%n  Categoría: %s%n  Ubicación: %s%n  Descripción: %s%n%n" +
                "Características coincidentes: %s",
                c.getId(), c.getPuntaje(),
                c.getReportePerdido().getId(), p.getNombre(), p.getCategoria(), p.getUbicacion(), p.getDescripcion(),
                c.getReporteEncontrado().getId(), e.getNombre(), e.getCategoria(), e.getUbicacion(), e.getDescripcion(),
                String.join(", ", c.getCaracteristicasCoincidentes())
        );

        JTextArea ta = new JTextArea(detalle);
        ta.setEditable(false);
        ta.setFont(Estilos.TEXTO);
        ta.setBackground(Estilos.FONDO_CLARO);
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(520, 340));

        JOptionPane.showMessageDialog(this, sp, "Detalle de coincidencia", JOptionPane.INFORMATION_MESSAGE);
    }
}
