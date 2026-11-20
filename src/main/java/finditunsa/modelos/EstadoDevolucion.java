package finditunsa.modelos;

public enum EstadoDevolucion {
    SOLICITADA("Solicitada"),
    VERIFICADA("Verificada"),
    AUTORIZADA("Autorizada"),
    ENTREGADA("Entregada");

    private final String descripcion;
    
    EstadoDevolucion(String descripcion) { 
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
