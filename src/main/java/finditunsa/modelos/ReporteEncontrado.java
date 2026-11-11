package finditunsa.modelos;

import java.time.LocalDateTime;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Reporte de un objeto encontrado.

public class ReporteEncontrado extends Reporte {

    public ReporteEncontrado() {
        super();
    }

    public ReporteEncontrado(int id, Usuario usuario, Objeto objeto,
                             LocalDateTime fechaRegistro, EstadoReporte estado) {
        super(id, usuario, objeto, fechaRegistro, estado);
    }

    @Override
    public String getTipoReporte() {
        return "ENCONTRADO";
    }
}