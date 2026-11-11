package finditunsa.modelos;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Usuarios y autenticación
//DESCRIPCION: Define los roles disponibles para los usuarios.

//Clase de constantes con las etiquetas para USUARIO Y ADMINISTRADOR
public enum Rol {
    USUARIO("Usuario"),
    ADMINISTRADOR("Administrador");

    private final String descripcion;

    Rol(String descripcion) {
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