package finditunsa;

import finditunsa.interfaz.LoginFrame;
import finditunsa.modelos.Rol;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.AutenticacionService;
import finditunsa.servicios.BusquedaService;
import finditunsa.servicios.CoincidenciaService;
import finditunsa.servicios.DevolucionService;
import finditunsa.servicios.ReporteService;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar la lógica (Sprint 1)
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        AutenticacionService autenticacion = new AutenticacionService(usuarioRepository);
        ReporteService reporteService = new ReporteService();
        BusquedaService busquedaService = new BusquedaService(reporteService);
        CoincidenciaService coincidenciaService = new CoincidenciaService(reporteService);
        DevolucionService devolucionService = new DevolucionService(reporteService);

        // 2. Cargar datos demo
        cargarDatosDemo(autenticacion);

        // 3. Abrir la ventana (Exactamente como en la página 15 del PDF)
        LoginFrame login = new LoginFrame(
                autenticacion, reporteService, busquedaService,
                coincidenciaService, devolucionService, usuarioRepository
        );
        login.setVisible(true);
    }

    private static void cargarDatosDemo(AutenticacionService autenticacion) {
        try {
            autenticacion.registrarUsuario("Administrador FindIt UNSA",
                    "admin@unsa.edu.pe", "admin123", Rol.ADMINISTRADOR);
        } catch (Exception e) {}

        try {
            autenticacion.registrarUsuario("Ana Estudiante",
                    "ana@unsa.edu.pe", "1234", Rol.USUARIO);
        } catch (Exception e) {}
    }
}
