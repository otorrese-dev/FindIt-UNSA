package finditunsa;

import finditunsa.archivos.GestorArchivosBinarios;
import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.archivos.GestorArchivosTexto;
import finditunsa.interfaz.LoginFrame;
import finditunsa.modelos.DatosSistema;
import finditunsa.modelos.Rol;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.AutenticacionService;
import finditunsa.servicios.BusquedaService;
import finditunsa.servicios.CoincidenciaService;
import finditunsa.servicios.DevolucionService;
import finditunsa.servicios.ReporteService;
import javax.swing.SwingUtilities;

// MODULO: Arranque y Persistencia
//DESCRIPCION: Carga los datos persistidos, inicializa los servicios y abre el login.
public class Main {

    /**
     * Punto de entrada principal de la aplicación.
     * Restaura el estado previo desde disco, inyecta dependencias en los servicios
     * e inicia la interfaz gráfica en el hilo de despacho de eventos (EDT).
     */
    public static void main(String[] args) {
        // Carga el estado global del sistema (.dat) y muestra registros previos (.bin y .txt) en consola
        DatosSistema datos = GestorArchivosObjetos.cargar();
        GestorArchivosBinarios.leerEstadisticas();
        GestorArchivosTexto.leerHistorial();

        // Inicializa el repositorio y los servicios compartiendo la misma instancia de DatosSistema
        UsuarioRepository usuarioRepository = new UsuarioRepository(datos);
        AutenticacionService autenticacion = new AutenticacionService(usuarioRepository);
        ReporteService reporteService = new ReporteService(datos);
        BusquedaService busquedaService = new BusquedaService(reporteService);
        CoincidenciaService coincidenciaService = new CoincidenciaService(reporteService, datos);
        DevolucionService devolucionService = new DevolucionService(reporteService, datos);

        // Si es la primera ejecución (sin usuarios registrados), crea cuentas de prueba por defecto
        if (datos.usuarios.isEmpty()) {
            cargarDatosDemo(autenticacion);
            GestorArchivosTexto.registrarActividad("Sistema inicializado. Datos demo creados.");
        }

        // Lanza la ventana de inicio de sesión de forma segura en el hilo gráfico de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame login = new LoginFrame(autenticacion, reporteService, busquedaService,
                        coincidenciaService, devolucionService, usuarioRepository);
                login.setVisible(true);
            }
        });
    }

    // Registra un usuario administrador y un usuario estándar iniciales para pruebas del sistema.
    private static void cargarDatosDemo(AutenticacionService autenticacion) {
        try {
            autenticacion.registrarUsuario("Administrador FindIt UNSA", "admin@unsa.edu.pe", "admin123", Rol.ADMINISTRADOR);
        } catch (Exception ignored) { }
        try {
            autenticacion.registrarUsuario("Ana Estudiante", "ana@unsa.edu.pe", "1234", Rol.USUARIO);
        } catch (Exception ignored) { }
    }
}
