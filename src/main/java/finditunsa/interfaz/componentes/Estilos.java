package finditunsa.interfaz.componentes;

import java.awt.Color;
import java.awt.Font;

//RESPONSABLE: INTEGRACIÓN (todos los integrantes)
//MODULO: Componentes reutilizables
//DESCRIPCION: Paleta de colores y tipografías centralizadas para la interfaz.

public final class Estilos {

    private Estilos() {}

    //olores base y fondos
    public static final Color AZUL_PRINCIPAL = new Color(33, 82, 165);
    public static final Color AZUL_OSCURO    = new Color(22, 56, 115);
    public static final Color AZUL_CLARO     = new Color(220, 233, 250);
    public static final Color FONDO_CLARO    = new Color(245, 247, 250);
    public static final Color BLANCO         = Color.WHITE;
    public static final Color TEXTO_OSCURO   = new Color(40, 40, 40);
    public static final Color GRIS           = new Color(130, 130, 130);

    //Colores para estados y alertas
    public static final Color VERDE          = new Color(46, 160, 67);
    public static final Color ROJO           = new Color(200, 50, 50);
    public static final Color NARANJA        = new Color(230, 130, 30);
    public static final Color MORADO         = new Color(140, 80, 200);

    //Tipografías (Fuentes)
    public static final Font TITULO_GRANDE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font TITULO        = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font SUBTITULO     = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font TEXTO         = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font TEXTO_PEQUENO = new Font("Segoe UI", Font.PLAIN, 12);
}