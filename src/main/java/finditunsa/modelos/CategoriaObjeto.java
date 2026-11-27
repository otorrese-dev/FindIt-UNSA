package finditunsa.modelos;

import java.io.Serializable;
//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Categorías disponibles para clasificar objetos.

public enum CategoriaObjeto implements Serializable{
    ELECTRONICA("Electrónica"),
    DOCUMENTOS("Documentos"),
    ROPA("Ropa"),
    ACCESORIOS("Accesorios"),
    MOCHILAS("Mochilas"),
    LIBROS("Libros"),
    OTROS("Otros");

    private final String descripcion;

    CategoriaObjeto(String descripcion) {
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