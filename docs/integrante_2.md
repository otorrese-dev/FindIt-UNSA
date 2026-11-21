# Integrante 2 - Registro y Visualización de Reportes

## Objetivo
Facilitar la publicación de objetos y su consulta estructurada mediante tablas.

## Responsabilidades y Cambios
- **Sprint 1:** Modelos `Objeto`, `Reporte` y servicio `ReporteService`.
- **Ajustes de Lógica:** Se agregó la lógica de refresco al panel de estadísticas (`InicioPanel.refrescar()`) para reflejar datos actualizados al cambiar de vista.
- **Sprint 2 (UI):**
  - Creación de `DialogoRegistro`, que emplea `JRadioButton`, `JComboBox` y `JTextArea` para capturar los datos del objeto.
  - Desarrollo de `MisReportesPanel`, con filtrado por usuario y actualización automática de la tabla.
  - Implementación de `AdminReportesPanel` y la tabla reutilizable `TablaReportes`.

## Pruebas
1. Registrar un nuevo objeto desde "Mis reportes".
2. Comprobar que la tabla refleja los datos de forma inmediata.
3. Verificar que el contador en "Inicio" incrementa correctamente tras el registro.
