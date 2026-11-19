package finditunsa.interfaz;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Usuario;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Autenticación y usuarios
//DESCRIPCION: Ventana principal de inicio de sesión conectada a AutenticacionService.
//             Permite validar credenciales para entrar al DashboardFrame o abrir RegistroFrame.

public class LoginFrame extends JFrame {

    // Servicios de negocio y repositorio: se reciben aquí para reenviarlos al DashboardFrame una vez iniciada la sesión.
    private final AutenticacionService autenticacion;
    private final ReporteService reporteService;
    private final BusquedaService busquedaService;
    private final CoincidenciaService coincidenciaService;
    private final DevolucionService devolucionService;
    private final UsuarioRepository usuarioRepository;

    // Componentes de entrada de texto del formulario
    private JTextField campoCorreo;
    private JPasswordField campoContrasena; // JPasswordField oculta los caracteres escritos por seguridad

    public LoginFrame(AutenticacionService autenticacion, ReporteService reporteService,
                      BusquedaService busquedaService, CoincidenciaService coincidenciaService,
                      DevolucionService devolucionService, UsuarioRepository usuarioRepository) {
        // Guardamos las referencias de los servicios recibidos desde Main
        this.autenticacion = autenticacion;
        this.reporteService = reporteService;
        this.busquedaService = busquedaService;
        this.coincidenciaService = coincidenciaService;
        this.devolucionService = devolucionService;
        this.usuarioRepository = usuarioRepository;

        // Configuraciones básicas de la ventana (JFrame)
        setTitle("FindIt UNSA - Iniciar Sesión");
        setSize(840, 520); // Ancho y alto fijo de la ventana en píxeles
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierra toda la aplicación al presionar la X
        setLocationRelativeTo(null); // Centra la ventana en la pantalla del usuario
        setLayout(new GridLayout(1, 2)); // Divide la ventana en 1 fila y 2 columnas del mismo tamaño
        setResizable(false); // Evita que el usuario deforme el diseño estirando los bordes

        // Agregamos las dos mitades de la pantalla
        add(crearPanelIzquierdo());
        add(crearPanelDerecho());
    }

    // Construye el panel izquierdo decorativo con la identidad visual de la aplicación
    private JPanel crearPanelIzquierdo() {
        // GridBagLayout centra automáticamente el contenido tanto vertical como horizontalmente
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.AZUL_PRINCIPAL);

        // Contenedor vertical para apilar el título, subtítulo y descripción
        JPanel contenido = new JPanel();
        contenido.setOpaque(false); // Transparente para que se vea el fondo azul del panel padre
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("FindIt UNSA");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Sistema de objetos perdidos");
        sub.setFont(Estilos.SUBTITULO);
        sub.setForeground(Estilos.AZUL_CLARO);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Usamos HTML básico dentro del JLabel para permitir saltos de línea y texto centrado
        JLabel desc = new JLabel("<html><div style='text-align:center;'>"
                + "Recupera lo que perdiste.<br/>Reporta lo que encontraste.</div></html>");
        desc.setFont(Estilos.TEXTO);
        desc.setForeground(Estilos.AZUL_CLARO);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregamos los textos dejando separaciones verticales (Strut) en píxeles
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(sub);
        contenido.add(Box.createVerticalStrut(30));
        contenido.add(desc);

