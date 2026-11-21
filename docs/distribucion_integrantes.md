# Distribución de responsabilidades

## Sprint 1 y Sprint 2 (Desarrollo Base)
| Integrante | Módulo Principal | Clases y Paneles Desarrollados |
|---|---|---|
| 1 | Autenticación y Usuarios | `Usuario`, `AutenticacionService`, `LoginFrame`, `RegistroFrame`, `MiPerfilPanel`, `AdminUsuariosPanel`. |
| 2 | Objetos y Reportes | `Objeto`, `Reporte`, `ReporteService`, `DialogoRegistro`, `MisReportesPanel`, `AdminReportesPanel`. |
| 3 | Búsqueda y Devoluciones | `BusquedaService`, `DevolucionService`, `BusquedaPanel`, `CoincidenciasPanel`, `DevolucionesPanel`, `AdminDevolucionesPanel`. |

## Refactorización y Corrección de Errores (Fase de Integración)
En la transición hacia el Sprint 2 y la implementación del flujo por roles, se requirió ajustar parte de la lógica del Sprint 1. Responsables de los cambios:

| Archivo Modificado / Creado | Responsable | Motivo del Cambio |
|---|---|---|
| **`Devolucion` y `EstadoDevolucion`** | Integrante 3 | Se reemplazó la devolución directa por una máquina de estados (Solicitada, Verificada, Autorizada, Entregada). |
| **`DevolucionService`** | Integrante 3 | Reestructuración de métodos (`solicitar`, `verificar`, `autorizar`, `entregar`) para respetar el flujo de trabajo. |
| **`AutenticacionService`** | Integrante 1 | Se agregó el método `actualizarPerfil` para soportar la vista de "Mi Perfil". |
| **`CoincidenciaService`** | Integrante 3 | Corrección de bug: se reinicia el contador `secuenciaId` para evitar IDs acumulativos erróneos. |
| **`InicioPanel`** | Integrante 2 | Se agregó el método `refrescar()` para actualizar dinámicamente las tarjetas estadísticas al navegar. |
| **`DashboardFrame`** | Integración General | Unificación de la navegación con `CardLayout` y despacho de eventos por `setActionCommand`. |
