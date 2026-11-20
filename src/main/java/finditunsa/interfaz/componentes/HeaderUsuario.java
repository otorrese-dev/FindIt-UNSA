package finditunsa.interfaz.componentes;

import finditunsa.modelos.Usuario;

import javax.swing.*;
import java.awt.*;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Integración y navegación
//DESCRIPCION: Barra superior del DashboardFrame que muestra el nombre y el rol
//             del usuario autenticado en la sesión actual.

public class HeaderUsuario extends JPanel {

    public HeaderUsuario(Usuario usuario) {
        // FlowLayout alineado a la derecha con 20px de margen horizontal y 10px vertical
        setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        setBackground(Estilos.BLANCO);
        // MatteBorder dibuja únicamente una línea divisoria de 1px en el borde inferior
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 228, 234)));

        // Obtenemos el nombre y la descripción del rol con protección ante valores nulos
        String nombre = (usuario != null) ? usuario.getNombre() : "";
        String rolTexto = (usuario != null && usuario.getRol() != null)
                ? usuario.getRol().getDescripcion().toUpperCase()
                : "";

        // Etiqueta principal con el nombre del usuario en sesión
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(Estilos.SUBTITULO);
        lblNombre.setForeground(Estilos.TEXTO_OSCURO);

        // Etiqueta secundaria que indica el rol (Usuario o Administrador) en mayúsculas
        JLabel lblRol = new JLabel("· " + rolTexto);
        lblRol.setFont(Estilos.TEXTO_PEQUENO);
        lblRol.setForeground(Estilos.GRIS);

        // Agregamos ambas etiquetas en orden de izquierda a derecha
        add(lblNombre);
        add(lblRol);
    }
}