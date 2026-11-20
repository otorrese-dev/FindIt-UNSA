package finditunsa.interfaz;

import finditunsa.interfaz.componentes.HeaderUsuario;
import finditunsa.interfaz.componentes.MenuLateral;
import finditunsa.interfaz.paneles.*;
import finditunsa.modelos.Usuario;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Ventana principal del sistema que organiza la navegación por paneles mediante CardLayout
// según el rol del usuario autenticado.
public class DashboardFrame extends JFrame {
    private final AutenticacionService auth;
    private final ReporteService reporteService;
    private final BusquedaService busquedaService;
    private final CoincidenciaService coincidenciaService;
    private final DevolucionService devolucionService;
    private final UsuarioRepository usuarioRepo;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelCentral = new JPanel(cardLayout);
    private InicioPanel inicioPanel;

    public DashboardFrame(AutenticacionService auth, ReporteService rs, BusquedaService bs,
                          CoincidenciaService cs, DevolucionService ds, UsuarioRepository ur) {
        this.auth = auth; this.reporteService = rs; this.busquedaService = bs;
        this.coincidenciaService = cs; this.devolucionService = ds; this.usuarioRepo = ur;
        Usuario user = auth.getUsuarioActual();

        setTitle("FindIt UNSA - Panel Principal");
        setSize(1250, 750);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Vistas comunes disponibles para cualquier rol
        inicioPanel = new InicioPanel(rs, cs, ds, user);
        panelCentral.add(inicioPanel, "inicio");
        panelCentral.add(new BusquedaPanel(bs), "buscar");
        panelCentral.add(new CoincidenciasPanel(cs), "coincidencias");
        panelCentral.add(new MiPerfilPanel(auth), "perfil");

        // Carga condicional de vistas exclusivas de administrador o usuario estándar
        if (user.esAdministrador()) {
            panelCentral.add(new AdminDevolucionesPanel(ds), "admin_dev");
            panelCentral.add(new AdminUsuariosPanel(usuarioRepo, auth), "admin_usr");
            panelCentral.add(new AdminReportesPanel(rs), "admin_rep");
        } else {
            panelCentral.add(new MisReportesPanel(rs, user), "mis_reportes");
            panelCentral.add(new DevolucionesPanel(rs, ds, user), "devoluciones");
        }

        MenuLateral menu = new MenuLateral(user);
        
        // Controla el cambio de vista en el CardLayout usando el actionCommand de cada botón
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comando = e.getActionCommand();
                if (comando.equals("salir")) {
                    cerrarSesion();
                } else {
                    if (comando.equals("inicio")) {
                        inicioPanel.refrescar();
                    }
                    cardLayout.show(panelCentral, comando);
                }
            }
        };

        // Vincula el listener de navegación a todos los botones del menú lateral
        Component[] comps = menu.getComponents();
        for (int i = 0; i < comps.length; i++) {
            Component c = comps[i];
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(listener);
            }
        }

        add(new HeaderUsuario(user), BorderLayout.NORTH);
        add(menu, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
    }

    // Limpia la sesión actual, vuelve a la pantalla de inicio de sesión y cierra esta ventana.
    private void cerrarSesion() {
        auth.cerrarSesion();
        new LoginFrame(auth, reporteService, busquedaService, coincidenciaService, devolucionService, usuarioRepo).setVisible(true);
        dispose();
    }
}
