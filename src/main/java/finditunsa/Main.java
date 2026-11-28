package finditunsa;

import finditunsa.archivos.GestorArchivosBinarios;
import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.archivos.GestorArchivosTexto;
import finditunsa.interfaz.LoginFrame;
import finditunsa.modelos.DatosSistema;
import finditunsa.modelos.Rol;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.*;

/**
 * RESPONSABLE: INTEGRACION (INTEGRANTE 3 - LIDER)
 * MODULO: Arranque y Persistencia
 */
public class Main {
    public static void main(String[] args) {
        
        // 1. CARGAR DATOS DEL ARCHIVO .DAT
        DatosSistema datos = GestorArchivosObjetos.cargar();
        
        // 2. LEER ARCHIVOS COMPLEMENTARIOS
        GestorArchivosBinarios.leerEstadisticas();
        GestorArchivosTexto.leerHistorial();

        // 3. INICIALIZAR SERVICIOS
        UsuarioRepository usuarioRepository = new UsuarioRepository(datos);
        AutenticacionService autenticacion = new AutenticacionService(usuarioRepository);
        ReporteService reporteService = new ReporteService(datos);
        BusquedaService busquedaService = new BusquedaService(reporteService);
        CoincidenciaService coincidenciaService = new CoincidenciaService(reporteService, datos);
        DevolucionService devolucionService = new DevolucionService(reporteService, datos);

        // 4. CARGAR DATOS DEMO SI ESTÁ VACÍO
        if (datos.usuarios.isEmpty()) {
            cargarDatosDemo(autenticacion);
            GestorArchivosTexto.registrarActividad("Sistema inicializado. Datos demo creados.");
        }

        // 5. CREAR/ACTUALIZAR EL ARCHIVO BINARIO AL INICIAR
        // Esto asegura que estadisticas.bin exista siempre, incluso si no hay devoluciones aún.
        GestorArchivosBinarios.guardarEstadisticas(
                datos.usuarios.size(),
                datos.reportes.size(),
                datos.devoluciones.size()
        );

        // 6. ABRIR INTERFAZ
        LoginFrame login = new LoginFrame(
                autenticacion, reporteService, busquedaService,
                coincidenciaService, devolucionService, usuarioRepository
        );
        login.setVisible(true);
    }

    private static void cargarDatosDemo(AutenticacionService autenticacion) {
        try {
            autenticacion.registrarUsuario("Administrador FindIt UNSA", "admin@unsa.edu.pe", "admin123", Rol.ADMINISTRADOR);
        } catch (Exception ignored) {}
        try {
            autenticacion.registrarUsuario("Ana Estudiante", "ana@unsa.edu.pe", "1234", Rol.USUARIO);
        } catch (Exception ignored) {}
    }
}
