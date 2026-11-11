package finditunsa.modelos;

import java.time.LocalDateTime;

//RESPONSABLE: INTEGRANTE 2
//MODULO: Registro y gestión de objetos
//DESCRIPCION: Reporte de un objeto perdido.

public class ReportePerdido extends Reporte {

    public ReportePerdido() {
        super();
    }

    public ReportePerdido(int id, Usuario usuario, Objeto objeto,
                          LocalDateTime fechaRegistro, EstadoReporte estado) {
        super(id, usuario, objeto, fechaRegistro, estado);
    }

    @Override
    public String getTipoReporte() {
        return "PERDIDO";
    }
}