# Distribución de responsabilidades

## Integrante 1 - Usuarios y autenticación
- Clases: `Usuario`, `AutenticacionService`, `UsuarioRepository`, `Rol`, `Validador`.
- Responsabilidades: registro, inicio/cierre de sesión, validación de datos, roles y almacenamiento en memoria.
- Archivos modificables: `modelos/Usuario.java`, `modelos/Rol.java`, `servicios/AutenticacionService.java`, `repositorios/UsuarioRepository.java`, `utilidades/Validador.java`.
- Dependencias: ninguna obligatoria para iniciar; expone métodos públicos a `Main`.
- Entregable: módulo de usuarios funcional y documentado.

## Integrante 2 - Registro y gestión de objetos
- Clases: `Objeto`, `Reporte`, `ReportePerdido`, `ReporteEncontrado`, `ReporteService`, `EstadoReporte`, `CategoriaObjeto`.
- Responsabilidades: modelos de reportes, herencia, registro, consulta y actualización de estados.
- Archivos modificables: `modelos/Objeto.java`, `modelos/Reporte*.java`, `modelos/EstadoReporte.java`, `modelos/CategoriaObjeto.java`, `servicios/ReporteService.java`.
- Dependencias: usa `Usuario` para asociar reportes.
- Entregable: módulo de reportes funcional y documentado.

## Integrante 3 - Búsqueda, coincidencias y devoluciones
- Clases: `BusquedaService`, `Coincidencia`, `CoincidenciaService`, `Devolucion`, `DevolucionService`.
- Responsabilidades: búsquedas por criterios, cálculo de coincidencias, devoluciones y actualización de estados.
- Archivos modificables: `servicios/BusquedaService.java`, `servicios/CoincidenciaService.java`, `servicios/DevolucionService.java`, `modelos/Coincidencia.java`, `modelos/Devolucion.java`.
- Dependencias: recibe `ReporteService` por constructor.
- Entregable: módulo de búsqueda, coincidencias y devoluciones funcional y documentado.

## Integración
- `Main.java` conecta los módulos mediante servicios.
- No contiene lógica de negocio duplicada; solo entrada/salida por consola.
