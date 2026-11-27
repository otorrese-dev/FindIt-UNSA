package finditunsa.interfaz.componentes;

import java.awt.Color;
import java.awt.Font;

//MODULO: Componentes reutilizables
//DESCRIPCION: Paleta de colores y fuentes del sistema gráfico (Tema UNSA Vino).

public final class Estilos {
    private Estilos() {}

    // --- PALETA DE COLORES (Tema Vino/Borgoña) ---
    // Mantenemos los nombres de las variables (AZUL_...) para no romper los demás archivos,
    // pero ahora tienen valores rojizos.

    public static final Color AZUL_PRINCIPAL = new Color(114, 28, 36);  // Vino principal
    public static final Color AZUL_OSCURO    = new Color(74, 14, 20);   // Vino oscuro (Menú lateral)
    public static final Color AZUL_CLARO     = new Color(245, 230, 230);// Fondo de tablas y detalles
    public static final Color FONDO_CLARO    = new Color(250, 250, 250);// Fondo general grisáceo
    public static final Color BLANCO         = Color.WHITE;
    public static final Color TEXTO_OSCURO   = new Color(33, 37, 41);
    public static final Color GRIS           = new Color(108, 117, 125);
    public static final Color VERDE          = new Color(40, 167, 69);
    public static final Color ROJO            = new Color(220, 53, 69);
    public static final Color NARANJA         = new Color(253, 126, 20);
    public static final Color MORADO          = new Color(111, 66, 193);

    // --- TIPOGRAFÍA ---
    // Georgia para títulos y Segoe UI para los textos pequeños.

    public static final Font TITULO_GRANDE = new Font("Georgia", Font.BOLD, 26);
    public static final Font TITULO        = new Font("Georgia", Font.BOLD, 20);
    public static final Font SUBTITULO     = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font TEXTO         = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font TEXTO_PEQUENO = new Font("Segoe UI", Font.PLAIN, 12);
}
