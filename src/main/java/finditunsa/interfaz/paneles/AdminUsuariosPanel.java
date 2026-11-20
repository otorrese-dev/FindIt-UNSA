package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.modelos.Rol;
import finditunsa.modelos.Usuario;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.AutenticacionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Autenticación y usuarios
//DESCRIPCION: Panel administrativo del Dashboard que lista los usuarios registrados en el sistema
//             y permite crear nuevas cuentas con rol ADMINISTRADOR.

public class AdminUsuariosPanel extends JPanel {

    // Modelo de datos que administra las filas y columnas de la tabla visual
    private final DefaultTableModel modelo;

    public AdminUsuariosPanel(final UsuarioRepository repo, final AutenticacionService auth) {
        // BorderLayout organiza el título arriba (NORTH), la tabla al centro (CENTER) y el botón abajo (SOUTH)
        setLayout(new BorderLayout(0, 15));
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Encabezado superior de la vista
        JLabel title = new JLabel("Gestión de Usuarios");
        title.setFont(Estilos.TITULO_GRANDE);
        title.setForeground(Estilos.TEXTO_OSCURO);
        add(title, BorderLayout.NORTH);

        // Inicializamos el modelo de tabla con 4 columnas y celdas bloqueadas contra edición directa
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Configuración visual de la tabla siguiendo la paleta del sistema
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.setFont(Estilos.TEXTO);
        tabla.setGridColor(new Color(230, 233, 238));
        tabla.getTableHeader().setFont(Estilos.SUBTITULO);
        tabla.getTableHeader().setBackground(Estilos.AZUL_CLARO);
        tabla.setSelectionBackground(Estilos.AZUL_CLARO);
        tabla.setSelectionForeground(Estilos.TEXTO_OSCURO);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior alineado a la derecha para el botón de acción administrativa
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBtn.setOpaque(false);

        JButton btnCrearAdmin = new JButton("Crear Nuevo Administrador");
        btnCrearAdmin.setFont(Estilos.SUBTITULO);
        btnCrearAdmin.setBackground(Estilos.AZUL_PRINCIPAL);
        btnCrearAdmin.setForeground(Color.WHITE);
        btnCrearAdmin.setFocusPainted(false);
        btnCrearAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Solicita los datos mediante cuadros de diálogo secuenciales y registra el nuevo administrador
        btnCrearAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = JOptionPane.showInputDialog(AdminUsuariosPanel.this, "Nombre:");
                if (nom == null) return; // Si el usuario presiona Cancelar, aborta sin mostrar error

                String cor = JOptionPane.showInputDialog(AdminUsuariosPanel.this, "Correo:");
                if (cor == null) return;

                String pas = JOptionPane.showInputDialog(AdminUsuariosPanel.this, "Contraseña:");
                if (pas == null) return;

                try {
                    // Registra la cuenta asignando explícitamente el rol ADMINISTRADOR
                    auth.registrarUsuario(nom, cor, pas, Rol.ADMINISTRADOR);
                    JOptionPane.showMessageDialog(AdminUsuariosPanel.this,
                            "Administrador creado exitosamente.",
                            "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    actualizar(repo); // Refresca la tabla para mostrar el nuevo registro
                } catch (Exception ex) {
                    // Muestra errores de validación (correo duplicado, formato inválido o clave corta)
                    JOptionPane.showMessageDialog(AdminUsuariosPanel.this,
                            ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pnlBtn.add(btnCrearAdmin);
        add(pnlBtn, BorderLayout.SOUTH);

        // Carga inicial de los usuarios existentes al abrir el panel
        actualizar(repo);
    }

    // Limpia la tabla y vuelve a poblarla con la lista actualizada del repositorio
    private void actualizar(UsuarioRepository repo) {
        modelo.setRowCount(0);
        for (Usuario u : repo.listar()) {
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getRol()
            });
        }
    }
}