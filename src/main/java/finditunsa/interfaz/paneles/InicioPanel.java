package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.*;
import finditunsa.modelos.*;
import finditunsa.servicios.*;
import javax.swing.*;
import java.awt.*;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y visualización de objetos
//DESCRIPCION: Panel de inicio (Dashboard). Muestra tarjetas estadísticas que varían según el rol del usuario (Admin o Usuario normal) y una
//tabla con los reportes más recientes del sistema.

public class InicioPanel extends JPanel {
    // Servicios necesarios para obtener las estadísticas
    private final ReporteService rs;
    private final CoincidenciaService cs;
    private final DevolucionService ds;
    private final Usuario user;

    // Componentes visuales que necesitan ser actualizados dinámicamente
    private JPanel panelTarjetas;
    private TablaReportes tabla;

    public InicioPanel(ReporteService rs, CoincidenciaService cs, DevolucionService ds, Usuario user) {
        this.rs = rs;
        this.cs = cs;
        this.ds = ds;
        this.user = user;

        setLayout(new BorderLayout(0, 20));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Título de bienvenida personalizado
        JLabel titulo = new JLabel("Hola, " + user.getNombre());
        titulo.setFont(Estilos.TITULO_GRANDE);
        titulo.setForeground(Estilos.TEXTO_OSCURO);
        add(titulo, BorderLayout.NORTH);

        // Panel para las 4 tarjetas estadísticas (GridLayout 1 fila, 4 columnas)
        panelTarjetas = new JPanel(new GridLayout(1, 4, 15, 0));
        panelTarjetas.setOpaque(false);

        // Panel para la tabla de reportes recientes
        JPanel panelTabla = new JPanel(new BorderLayout(0, 10));
        panelTabla.setOpaque(false);

        JLabel lblTabla = new JLabel("Reportes Recientes en el Sistema");
        lblTabla.setFont(Estilos.SUBTITULO);
        panelTabla.add(lblTabla, BorderLayout.NORTH);

        tabla = new TablaReportes();
        panelTabla.add(tabla, BorderLayout.CENTER);

        // Contenedor central que une las tarjetas (Norte) y la tabla (Centro)
        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.setOpaque(false);
        centro.add(panelTarjetas, BorderLayout.NORTH);
        centro.add(panelTabla, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // Carga inicial de datos
        refrescar();
    }

     //Método clave que actualiza la información en pantalla. Es invocado por el DashboardFrame cada vez que el usuario vuelve a la vista de Inicio.

    public void refrescar() {
        // Limpiar las tarjetas actuales para evitar duplicados al refrescar
        panelTarjetas.removeAll();

        // 1. Lógica de visualización para el Administrador
        if (user.esAdministrador()) {
            int pendientes = 0;
            int entregadas = 0;

            for (Devolucion d : ds.listarTodas()) {
                if (d.getEstado() == EstadoDevolucion.ENTREGADA) {
                    entregadas++;
                } else {
                    pendientes++;
                }
            }

            panelTarjetas.add(new TarjetaEstadistica("Total Reportes", rs.listarReportes().size(), Estilos.AZUL_PRINCIPAL));
            panelTarjetas.add(new TarjetaEstadistica("Solicitudes Pendientes", pendientes, Estilos.ROJO));
            panelTarjetas.add(new TarjetaEstadistica("Devueltos Exitosos", entregadas, Estilos.VERDE));
            panelTarjetas.add(new TarjetaEstadistica("Coincidencias Activas", cs.listarCoincidencias().size(), Estilos.NARANJA));

            // 2. Lógica de visualización para el Usuario Normal
        } else {
            int misPerdidos = 0;
            int misEncontrados = 0;

            // Contar solo los reportes del usuario actual
            for (Reporte r : rs.listarPorUsuario(user)) {
                if (r instanceof ReportePerdido) {
                    misPerdidos++;
                } else if (r instanceof ReporteEncontrado) {
                    misEncontrados++;
                }
            }

            panelTarjetas.add(new TarjetaEstadistica("Mis Objetos Perdidos", misPerdidos, Estilos.AZUL_PRINCIPAL));
            panelTarjetas.add(new TarjetaEstadistica("Mis Objetos Encontrados", misEncontrados, Estilos.VERDE));
            panelTarjetas.add(new TarjetaEstadistica("Mis Solicitudes", ds.listarPorSolicitante(user).size(), Estilos.MORADO));
            panelTarjetas.add(new TarjetaEstadistica("Coincidencias Globales", cs.listarCoincidencias().size(), Estilos.NARANJA));
        }

        // Actualizar la tabla con los reportes más recientes
        tabla.cargar(rs.listarReportes());

        // Forzar a Swing a redibujar el panel de tarjetas tras añadir/quitar componentes
        panelTarjetas.revalidate();
        panelTarjetas.repaint();
    }
}