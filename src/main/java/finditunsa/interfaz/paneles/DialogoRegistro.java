package finditunsa.interfaz.paneles;

import finditunsa.modelos.*;
import finditunsa.servicios.ReporteService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y visualización de objetos
//DESCRIPCION: Ventana modal (JDialog) que contiene el formulario para registrar un nuevo objeto (perdido o encontrado). Utiliza componentes
//avanzados como JRadioButton, ButtonGroup, JComboBox y JTextArea.

public class DialogoRegistro extends JDialog {

    public DialogoRegistro(Window padre, final ReporteService rs, final Usuario usuario) {
        // Configuración básica de la ventana modal
        super(padre, "Registrar Objeto", ModalityType.APPLICATION_MODAL);
        setSize(450, 550);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        // Panel principal que organiza el formulario en una grilla de 8 filas x 2 columnas
        JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //COMPONENTES DE SELECCIÓN
        // Uso de JRadioButton para selección excluyente y ButtonGroup para agruparlos
        JRadioButton rbPerdido = new JRadioButton("Objeto Perdido", true);
        JRadioButton rbEncontrado = new JRadioButton("Objeto Encontrado");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbPerdido);
        grupoTipo.add(rbEncontrado);

        // Panel auxiliar para alinear los radio buttons a la izquierda
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRadio.add(rbPerdido);
        panelRadio.add(rbEncontrado);

        //CAMPOS DE TEXTO Y LISTAS
        final JTextField txtNombre = new JTextField();
        final JComboBox<CategoriaObjeto> cbCat = new JComboBox<>(CategoriaObjeto.values());
        final JTextArea txtDesc = new JTextArea(3, 20); // JTextArea para descripciones largas
        final JTextField txtUbi = new JTextField();
        final JTextField txtFecha = new JTextField(LocalDate.now().toString()); // Fecha actual por defecto
        final JTextField txtCarac = new JTextField();

        //ENSAMBLADO DEL FORMULARIO (Etiqueta + Componente)
        form.add(new JLabel("Tipo de Reporte:")); form.add(panelRadio);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Categoría:")); form.add(cbCat);
        form.add(new JLabel("Descripción:")); form.add(new JScrollPane(txtDesc)); // Scroll en caso de mucho texto
        form.add(new JLabel("Ubicación:")); form.add(txtUbi);
        form.add(new JLabel("Fecha (YYYY-MM-DD):")); form.add(txtFecha);
        form.add(new JLabel("Características:")); form.add(txtCarac);

        // Botón para guardar los datos
        JButton btnGuardar = new JButton("Guardar");
        form.add(new JLabel("")); // Celda vacía para empujar el botón a la derecha
        form.add(btnGuardar);

        add(form, BorderLayout.CENTER);

        //PROGRAMACIÓN ORIENTADA A EVENTOS
        // Listener del botón Guardar usando una clase anónima
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Instanciar el modelo Objeto con los datos capturados del formulario
                    Objeto o = new Objeto(
                            txtNombre.getText(),
                            txtDesc.getText(),
                            (CategoriaObjeto) cbCat.getSelectedItem(),
                            "", "", // Color y Marca omitidos para simplificar el formulario
                            txtUbi.getText(),
                            LocalDate.parse(txtFecha.getText()), // Convierte el String a LocalDate
                            txtCarac.getText()
                    );

                    // 2. Evaluar qué RadioButton está seleccionado para llamar al método correcto
                    if (rbPerdido.isSelected()) {
                        rs.registrarReportePerdido(usuario, o);
                    } else {
                        rs.registrarReporteEncontrado(usuario, o);
                    }

                    // 3. Feedback al usuario y cierre de la ventana modal
                    JOptionPane.showMessageDialog(DialogoRegistro.this, "Registro exitoso.");
                    dispose(); // Cierra la ventana y devuelve el control al panel que lo invocó

                } catch (Exception ex) {
                    // Manejo de excepciones (ej. si el usuario escribe mal la fecha)
                    JOptionPane.showMessageDialog(DialogoRegistro.this,
                            "Error: " + ex.getMessage(),
                            "Error al registrar",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}