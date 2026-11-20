package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.*;
import finditunsa.servicios.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel donde el usuario visualiza el historial y estado de sus solicitudes de devolución.

public class DevolucionesPanel extends JPanel {
    private final DefaultTableModel modelo;

    public DevolucionesPanel(final ReporteService rs, final DevolucionService ds, final Usuario user) {
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Mis Devoluciones");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        add(title, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new String[]{"ID", "Objeto Perdido", "Objeto Encontrado", "Fecha", "Estado"}, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.setFont(Estilos.TEXTO);
        tabla.getTableHeader().setFont(Estilos.SUBTITULO);

        JPanel cont = new JPanel(new BorderLayout(0, 15));
        cont.setOpaque(false);
        cont.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Captura los IDs de ambos reportes y registra una nueva solicitud de devolución
        JButton btnSolicitar = new JButton("Solicitar Nueva Devolución");
        btnSolicitar.setBackground(Estilos.AZUL_PRINCIPAL);
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idP = JOptionPane.showInputDialog(DevolucionesPanel.this, "ID del reporte perdido:");
                String idE = JOptionPane.showInputDialog(DevolucionesPanel.this, "ID del reporte encontrado:");
                if (idP != null && idE != null && !idP.isEmpty() && !idE.isEmpty()) {
                    try {
                        ds.solicitar(Integer.parseInt(idP), Integer.parseInt(idE), user, "Sin obs");
                        JOptionPane.showMessageDialog(DevolucionesPanel.this, "Solicitud enviada al administrador.");
                        actualizarTabla(ds, user);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DevolucionesPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnSolicitar);
        cont.add(pnlBtn, BorderLayout.SOUTH);

        add(cont, BorderLayout.CENTER);
        actualizarTabla(ds, user);
    }

    //Consulta las devoluciones asociadas al usuario actual y recarga la tabla.
    private void actualizarTabla(DevolucionService ds, Usuario user) {
        modelo.setRowCount(0);
        for (Devolucion d : ds.listarPorSolicitante(user)) {
            modelo.addRow(new Object[]{
                d.getId(), 
                d.getReportePerdido().getObjeto().getNombre(),
                d.getReporteEncontrado().getObjeto().getNombre(),
                d.getFechaSolicitud().toLocalDate(),
                d.getEstado()
            });
        }
    }
}
