# Integrante 2 - Registro y gestión de objetos

## Objetivo
Modelar y gestionar reportes de objetos perdidos y encontrados.

## Clases asignadas
- `Objeto`
- `Reporte`
- `ReportePerdido`
- `ReporteEncontrado`
- `ReporteService`
- `EstadoReporte`
- `CategoriaObjeto`

## Funciones
- Registrar objetos perdidos y encontrados.
- Consultar reportes.
- Actualizar estados.
- Aplicar herencia y abstracción.
- Mantener reportes en `ArrayList`.

## Integración
`Main` invoca `ReporteService`. `BusquedaService`, `CoincidenciaService` y `DevolucionService` reciben `ReporteService` por constructor.
