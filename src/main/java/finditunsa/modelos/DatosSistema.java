package finditunsa.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// SPRINT: 3
// RESPONSABLE: INTEGRACION (INTEGRANTE 3 - LIDER)
// MODULO: Persistencia de Archivos
// Agrupa las listas del sistema para guardarlas en un archivo .dat.
public class DatosSistema implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<Usuario> usuarios = new ArrayList<>();
    public List<Reporte> reportes = new ArrayList<>();
    public List<Coincidencia> coincidencias = new ArrayList<>();
    public List<Devolucion> devoluciones = new ArrayList<>();
}
