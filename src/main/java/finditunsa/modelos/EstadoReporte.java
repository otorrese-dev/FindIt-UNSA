package finditunsa.modelos;


//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Estados posibles de un reporte.

public enum EstadoReporte {
    REGISTRADO("Registrado"),
    EN_BUSQUEDA("En búsqueda"),
    POSIBLE_COINCIDENCIA("Posible coincidencia"),
    DEVUELTO("Devuelto"),
    CERRADO("Cerrado");

    private final String descripcion;

    EstadoReporte(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}