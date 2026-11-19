package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Usuario;
import finditunsa.servicios.AutenticacionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Autenticación y usuarios
//DESCRIPCION: Panel interno del Dashboard donde el usuario autenticado visualiza
//             y actualiza su nombre, correo electrónico o contraseña.

public class MiPerfilPanel extends JPanel {

    public MiPerfilPanel(final AutenticacionService auth) {
        // Obtenemos los datos del usuario que tiene la sesión iniciada
        Usuario u = auth.getUsuarioActual();

        // GridBagLayout centra la tarjeta del formulario en el área disponible del Dashboard
        setLayout(new GridBagLayout());
        setBackground(Estilos.FONDO_CLARO);

        // Contenedor tipo tarjeta blanca con borde gris suave y margen interno
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Estilos.BLANCO);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 234)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel title = new JLabel("Mi Perfil");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Etiquetas descriptivas alineadas a la izquierda para mantener uniformidad en BoxLayout
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(Estilos.TEXTO);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(Estilos.TEXTO);
        lblCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPass = new JLabel("Nueva Contraseña (vacío = no cambiar):");
        lblPass.setFont(Estilos.TEXTO);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Campos precargados con la información actual del usuario (campo de clave inicia vacío)
        final JTextField txtNombre = new JTextField(u != null ? u.getNombre() : "", 20);
        final JTextField txtCorreo = new JTextField(u != null ? u.getCorreo() : "", 20);
        final JPasswordField txtPass = new JPasswordField(20);

        txtNombre.setFont(Estilos.TEXTO);
        txtCorreo.setFont(Estilos.TEXTO);
        txtPass.setFont(Estilos.TEXTO);

        // Ajustamos altura fija de 35px y alineación izquierda en las cajas de entrada
        txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        txtNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCorreo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Botón principal para confirmar y guardar la edición del perfil
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(Estilos.SUBTITULO);
        btnGuardar.setBackground(Estilos.AZUL_PRINCIPAL);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ensamblaje vertical de los elementos dentro de la tarjeta
        form.add(title);
        form.add(Box.createVerticalStrut(20));
        form.add(lblNombre);
        form.add(Box.createVerticalStrut(4));
        form.add(txtNombre);
        form.add(Box.createVerticalStrut(12));
        form.add(lblCorreo);
        form.add(Box.createVerticalStrut(4));
        form.add(txtCorreo);
        form.add(Box.createVerticalStrut(12));
        form.add(lblPass);
        form.add(Box.createVerticalStrut(4));
        form.add(txtPass);
        form.add(Box.createVerticalStrut(20));
        form.add(btnGuardar);

        // Evento de clic: delega la validación y actualización al servicio de autenticación
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    auth.actualizarPerfil(
                            txtNombre.getText(),
                            txtCorreo.getText(),
                            new String(txtPass.getPassword())
                    );
                    JOptionPane.showMessageDialog(MiPerfilPanel.this,
                            "Perfil actualizado.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtPass.setText(""); // Limpia el campo de contraseña por seguridad
                } catch (Exception ex) {
                    // Muestra mensajes de error si el nombre, correo o clave nueva no son válidos
                    JOptionPane.showMessageDialog(MiPerfilPanel.this,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(form);
    }
}