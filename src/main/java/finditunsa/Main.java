package finditunsa;

import finditunsa.modelos.*;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.servicios.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// RESPONSABLE: INTEGRACIÓN (INTEGRANTES 1, 2 y 3)
// MODULO: Consola e integración
// DESCRIPCION: Menú principal en consola que instancia y conecta todos los servicios del Sprint 1.
public class Main {
    // Herramientas globales para lectura por teclado y formateo de fechas en pantalla
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Inyección de dependencias: se instancian los repositorios y servicios compartidos en memoria
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static final AutenticacionService autenticacionService = new AutenticacionService(usuarioRepository);
    private static final ReporteService reporteService = new ReporteService();
    private static final BusquedaService busquedaService = new BusquedaService(reporteService);
    private static final CoincidenciaService coincidenciaService = new CoincidenciaService(reporteService);
    private static final DevolucionService devolucionService = new DevolucionService(reporteService);

    // Punto de entrada de la aplicación: precarga usuarios de prueba y ejecuta el bucle principal del menú
    public static void main(String[] args) {
        cargarDatosDemo();

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Seleccione una opción: ");

            // Captura centralizada de excepciones de validación y estado (IllegalArgumentException / IllegalStateException)
            try {
                switch (opcion) {
                    case 1 -> registrarUsuario();
                    case 2 -> iniciarSesion();
                    case 3 -> cerrarSesion();
                    case 4 -> registrarReporte(true);
                    case 5 -> registrarReporte(false);
                    case 6 -> consultarReportes();
                    case 7 -> buscarObjetos();
                    case 8 -> verCoincidencias();
                    case 9 -> registrarDevolucion();
                    case 10 -> actualizarEstadoReporte();
                    case 0 -> {
                        salir = true;
                        System.out.println("Gracias por usar FindIt UNSA.");
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        SCANNER.close();
    }

    // Precarga cuentas de los integrantes y reportes de prueba en ubicaciones de la UNSA
    private static void cargarDatosDemo() {
        try {
            Usuario sebastian = autenticacionService.registrarUsuario(
                    "Sebastián Ala",
                    "sala@unsa.edu.pe",
                    "admin123",
                    Rol.ADMINISTRADOR
            );

            Usuario omar = autenticacionService.registrarUsuario(
                    "Omar Torres",
                    "otorrese@unsa.edu.pe",
                    "1234",
                    Rol.USUARIO
            );

            Usuario gabriel = autenticacionService.registrarUsuario(
                    "Gabriel Turpo",
                    "gturpo@unsa.edu.pe",
                    "1234",
                    Rol.USUARIO
            );

            // --- PAR 1 (HACE COINCIDENCIA): Laptop Lenovo ---
            reporteService.registrarReportePerdido(omar, new Objeto(
                    "Laptop Lenovo",
                    "Laptop gris de 14 pulgadas con sticker de Java",
                    CategoriaObjeto.ELECTRONICA,
                    "Gris",
                    "Lenovo",
                    "Pabellón Alan Turing - Aula 204",
                    LocalDate.now().minusDays(1),
                    "Cargador incluido en funda negra"
            ));

            reporteService.registrarReporteEncontrado(gabriel, new Objeto(
                    "Laptop Lenovo",
                    "Laptop encontrada en carpeta del segundo piso",
                    CategoriaObjeto.ELECTRONICA,
                    "Gris",
                    "Lenovo",
                    "Pabellón Alan Turing",
                    LocalDate.now(),
                    "Tiene un sticker en la tapa"
            ));

            // --- PAR 2 (HACE COINCIDENCIA): Carnet Universitario ---
            reporteService.registrarReportePerdido(gabriel, new Objeto(
                    "Carnet Universitario",
                    "Carnet universitario UNSA 2026 de Ingeniería de Sistemas",
                    CategoriaObjeto.DOCUMENTOS,
                    "Blanco",
                    "UNSA",
                    "Comedor de Ingenierías",
                    LocalDate.now().minusDays(1),
                    "Portacarnet azul con cinta"
            ));

            reporteService.registrarReporteEncontrado(sebastian, new Objeto(
                    "Carnet Universitario",
                    "Carnet universitario UNSA encontrado cerca a las mesas",
                    CategoriaObjeto.DOCUMENTOS,
                    "Blanco",
                    "UNSA",
                    "Comedor de Ingenierías",
                    LocalDate.now(),
                    "Portacarnet azul"
            ));

            // --- REPORTES ADICIONALES (PARA PROBAR BÚSQUEDAS Y FILTROS) ---
            reporteService.registrarReportePerdido(omar, new Objeto(
                    "Mochila Porta",
                    "Mochila negra con cuadernos de Estructuras Discretas",
                    CategoriaObjeto.MOCHILAS,
                    "Negro",
                    "Porta",
                    "Biblioteca de Ingenierías",
                    LocalDate.now().minusDays(2),
                    "Llavero metálico en el cierre"
            ));

            reporteService.registrarReportePerdido(gabriel, new Objeto(
                    "Casaca Polar",
                    "Casaca térmica azul marino talla M",
                    CategoriaObjeto.ROPA,
                    "Azul",
                    "North Face",
                    "Canchas de Ingenierías",
                    LocalDate.now().minusDays(3),
                    "Bolsillo izquierdo con cierre roto"
            ));

            reporteService.registrarReporteEncontrado(omar, new Objeto(
                    "Libro Cálculo Integral",
                    "Libro de Cálculo de una variable con apuntes",
                    CategoriaObjeto.LIBROS,
                    "Rojo",
                    "Stewart",
                    "Escuela Profesional de Ingeniería de Sistemas",
                    LocalDate.now().minusDays(1),
                    "Nombre escrito en la primera hoja"
            ));

            reporteService.registrarReporteEncontrado(sebastian, new Objeto(
                    "Audífonos Inalámbricos",
                    "Estuche de audífonos bluetooth color negro",
                    CategoriaObjeto.ACCESORIOS,
                    "Negro",
                    "Xiaomi",
                    "Puerta de Av. Independencia - Ingenierías",
                    LocalDate.now(),
                    "Tiene una pequeña raya en la tapa"
            ));

            // Genera automáticamente las coincidencias iniciales detectadas
            coincidenciaService.generarCoincidencias();

        } catch (Exception ignored) {}
    }

    // Imprime la cabecera con el estado de la sesión actual y las opciones disponibles
    private static void mostrarMenu() {
        Usuario actual = autenticacionService.getUsuarioActual();

        System.out.println("\n====================================");
        System.out.println("             FINDIT UNSA");
        System.out.println("     Sistema de objetos perdidos");
        System.out.println("====================================");
        System.out.println("Usuario actual: " + (actual == null
                ? "No autenticado"
                : actual.getNombre() + " (" + actual.getRol() + ")"));
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Cerrar sesión");
        System.out.println("4. Registrar objeto perdido");
        System.out.println("5. Registrar objeto encontrado");
        System.out.println("6. Consultar reportes");
        System.out.println("7. Buscar objetos");
        System.out.println("8. Ver coincidencias");
        System.out.println("9. Registrar devolución");
        System.out.println("10. Actualizar estado de reporte");
        System.out.println("0. Salir");
    }

    // Solicita datos de cuenta y registra un usuario (solo un admin logueado puede crear otro admin)
    private static void registrarUsuario() {
        System.out.println("\n--- REGISTRO DE USUARIO ---");
        String nombre = leerTextoObligatorio("Nombre completo: ");
        String correo = leerTextoObligatorio("Correo institucional: ");
        String contrasena = leerTextoObligatorio("Contraseña (mínimo 4 caracteres): ");

        Rol rol = Rol.USUARIO;
        if (autenticacionService.esAdministrador()) {
            System.out.println("Rol a registrar:");
            System.out.println("1. Usuario");
            System.out.println("2. Administrador");
            int opcionRol = leerEntero("Seleccione rol: ");
            if (opcionRol == 2) {
                rol = Rol.ADMINISTRADOR;
            }
        }

        Usuario usuario = autenticacionService.registrarUsuario(nombre, correo, contrasena, rol);
        System.out.println("Usuario registrado correctamente: " + usuario.getNombre()
                + " (" + usuario.getRol() + ")");
    }

    // Pide credenciales y delega la autenticación al servicio
    private static void iniciarSesion() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        String correo = leerTextoObligatorio("Correo: ");
        String contrasena = leerTextoObligatorio("Contraseña: ");

        Usuario usuario = autenticacionService.iniciarSesion(correo, contrasena);
        System.out.println("Bienvenido(a), " + usuario.getNombre() + ". Rol: " + usuario.getRol());
    }

    // Limpia la sesión activa si existe un usuario autenticado
    private static void cerrarSesion() {
        if (!autenticacionService.estaAutenticado()) {
            System.out.println("No hay una sesión activa.");
            return;
        }

        autenticacionService.cerrarSesion();
        System.out.println("Sesión cerrada correctamente.");
    }

    // Flujo unificado para crear reportes: si perdido es true crea ReportePerdido, si es false crea ReporteEncontrado
    private static void registrarReporte(boolean perdido) {
        Usuario usuario = requerirSesion();

        System.out.println("\n--- REGISTRAR OBJETO "
                + (perdido ? "PERDIDO" : "ENCONTRADO") + " ---");

        Objeto objeto = leerObjeto();

        if (perdido) {
            ReportePerdido reporte = reporteService.registrarReportePerdido(usuario, objeto);
            System.out.println("Reporte perdido registrado con ID #" + reporte.getId());
        } else {
            ReporteEncontrado reporte = reporteService.registrarReporteEncontrado(usuario, objeto);
            System.out.println("Reporte encontrado registrado con ID #" + reporte.getId());
        }
    }

    // Construye un objeto físico pidiendo sus 8 atributos por consola
    private static Objeto leerObjeto() {
        String nombre = leerTextoObligatorio("Nombre del objeto: ");
        String descripcion = leerTextoObligatorio("Descripción: ");
        CategoriaObjeto categoria = seleccionarCategoria();
        String color = leerTextoOpcional("Color (opcional): ");
        String marca = leerTextoOpcional("Marca (opcional): ");
        String ubicacion = leerTextoObligatorio("Ubicación: ");
        LocalDate fecha = leerFecha("Fecha del evento (yyyy-MM-dd): ");
        String caracteristicas = leerTextoOpcional("Características adicionales (opcional): ");

        return new Objeto(nombre, descripcion, categoria, color, marca, ubicacion, fecha, caracteristicas);
    }

    // Muestra dinámicamente todos los valores del enum CategoriaObjeto y valida la selección numérica
    private static CategoriaObjeto seleccionarCategoria() {
        CategoriaObjeto[] categorias = CategoriaObjeto.values();

        System.out.println("Categorías disponibles:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }

        int opcion = leerEntero("Seleccione categoría: ");
        if (opcion < 1 || opcion > categorias.length) {
            throw new IllegalArgumentException("Categoría inválida.");
        }

        return categorias[opcion - 1];
    }

    // Permite omitir el filtro de categoría devolviendo null si el usuario responde distinto de 's'
    private static CategoriaObjeto seleccionarCategoriaOpcional() {
        System.out.print("¿Desea filtrar por categoría? (s/n): ");
        String respuesta = SCANNER.nextLine().trim();

        if (!respuesta.equalsIgnoreCase("s")) {
            return null;
        }

        return seleccionarCategoria();
    }

    // Muestra todos los reportes almacenados en ReporteService
    private static void consultarReportes() {
        requerirSesion();
        System.out.println("\n--- REPORTES REGISTRADOS ---");
        imprimirReportes(reporteService.listarReportes());
    }

    // Solicita filtros opcionales y delega la búsqueda combinada a BusquedaService
    private static void buscarObjetos() {
        requerirSesion();
        System.out.println("\n--- BÚSQUEDA DE OBJETOS ---");

        String texto = leerTextoOpcional("Texto a buscar (nombre, descripción, características, etc.): ");
        CategoriaObjeto categoria = seleccionarCategoriaOpcional();
        String ubicacion = leerTextoOpcional("Ubicación (opcional): ");

        List<Reporte> resultados = busquedaService.buscarAvanzado(texto, categoria, ubicacion);
        System.out.println("Resultados encontrados: " + resultados.size());
        imprimirReportes(resultados);
    }

    // Ejecuta el algoritmo de emparejamiento y muestra en pantalla los pares sugeridos con su puntaje
    private static void verCoincidencias() {
        requerirSesion();
        System.out.println("\n--- POSIBLES COINCIDENCIAS ---");

        List<Coincidencia> coincidencias = coincidenciaService.generarCoincidencias();

        if (coincidencias.isEmpty()) {
            System.out.println("No se encontraron posibles coincidencias.");
            return;
        }

        for (Coincidencia coincidencia : coincidencias) {
            System.out.println("Coincidencia #" + coincidencia.getId()
                    + " | Puntaje: " + coincidencia.getPuntaje());
            System.out.println("  Perdido: " + describirReporte(coincidencia.getReportePerdido()));
            System.out.println("  Encontrado: " + describirReporte(coincidencia.getReporteEncontrado()));
            System.out.println("  Características: "
                    + String.join(", ", coincidencia.getCaracteristicasCoincidentes()));
        }
    }

    // Lista reportes perdidos y encontrados, pide sus IDs y cierra el ciclo mediante DevolucionService
    private static void registrarDevolucion() {
        Usuario usuario = requerirSesion();
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");

        List<ReportePerdido> perdidos = reporteService.listarPerdidos();
        List<ReporteEncontrado> encontrados = reporteService.listarEncontrados();

        if (perdidos.isEmpty() || encontrados.isEmpty()) {
            System.out.println("Debe existir al menos un reporte perdido y uno encontrado.");
            return;
        }

        System.out.println("Reportes perdidos:");
        imprimirReportes(perdidos.stream().map(r -> (Reporte) r).toList());

        System.out.println("Reportes encontrados:");
        imprimirReportes(encontrados.stream().map(r -> (Reporte) r).toList());

        int idPerdido = leerEntero("ID del reporte perdido: ");
        int idEncontrado = leerEntero("ID del reporte encontrado: ");
        String observaciones = leerTextoOpcional("Observaciones (opcional): ");

        Devolucion devolucion = devolucionService.registrarDevolucion(
                idPerdido, idEncontrado, usuario, observaciones
        );

        System.out.println("Devolución registrada con ID #" + devolucion.getId()
                + ". Reportes actualizados a DEVUELTO.");
    }

    // Busca un reporte por ID y permite cambiar manualmente su EstadoReporte
    private static void actualizarEstadoReporte() {
        requerirSesion();
        System.out.println("\n--- ACTUALIZAR ESTADO DE REPORTE ---");

        int id = leerEntero("ID del reporte: ");
        Reporte reporte = reporteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un reporte con ID " + id));

        System.out.println("Reporte actual: " + describirReporte(reporte));

        EstadoReporte[] estados = EstadoReporte.values();
        System.out.println("Estados disponibles:");
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }

        int opcion = leerEntero("Seleccione nuevo estado: ");
        if (opcion < 1 || opcion > estados.length) {
            throw new IllegalArgumentException("Estado inválido.");
        }

        reporteService.actualizarEstado(id, estados[opcion - 1]);
        System.out.println("Estado actualizado correctamente.");
    }

    // --- MÉTODOS AUXILIARES DE PRESENTACIÓN Y LECTURA SEGURA ---

    // Recorre e imprime una lista de reportes o muestra un aviso si está vacía
    private static void imprimirReportes(List<Reporte> reportes) {
        if (reportes.isEmpty()) {
            System.out.println("No hay reportes para mostrar.");
            return;
        }

        for (Reporte reporte : reportes) {
            System.out.println(describirReporte(reporte));
        }
    }

    // Formatea en una sola línea todos los datos clave de un reporte para consola
    private static String describirReporte(Reporte reporte) {
        Objeto objeto = reporte.getObjeto();

        return String.format(
                "#%d | %s | %s | Categoría: %s | Ubicación: %s | Fecha: %s | Estado: %s | Reportado por: %s",
                reporte.getId(),
                reporte.getTipoReporte(),
                objeto.getNombre(),
                objeto.getCategoria(),
                objeto.getUbicacion(),
                objeto.getFechaEvento() != null
                        ? objeto.getFechaEvento().format(FORMATO_FECHA)
                        : "N/A",
                reporte.getEstado(),
                reporte.getUsuario() != null ? reporte.getUsuario().getNombre() : "N/A"
        );
    }

    // Guardia de seguridad: verifica que exista sesión activa o lanza excepción para cortar la operación
    private static Usuario requerirSesion() {
        if (!autenticacionService.estaAutenticado()) {
            throw new IllegalStateException("Debe iniciar sesión para realizar esta acción.");
        }
        return autenticacionService.getUsuarioActual();
    }

    // Bucle de lectura que impide avanzar hasta que el usuario ingrese un texto no vacío
    private static String leerTextoObligatorio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este campo es obligatorio.");
        }
    }

    // Lee una línea de texto permitiendo que quede vacía
    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return SCANNER.nextLine().trim();
    }

    // Bucle de lectura segura de enteros (evita el bug clásico de salto de línea de Scanner.nextInt())
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = SCANNER.nextLine().trim();

            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    // Bucle de lectura que valida el formato ISO de fecha (yyyy-MM-dd)
    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = SCANNER.nextLine().trim();

            try {
                return LocalDate.parse(linea);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use yyyy-MM-dd.");
            }
        }
    }
}