        panel.add(contenido);
        return panel;
    }

    // Construye el panel derecho que contiene el formulario de inicio de sesión
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.FONDO_CLARO);

        // Contenedor del formulario alineado de arriba hacia abajo
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(320, 400));

        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(Estilos.TITULO_GRANDE);
        titulo.setForeground(Estilos.TEXTO_OSCURO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Etiqueta y caja de texto para el correo institucional
        JLabel lblCorreo = new JLabel("Correo institucional");
        lblCorreo.setFont(Estilos.TEXTO);
        lblCorreo.setForeground(Estilos.GRIS);
        lblCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoCorreo = new JTextField();
        campoCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // Ocupa 100% del ancho disponible y 35px de alto
        campoCorreo.setFont(Estilos.TEXTO);
        campoCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Etiqueta y caja de contraseña
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(Estilos.TEXTO);
        lblPass.setForeground(Estilos.GRIS);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoContrasena = new JPasswordField();
        campoContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoContrasena.setFont(Estilos.TEXTO);
        campoContrasena.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botón principal para validar credenciales y entrar al sistema
        JButton ingresar = new JButton("Ingresar");
        ingresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        ingresar.setBackground(Estilos.AZUL_PRINCIPAL);
        ingresar.setForeground(Color.WHITE);
        ingresar.setFont(Estilos.SUBTITULO);
        ingresar.setFocusPainted(false); // Quita el recuadro de enfoque punteado al hacer clic
        ingresar.setBorderPainted(false); // Diseño plano sin borde 3D por defecto
        ingresar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a mano al pasar encima
        ingresar.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingresar.addActionListener(new ManejadorLogin()); // Vincula el evento de clic al listener interno

        // Botón estilizado como enlace de texto para abrir la ventana de registro
        JButton crearCuenta = new JButton("¿No tienes cuenta? Crear una");
        crearCuenta.setFont(Estilos.TEXTO);
        crearCuenta.setForeground(Estilos.AZUL_PRINCIPAL);
        crearCuenta.setBackground(Estilos.FONDO_CLARO);
        crearCuenta.setBorderPainted(false);
        crearCuenta.setContentAreaFilled(false); // Elimina el fondo de botón para que parezca un hipervínculo
        crearCuenta.setFocusPainted(false);
        crearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        crearCuenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        crearCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana de registro sin cerrar el login para poder volver al terminar
                RegistroFrame registro = new RegistroFrame(autenticacion);
                registro.setVisible(true);
            }
        });

        // Etiqueta informativa con usuarios precargados para pruebas rápidas
        JLabel demo = new JLabel("<html><small>Demo:<br/>admin@unsa.edu.pe / admin123<br/>ana@unsa.edu.pe / 1234</small></html>");
        demo.setFont(Estilos.TEXTO_PEQUENO);
        demo.setForeground(Estilos.GRIS);
        demo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Armamos el formulario ordenando cada componente con espaciados verticales
        form.add(titulo);
        form.add(Box.createVerticalStrut(20));
        form.add(lblCorreo);
        form.add(Box.createVerticalStrut(5));
        form.add(campoCorreo);
        form.add(Box.createVerticalStrut(12));
        form.add(lblPass);
        form.add(Box.createVerticalStrut(5));
        form.add(campoContrasena);
        form.add(Box.createVerticalStrut(20));
        form.add(ingresar);
        form.add(Box.createVerticalStrut(10));
        form.add(crearCuenta);
        form.add(Box.createVerticalStrut(20));
        form.add(demo);

        panel.add(form);
        return panel;
    }

    // Clase interna que gestiona el evento al hacer clic en el botón "Ingresar"
    private class ManejadorLogin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Extraemos el correo limpio y convertimos el arreglo char[] de la contraseña a String
            String correo = campoCorreo.getText().trim();
            String pass = new String(campoContrasena.getPassword());

            // Validación rápida en interfaz antes de invocar al servicio
            if (correo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Debe completar todos los campos.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Delegamos la autenticación a la capa de servicio
                Usuario usuario = autenticacion.iniciarSesion(correo, pass);
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Bienvenido(a), " + usuario.getNombre(),
                        "Acceso concedido", JOptionPane.INFORMATION_MESSAGE);

                // Abrimos el panel principal pasando todos los servicios activos
                DashboardFrame dashboard = new DashboardFrame(
                        autenticacion, reporteService, busquedaService,
                        coincidenciaService, devolucionService, usuarioRepository);
                dashboard.setVisible(true);
                dispose(); // Libera y cierra la ventana de Login
            } catch (Exception ex) {
                // Muestra el error devuelto por Validador o AutenticacionService y limpia la contraseña
                JOptionPane.showMessageDialog(LoginFrame.this,
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                campoContrasena.setText("");
            }
        }
    }
}