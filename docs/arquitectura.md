# Arquitectura de EncuentraU - Sprint 1

## Capas
1. **Modelos**: entidades del dominio.
2. **Repositorios**: almacenamiento temporal en memoria.
3. **Servicios**: reglas de negocio.
4. **Utilidades**: validaciones comunes.
5. **Main**: consola e integración.

## Relaciones principales
- `ReportePerdido` y `ReporteEncontrado` heredan de `Reporte`.
- `Reporte` contiene un `Usuario` y un `Objeto`.
- `Coincidencia` relaciona un reporte perdido con uno encontrado.
- `Devolucion` relaciona ambos reportes y registra al responsable.
- Los servicios usan `ReporteService` y `UsuarioRepository` por composición.

## Diagrama de clases

```mermaid
classDiagram
    class Usuario {
        -int id
        -String nombre
        -String correo
        -String contrasena
        -Rol rol
    }
    class Objeto {
        -String nombre
        -String descripcion
        -CategoriaObjeto categoria
        -String color
        -String marca
        -String ubicacion
        -LocalDate fechaEvento
        -String caracteristicas
    }
    class Reporte {
        <<abstract>>
        -int id
        -Usuario usuario
        -Objeto objeto
        -LocalDateTime fechaRegistro
        -EstadoReporte estado
        +getTipoReporte()
    }
    class ReportePerdido
    class ReporteEncontrado
    class Coincidencia {
        -int id
        -ReportePerdido reportePerdido
        -ReporteEncontrado reporteEncontrado
        -int puntaje
        -List~String~ caracteristicasCoincidentes
    }
    class Devolucion {
        -int id
        -ReportePerdido reportePerdido
        -ReporteEncontrado reporteEncontrado
        -Usuario registradoPor
        -LocalDateTime fechaDevolucion
    }
    class UsuarioRepository
    class AutenticacionService
    class ReporteService
    class BusquedaService
    class CoincidenciaService
    class DevolucionService

    Reporte <|-- ReportePerdido
    Reporte <|-- ReporteEncontrado
    Reporte --> Usuario
    Reporte --> Objeto
    Coincidencia --> ReportePerdido
    Coincidencia --> ReporteEncontrado
    Devolucion --> ReportePerdido
    Devolucion --> ReporteEncontrado
    AutenticacionService --> UsuarioRepository
    ReporteService --> Reporte
    BusquedaService --> ReporteService
    CoincidenciaService --> ReporteService
    DevolucionService --> ReporteService
    UsuarioRepository --> Usuario
```
