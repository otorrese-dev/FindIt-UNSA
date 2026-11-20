package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Devolucion;
import finditunsa.servicios.DevolucionService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel administrativo para supervisar y aprobar el flujo de devoluciones.
public class AdminDevolucionesPanel extends JPanel {
    private final DevolucionService ds;
    private final DefaultTableModel modelo;
    private final JTable tabla;

    public AdminDevolucionesPanel(final DevolucionService ds) {
        this.ds = ds;
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Gestión de Devoluciones");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        add(title, BorderLayout.NORTH);

        // Modelo de tabla no editable para listar todas las solicitudes registradas
        modelo = new DefaultTableModel(new String[]{"ID", "Solicitante", "Perdido", "Encontrado", "Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        
        JPanel cont = new JPanel(new BorderLayout(0, 15));
        cont.setOpaque(false);
        cont.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Controles secuenciales del flujo de aprobación: Verificación -> Autorización -> Entrega
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtns.setOpaque(false);
        JButton btnVerificar = new JButton("1. Verificar");
        JButton btnAutorizar = new JButton("2. Autorizar");
        JButton btnEntregar = new JButton("3. Entregar Objeto");

        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { avanzarEstado(1); }
        });
        btnAutorizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { avanzarEstado(2); }
        });
        btnEntregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { avanzarEstado(3); }
        });

        pnlBtns.add(btnVerificar);
        pnlBtns.add(btnAutorizar);
        pnlBtns.add(btnEntregar);
        cont.add(pnlBtns, BorderLayout.SOUTH);

        add(cont, BorderLayout.CENTER);
        refrescar();
    }

    // Ejecuta la transición de estado correspondiente (1: verificar, 2: autorizar, 3: entregar)
    // sobre la solicitud seleccionada en la tabla.
    private void avanzarEstado(int accion) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una solicitud de la tabla.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        try {
            if (accion == 1) ds.verificar(id);
            else if (accion == 2) ds.autorizar(id);
            else if (accion == 3) ds.entregar(id);
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Consulta todas las solicitudes del sistema y actualiza los registros de la tabla.
    public void refrescar() {
        modelo.setRowCount(0);
        for (Devolucion d : ds.listarTodas()) {
            modelo.addRow(new Object[]{
                d.getId(), d.getSolicitante().getNombre(),
                d.getReportePerdido().getObjeto().getNombre(),
                d.getReporteEncontrado().getObjeto().getNombre(),
                d.getEstado()
            });
        }
    }
}
