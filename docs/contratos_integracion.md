# Contratos de integración

## AutenticacionService
- `Usuario registrarUsuario(String nombre, String correo, String contrasena, Rol rol)`
- `Usuario iniciarSesion(String correo, String contrasena)`
- `void cerrarSesion()`
- `Usuario getUsuarioActual()`
- `boolean estaAutenticado()`
- `boolean esAdministrador()`

## ReporteService
- `ReportePerdido registrarReportePerdido(Usuario usuario, Objeto objeto)`
- `ReporteEncontrado registrarReporteEncontrado(Usuario usuario, Objeto objeto)`
- `List<Reporte> listarReportes()`
- `List<ReportePerdido> listarPerdidos()`
- `List<ReporteEncontrado> listarEncontrados()`
- `Optional<Reporte> buscarPorId(int id)`
- `Reporte actualizarEstado(int id, EstadoReporte nuevoEstado)`

## BusquedaService
- `List<Reporte> buscarAvanzado(String texto, CategoriaObjeto categoria, String ubicacion)`
- `List<Reporte> buscar(String texto)`
- `List<Reporte> buscarPorCategoria(CategoriaObjeto categoria)`
- `List<Reporte> buscarPorUbicacion(String ubicacion)`

## CoincidenciaService
- `List<Coincidencia> generarCoincidencias()`
- `List<Coincidencia> listarCoincidencias()`

## DevolucionService
- `Devolucion registrarDevolucion(int idPerdido, int idEncontrado, Usuario responsable, String observaciones)`
- `List<Devolucion> listarDevoluciones()`

## Reglas
- Los servicios no imprimen por consola.
- `Main` solo captura datos, invoca servicios y muestra resultados.
- `ReporteService` es la única fuente de reportes en memoria.
- `UsuarioRepository` es la única fuente de usuarios en memoria.
