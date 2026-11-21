# Integrante 3 - Búsqueda, Coincidencias y Devoluciones

## Objetivo
Proveer herramientas de búsqueda avanzada y gestionar el ciclo de vida de las devoluciones.

## Responsabilidades y Cambios
- **Sprint 1:** Servicios de Búsqueda, Coincidencias y Devoluciones.
- **Ajustes Críticos de Lógica:**
  - **Refactorización de Devoluciones:** Se modificó la entidad `Devolucion` y su servicio para implementar una máquina de estados, evitando entregas sin verificación previa.
  - **Corrección de Coincidencias:** Se corrigió un error en `CoincidenciaService` que provocaba el incremento infinito de IDs. Ahora se reinicia el contador cada vez que se recalculan los resultados.
- **Sprint 2 (UI):**
  - `BusquedaPanel`: Incorpora un `JCheckBox` para filtrar objetos ya devueltos.
  - `DevolucionesPanel` y `AdminDevolucionesPanel`: Interfaz para que el usuario solicite y el administrador controle los 4 estados de la devolución.

## Pruebas
1. Ingresar como usuario, buscar objetos y usar el filtro de "Ocultar devueltos".
2. Solicitar una devolución.
3. Ingresar como admin y avanzar el estado desde "Solicitudes Devolución".
