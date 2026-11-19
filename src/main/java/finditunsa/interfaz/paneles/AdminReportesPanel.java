package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.interfaz.componentes.TablaReportes;
import finditunsa.servicios.ReporteService;
import javax.swing.*;
import java.awt.*;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y visualización de objetos
//DESCRIPCION: Panel exclusivo para el administrador. Muestra una tabla con absolutamente todos los reportes (perdidos y encontrados)
//registrados en el sistema por cualquier usuario.

public class AdminReportesPanel extends JPanel {

    public AdminReportesPanel(ReporteService rs) {
        // Configuración del layout principal y márgenes del panel
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Título de la vista
        JLabel title = new JLabel("Gestión Global de Reportes");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        add(title, BorderLayout.NORTH);

        // Instanciación del componente reutilizable de tabla
        TablaReportes tabla = new TablaReportes();

        // Carga de datos: A diferencia de "MisReportes", aquí se llama a listarReportes()
        // para obtener la lista completa de todo el sistema.
        tabla.cargar(rs.listarReportes());

        // Añadir la tabla al centro del panel (ocupará todo el espacio restante)
        add(tabla, BorderLayout.CENTER);
    }
}