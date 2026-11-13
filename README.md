# FindIt UNSA — CLASES Y ESTRUCTURA DEL SPRINT 1

## 1. Clases del integrante 1: Usuarios y autenticación

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Usuario` | Clase | Almacenar los datos del usuario. |
| `AutenticacionService` | Servicio | Registrar usuarios e iniciar/cerrar sesión. |
| `UsuarioRepository` | Repositorio | Gestionar usuarios temporalmente en memoria. |
| `Rol` | Enum | Diferenciar usuario y administrador. |

## 2. Clases del integrante 2: Registro y gestión de objetos

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Objeto` | Clase | Representar las características de un objeto. |
| `Reporte` | Clase abstracta | Contener los datos comunes de los reportes. |
| `ReportePerdido` | Clase | Representar un objeto que alguien perdió. |
| `ReporteEncontrado` | Clase | Representar un objeto que alguien encontró. |
| `ReporteService` | Servicio | Registrar, consultar y actualizar reportes. |
| `EstadoReporte` | Enum | Definir los estados de un reporte. |
| `CategoriaObjeto` | Enum | Clasificar los objetos. |

## 3. Clases del integrante 3: Búsqueda, coincidencias y devoluciones

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `BusquedaService` | Servicio | Buscar y filtrar objetos. |
| `Coincidencia` | Clase | Representar una posible coincidencia. |
| `CoincidenciaService` | Servicio | Comparar reportes perdidos y encontrados. |
| `Devolucion` | Clase | Registrar la devolución de un objeto. |
| `DevolucionService` | Servicio | Gestionar las devoluciones. |

## 4. Clases generales de integración

Estas clases se crean para conectar los módulos:

| Clase | Responsabilidad |
|---|---|
| `Main` | Ejecutar el programa y mostrar el menú principal. |
| `Validador` | Validar datos ingresados por consola. |

## 5. Estructura de carpetas del proyecto

El proyecto debe organizarse de la siguiente manera:

```text
FindIt-UNSA/
│
├── README.md
├── PLAN_TRABAJO.md
│
├── docs/
│   ├── clases_sprint1.md
│   ├── distribucion_integrantes.md
│   ├── integrante_1.md
│   ├── integrante_2.md
│   ├── integrante_3.md
│   ├── arquitectura.md
│   ├── contratos_integracion.md
│   └── instrucciones_ejecucion.md
│
└── src/
    └── main/
        └── java/
            └── finditunsa/
                │
                ├── Main.java
                │
                ├── modelos/
                │   ├── Usuario.java
                │   ├── Objeto.java
                │   ├── Reporte.java
                │   ├── ReportePerdido.java
                │   ├── ReporteEncontrado.java
                │   ├── Coincidencia.java
                │   ├── Devolucion.java
                │   ├── Rol.java
                │   ├── EstadoReporte.java
                │   └── CategoriaObjeto.java
                │
                ├── servicios/
                │   ├── AutenticacionService.java
                │   ├── ReporteService.java
                │   ├── BusquedaService.java
                │   ├── CoincidenciaService.java
                │   └── DevolucionService.java
                │
                ├── repositorios/
                │   └── UsuarioRepository.java
                │
                └── utilidades/
                    └── Validador.java
```

### Descripción de las carpetas

| Carpeta | Contenido |
|---|---|
| `docs/` | Documentación general y distribución del trabajo. |
| `modelos/` | Clases que representan las entidades del sistema. |
| `servicios/` | Clases que contienen la lógica y operaciones del sistema. |
| `repositorios/` | Clases encargadas de gestionar los datos en memoria. |
| `utilidades/` | Funciones auxiliares y validaciones. |
| `Main.java` | Punto de entrada y menú principal de consola. |

## 6. Resumen general

**Total: 18 archivos entre clases y enumeraciones.**

| Responsable | Cantidad de archivos |
|---|---:|
| Integrante 1 | 4 |
| Integrante 2 | 7 |
| Integrante 3 | 5 |
| Integración general | 2 |
| **Total** | **18** |

## 7. Consideraciones para el desarrollo

- Mantener estas clases como base del Sprint 1.
- No crear todavía ventanas, botones ni formularios gráficos.
- No implementar conexiones a MySQL ni JDBC en este sprint.
- Gestionar temporalmente los datos en memoria.
- Utilizar `Reporte` como clase abstracta.
- Hacer que `ReportePerdido` y `ReporteEncontrado` hereden de `Reporte`.
- Procurar que los servicios trabajen con los repositorios para facilitar la integración posterior.
- Mantener los nombres de clases, atributos y métodos acordados entre los tres integrantes.

## 8. Progresión de los siguientes sprints

| Sprint | Desarrollo |
|---|---|
| Sprint 1 | Lógica del sistema mediante consola y almacenamiento temporal en memoria. |
| Sprint 2 | Interfaz gráfica con Java Swing e integración con la lógica existente. |
| Sprint 3 | Base de datos MySQL y conexión mediante JDBC. |

**Importante:** La estructura presentada corresponde al Sprint 1. En los siguientes sprints se podrán agregar nuevas carpetas y clases sin necesidad de modificar toda la organización del proyecto.

# 9. Instrucciones de ejecución - FindIt UNSA

## Requisitos
- JDK 21 instalado.
- Terminal en la raíz del proyecto.

## Compilar

**Linux/macOS:**

```bash
javac -d out $(find src/main/java -name "*.java")
```

**Windows PowerShell:**

```powershell
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
```

## Ejecutar

```bash
java -cp out finditunsa.Main

```
## Notas
- Los datos se pierden al cerrar la aplicación.
- No se utiliza base de datos en este Sprint.
