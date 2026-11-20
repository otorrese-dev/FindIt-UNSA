package finditunsa.interfaz.componentes;

import finditunsa.modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Integración y navegación
//DESCRIPCION: Barra lateral de navegación del DashboardFrame. Construye dinámicamente
//             las opciones de menú disponibles según el rol del usuario autenticado.

public class MenuLateral extends JPanel {

    public MenuLateral(Usuario usuario) {
        // BoxLayout en eje Y apila el logotipo y los botones de arriba hacia abajo
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Estilos.AZUL_OSCURO);
        setPreferredSize(new Dimension(230, 0)); // Ancho fijo de 230px; la altura se adapta al JFrame
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Cabecera visual del menú con el nombre de la aplicación
        JLabel logo = new JLabel("FindIt UNSA");
        logo.setFont(Estilos.TITULO);
        logo.setForeground(Estilos.BLANCO);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        JLabel sub = new JLabel("COMUNIDAD UNIVERSITARIA");
        sub.setFont(Estilos.TEXTO_PEQUENO);
        sub.setForeground(Estilos.AZUL_CLARO);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(sub);
        add(Box.createVerticalStrut(30));

        // Carga condicional de botones dependiendo de si el usuario tiene privilegios de administrador
        if (usuario != null && usuario.esAdministrador()) {
            agregarBoton("Inicio", "inicio");
            agregarBoton("Buscar", "buscar");
            agregarBoton("Coincidencias", "coincidencias");
            agregarSeparador();
            agregarBoton("Solicitudes Devolución", "admin_dev");
            agregarBoton("Gestión Usuarios", "admin_usr");
            agregarBoton("Gestión Reportes", "admin_rep");
        } else {
            agregarBoton("Inicio", "inicio");
            agregarBoton("Buscar", "buscar");
            agregarBoton("Mis reportes", "mis_reportes");
            agregarBoton("Coincidencias", "coincidencias");
            agregarBoton("Devoluciones", "devoluciones");
        }

        // Opción común para cualquier rol
        agregarBoton("Mi perfil", "perfil");

        // VerticalGlue actúa como espacio elástico que empuja los siguientes elementos al fondo
        add(Box.createVerticalGlue());
        agregarSeparador();
        agregarBoton("Cerrar sesión", "salir");
    }

    // Crea un botón estilizado, le asigna su comando de navegación y configura el efecto hover
    public void agregarBoton(String texto, String actionCommand) {
        final JButton boton = new JButton(texto);
        // El actionCommand permite al DashboardFrame identificar qué panel mostrar al hacer clic
        boton.setActionCommand(actionCommand);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); // Ocupa el 100% del ancho de la barra
        boton.setBackground(Estilos.AZUL_OSCURO);
        boton.setForeground(Estilos.BLANCO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setFont(Estilos.TEXTO);
        boton.setHorizontalAlignment(SwingConstants.LEFT); // Alinea el texto del botón a la izquierda
        boton.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Cambia el color de fondo del botón cuando el puntero entra o sale de su área
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Estilos.AZUL_PRINCIPAL);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Estilos.AZUL_OSCURO);
            }
        });

        add(boton);
        add(Box.createVerticalStrut(3)); // Pequeña separación vertical entre botones
    }

    // Dibuja una línea horizontal sutil para agrupar secciones dentro del menú
    public void agregarSeparador() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(60, 90, 150));
        add(sep);
        add(Box.createVerticalStrut(10));
    }
}