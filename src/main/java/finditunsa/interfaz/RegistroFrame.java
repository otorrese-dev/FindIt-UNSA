package finditunsa.interfaz;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Rol;
import finditunsa.modelos.Usuario;
import finditunsa.servicios.AutenticacionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Autenticación y usuarios
//DESCRIPCION: Ventana emergente para el registro de cuentas nuevas con rol USUARIO.
//             Valida coincidencia de contraseñas y delega el guardado a AutenticacionService.

public class RegistroFrame extends JFrame {

    // Referencia al servicio de autenticación para registrar al nuevo usuario en memoria
    private final AutenticacionService autenticacion;

    // Componentes de entrada de datos del formulario
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmar;

    public RegistroFrame(AutenticacionService autenticacion) {
        this.autenticacion = autenticacion;

        // Configuraciones generales de la ventana de registro
        setTitle("FindIt UNSA - Crear cuenta");
        setSize(460, 560);
        // DISPOSE_ON_CLOSE cierra únicamente esta ventana sin detener la ejecución del LoginFrame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        setResizable(false);
        setLayout(new BorderLayout()); // Organiza la ventana en zona superior (NORTH) y central (CENTER)

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.CENTER);
    }

    // Construye la franja superior azul con el título e instrucciones de registro
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel();
        panel.setBackground(Estilos.AZUL_PRINCIPAL);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20)); // Márgenes internos

        JLabel titulo = new JLabel("Crear cuenta");
        titulo.setFont(Estilos.TITULO_GRANDE);
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Regístrate para reportar objetos perdidos y encontrados");
        sub.setFont(Estilos.TEXTO);
        sub.setForeground(Estilos.AZUL_CLARO);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(sub);
        return panel;
    }

    // Construye el cuerpo central con los campos de texto y la botonera inferior
    private JPanel crearFormulario() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(Estilos.FONDO_CLARO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 35, 20, 35));

        // GridBagLayout permite apilar etiquetas y campos ocupando el 100% del ancho disponible
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        campoNombre     = new JTextField();
        campoCorreo     = new JTextField();
        campoContrasena = new JPasswordField();
        campoConfirmar  = new JPasswordField();

        campoNombre.setFont(Estilos.TEXTO);
        campoCorreo.setFont(Estilos.TEXTO);
        campoContrasena.setFont(Estilos.TEXTO);
        campoConfirmar.setFont(Estilos.TEXTO);

        // Agregamos cada etiqueta y caja de entrada incrementando el índice de fila
        int fila = 0;
        form.add(etiqueta("Nombre completo"), gbc(fila++));
        form.add(campoNombre, gbc(fila++));
        form.add(etiqueta("Correo institucional"), gbc(fila++));
        form.add(campoCorreo, gbc(fila++));
        form.add(etiqueta("Contraseña (mínimo 4 caracteres)"), gbc(fila++));
        form.add(campoContrasena, gbc(fila++));
        form.add(etiqueta("Confirmar contraseña"), gbc(fila++));
        form.add(campoConfirmar, gbc(fila++));

        // Nota aclaratoria sobre el rol asignado por defecto
        JLabel nota = new JLabel("<html><small>Al registrarte se crea una cuenta con rol <b>Usuario</b>.</small></html>");
        nota.setFont(Estilos.TEXTO_PEQUENO);
        nota.setForeground(Estilos.GRIS);
        form.add(nota, gbc(fila++));

        contenedor.add(form, BorderLayout.CENTER);
        contenedor.add(crearBotones(), BorderLayout.SOUTH);
        return contenedor;
    }

    // Metodo auxiliar que genera las restricciones de posición para cada fila del GridBagLayout
    private GridBagConstraints gbc(int fila) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Columna única
        gbc.gridy = fila; // Posición vertical según el orden de inserción
        gbc.insets = new Insets(2, 0, 2, 0); // Espaciado superior e inferior de 2px
        gbc.fill = GridBagConstraints.HORIZONTAL; // Estira el componente a lo ancho
        gbc.weightx = 1.0; // Permite que el componente crezca horizontalmente
        return gbc;
    }

    // Metodo auxiliar para crear etiquetas de formulario con formato uniforme
    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Estilos.TEXTO);
        lbl.setForeground(Estilos.TEXTO_OSCURO);
        return lbl;
    }

    // Construye el panel inferior con los botones Cancelar y Registrarme en dos columnas iguales
    private JPanel crearBotones() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton cancelar = new JButton("Cancelar");
        cancelar.setFont(Estilos.TEXTO);
        cancelar.setBackground(Estilos.GRIS);
        cancelar.setForeground(Color.WHITE);
        cancelar.setFocusPainted(false);
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana de registro sin guardar cambios
            }
        });

        JButton registrar = new JButton("Registrarme");
        registrar.setFont(Estilos.SUBTITULO);
        registrar.setBackground(Estilos.AZUL_PRINCIPAL);
        registrar.setForeground(Color.WHITE);
        registrar.setFocusPainted(false);
        registrar.addActionListener(new ManejadorRegistro());

        panel.add(cancelar);
        panel.add(registrar);
        return panel;
    }

    // Clase interna que procesa el envío del formulario al hacer clic en "Registrarme"
    private class ManejadorRegistro implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtenemos los valores ingresados limpiando espacios innecesarios en nombre y correo
            String nombre     = campoNombre.getText().trim();
            String correo     = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword());
            String confirmar  = new String(campoConfirmar.getPassword());

            // Validación de campos obligatorios antes de invocar la lógica de negocio
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
                JOptionPane.showMessageDialog(RegistroFrame.this,
                        "Complete todos los campos.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificamos que ambas contraseñas ingresadas sean idénticas
            if (!contrasena.equals(confirmar)) {
                JOptionPane.showMessageDialog(RegistroFrame.this,
                        "Las contraseñas no coinciden.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Registramos al usuario asignando automáticamente el rol estándar (Rol.USUARIO)
                Usuario u = autenticacion.registrarUsuario(nombre, correo, contrasena, Rol.USUARIO);
                JOptionPane.showMessageDialog(RegistroFrame.this,
                        "Cuenta creada correctamente.\nYa puedes iniciar sesión con:\n" + u.getCorreo(),
                        "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cierra la ventana para volver al LoginFrame
            } catch (Exception ex) {
                // Captura y muestra errores de validación (correo inválido, duplicado o clave corta)
                JOptionPane.showMessageDialog(RegistroFrame.this,
                        ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}