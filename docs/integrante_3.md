# Integrante 3 - Búsqueda, coincidencias y devoluciones

## Objetivo
Relacionar reportes perdidos y encontrados, buscar objetos y registrar devoluciones.

## Clases asignadas
- `BusquedaService`
- `Coincidencia`
- `CoincidenciaService`
- `Devolucion`
- `DevolucionService`

## Funciones
- Buscar por texto, categoría y ubicación.
- Calcular coincidencias por características.
- Mostrar puntaje y características coincidentes.
- Registrar devoluciones.
- Actualizar estados a `DEVUELTO`.

## Integración
Los servicios reciben `ReporteService` por constructor. `Main` conecta las opciones del menú con estos servicios.
