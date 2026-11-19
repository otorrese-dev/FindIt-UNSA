package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.interfaz.componentes.TablaReportes;
import finditunsa.modelos.Usuario;
import finditunsa.servicios.ReporteService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y visualización de objetos
//DESCRIPCION: Panel de usuario que muestra una tabla con los reportes (perdidos y encontrados) que le pertenecen al usuario logueado.
//Permite abrir el diálogo para registrar uno nuevo.

public class MisReportesPanel extends JPanel {
    private final ReporteService rs;
    private final Usuario user;
    private final TablaReportes tabla;

    public MisReportesPanel(ReporteService rs, Usuario user) {
        this.rs = rs;
        this.user = user;

        // Configuración principal del panel
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // CONSTRUCCIÓN DEL ENCABEZADO (Título + Botón)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Mis Reportes");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        header.add(title, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        JButton btnNuevo = new JButton("+ Nuevo Reporte");
        btnNuevo.setBackground(Estilos.AZUL_PRINCIPAL);
        btnNuevo.setForeground(Color.WHITE);
        btnPanel.add(btnNuevo);
        header.add(btnPanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // CONSTRUCCIÓN DE LA TABLA
        tabla = new TablaReportes();
        add(tabla, BorderLayout.CENTER);

        // PROGRAMACIÓN DE EVENTOS
        // Al presionar el botón, se abre la ventana modal de registro
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Invoca al DialogoRegistro pasándole el servicio y el usuario actual
                new DialogoRegistro(SwingUtilities.getWindowAncestor(MisReportesPanel.this), MisReportesPanel.this.rs, MisReportesPanel.this.user).setVisible(true);
                // Al cerrarse el diálogo, se refresca la tabla para mostrar el nuevo registro
                refrescar();
            }
        });

        // Carga inicial de los datos del usuario al abrir el panel
        refrescar();
    }

     //Método que solicita al servicio únicamente los reportes asociados al ID del usuario actual y los carga en la tabla.
    public void refrescar() {
        tabla.cargar(rs.listarPorUsuario(user));
    }
}