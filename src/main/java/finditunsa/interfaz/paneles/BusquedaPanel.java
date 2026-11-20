package finditunsa.interfaz.paneles;

import finditunsa.interfaz.componentes.Estilos;
import finditunsa.interfaz.componentes.TablaReportes;
import finditunsa.modelos.*;
import finditunsa.servicios.BusquedaService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Panel principal encargado de la búsqueda y filtrado de objetos reportados.
public class BusquedaPanel extends JPanel {
    private final BusquedaService busquedaService;
    private JTextField campoTexto;
    private JComboBox<String> campoCategoria;
    private JCheckBox chkOcultarDevueltos;
    private TablaReportes tabla;

    public BusquedaPanel(BusquedaService busquedaService) {
        this.busquedaService = busquedaService;
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO_CLARO);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Estructura principal: cabecera superior y área de filtros + tabla en el centro
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel enc = new JPanel();
        enc.setOpaque(false);
        enc.setLayout(new BoxLayout(enc, BoxLayout.Y_AXIS));
        enc.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        JLabel titulo = new JLabel("Buscar objetos");
        titulo.setFont(Estilos.TITULO_GRANDE);
        titulo.setForeground(Estilos.TEXTO_OSCURO);
        JLabel sub = new JLabel("Filtra por texto, categoría o tipo de reporte");
        sub.setFont(Estilos.TEXTO);
        sub.setForeground(Estilos.GRIS);
        enc.add(titulo);
        enc.add(Box.createVerticalStrut(4));
        enc.add(sub);
        return enc;
    }

    private JPanel crearContenido() {
        JPanel cont = new JPanel(new BorderLayout(0, 15));
        cont.setOpaque(false);
        cont.add(crearFiltros(), BorderLayout.NORTH);
        tabla = new TablaReportes();
        cont.add(tabla, BorderLayout.CENTER);
        return cont;
    }

    // Construye la barra de controles de filtrado y enlaza los eventos de búsqueda y limpieza.
    private JPanel crearFiltros() {
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtros.setBackground(Estilos.BLANCO);
        filtros.setBorder(BorderFactory.createLineBorder(new Color(225, 228, 234)));

        campoTexto = new JTextField(20);
        campoTexto.setFont(Estilos.TEXTO);

        // Carga dinámica de categorías incluyendo una opción por defecto en el índice 0
        String[] categorias = new String[CategoriaObjeto.values().length + 1];
        categorias[0] = "Todas las categorías";
        for (int i = 0; i < CategoriaObjeto.values().length; i++) {
            categorias[i + 1] = CategoriaObjeto.values()[i].toString();
        }
        campoCategoria = new JComboBox<>(categorias);

        chkOcultarDevueltos = new JCheckBox("Ocultar devueltos");
        chkOcultarDevueltos.setBackground(Estilos.BLANCO);

        JButton buscar = new JButton("Buscar");
        buscar.setBackground(Estilos.AZUL_PRINCIPAL);
        buscar.setForeground(Color.WHITE);
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { buscar(); }
        });

        // Restablece los filtros y vacía la tabla de resultados
        JButton limpiar = new JButton("Limpiar");
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoTexto.setText("");
                campoCategoria.setSelectedIndex(0);
                chkOcultarDevueltos.setSelected(false);
                tabla.cargar(new ArrayList<>());
            }
        });

        filtros.add(campoTexto);
        filtros.add(campoCategoria);
        filtros.add(chkOcultarDevueltos);
        filtros.add(buscar);
        filtros.add(limpiar);

        return filtros;
    }

    // Consulta al servicio con los criterios ingresados, aplica el filtro de estado y actualiza la tabla.
    private void buscar() {
        String texto = campoTexto.getText().trim();
        CategoriaObjeto categoria = null;
        int idxCat = campoCategoria.getSelectedIndex();

        // Se resta 1 para compensar la opción "Todas las categorías" en el índice 0
        if (idxCat > 0) categoria = CategoriaObjeto.values()[idxCat - 1];

        List<Reporte> resultados = busquedaService.buscarAvanzado(texto, categoria, null);

        // Excluye los reportes marcados como devueltos si la casilla está activa
        if (chkOcultarDevueltos.isSelected()) {
            List<Reporte> filtrados = new ArrayList<>();
            for (Reporte r : resultados) {
                if (r.getEstado() != EstadoReporte.DEVUELTO) {
                    filtrados.add(r);
                }
            }
            resultados = filtrados;
        }

        tabla.cargar(resultados);
    }
}
