# FindIt UNSA

Sistema universitario de gestión de objetos perdidos y encontrados para la comunidad de la Universidad Nacional de San Agustín de Arequipa (UNSA).

El proyecto permite registrar objetos perdidos y encontrados, consultar reportes, identificar posibles coincidencias y gestionar el proceso de devolución mediante roles de usuario y administrador.

---

## 1. Estado del proyecto

El desarrollo se organizó en dos fases principales:

- **Sprint 1 – Lógica de negocio:** implementación de modelos, servicios y reglas de negocio utilizando programación orientada a objetos y almacenamiento en memoria.
- **Sprint 2 – Interfaz gráfica:** desarrollo de la interfaz con Java Swing, integración de los servicios y navegación según los roles del sistema.

Durante la integración también se realizaron refactorizaciones y correcciones para mejorar el funcionamiento del sistema, especialmente en el proceso de devoluciones y la actualización de información.

## 2. Tecnologías utilizadas

- **Lenguaje:** Java.
- **Versión:** JDK 21.
- **Interfaz gráfica:** Java Swing y AWT.
- **Arquitectura:** organización por capas.
- **Entorno de desarrollo:** IntelliJ IDEA, Eclipse, NetBeans o VS Code.

## 3. Funcionalidades principales

### Usuario

- Iniciar sesión y registrar una cuenta.
- Registrar objetos perdidos y encontrados.
- Consultar y administrar sus propios reportes.
- Buscar objetos mediante los filtros disponibles.
- Consultar posibles coincidencias.
- Solicitar la devolución de un objeto.
- Consultar y actualizar su perfil.

### Administrador

- Consultar reportes registrados en el sistema.
- Gestionar usuarios.
- Revisar coincidencias.
- Administrar las solicitudes de devolución.
- Controlar las etapas del proceso de devolución.

## 4. Estructura del proyecto

El repositorio está organizado de la siguiente manera:

```text
FindIt-UNSA/
├── README.md
├── docs/
│   ├── arquitectura.md
│   ├── clases_sprint1.md
│   ├── contratos_integracion.md
│   ├── distribucion_integrantes.md
│   ├── instrucciones_ejecucion.md
│   ├── integrante_1.md
│   ├── integrante_2.md
│   ├── integrante_3.md
│   └── sprint2.md
└── src/
    └── main/
        └── java/
            └── finditunsa/
                ├── Main.java
                ├── modelos/
                │   ├── CategoriaObjeto.java
                │   ├── Coincidencia.java
                │   ├── Devolucion.java
                │   ├── EstadoDevolucion.java
                │   ├── EstadoReporte.java
                │   ├── Objeto.java
                │   ├── Reporte.java
                │   ├── ReporteEncontrado.java
                │   ├── ReportePerdido.java
                │   ├── Rol.java
                │   └── Usuario.java
                ├── repositorios/
                │   └── UsuarioRepository.java
                ├── servicios/
                │   ├── AutenticacionService.java
                │   ├── BusquedaService.java
                │   ├── CoincidenciaService.java
                │   ├── DevolucionService.java
                │   └── ReporteService.java
                ├── utilidades/
                │   └── Validador.java
                └── interfaz/
                    ├── DashboardFrame.java
                    ├── LoginFrame.java
                    ├── RegistroFrame.java
                    ├── componentes/
                    │   ├── Estilos.java
                    │   ├── HeaderUsuario.java
                    │   ├── MenuLateral.java
                    │   ├── TablaReportes.java
                    │   └── TarjetaEstadistica.java
                    └── paneles/
                        ├── AdminDevolucionesPanel.java
                        ├── AdminReportesPanel.java
                        ├── AdminUsuariosPanel.java
                        ├── BusquedaPanel.java
                        ├── CoincidenciasPanel.java
                        ├── DevolucionesPanel.java
                        ├── DialogoRegistro.java
                        ├── InicioPanel.java
                        ├── MiPerfilPanel.java
                        └── MisReportesPanel.java
```

## 5. Distribución de responsabilidades

El proyecto fue desarrollado por tres integrantes, distribuyendo las actividades según los módulos de lógica de negocio y su correspondiente interfaz gráfica.

### Sprint 1 – Lógica de negocio

| Integrante | Módulo | Clases y servicios |
|---|---|---|
| **Integrante 1** | Usuarios y autenticación | `Usuario.java`, `Rol.java`, `UsuarioRepository.java`, `AutenticacionService.java`, `Validador.java`. |
| **Integrante 2** | Objetos y reportes | `Objeto.java`, `Reporte.java`, `ReportePerdido.java`, `ReporteEncontrado.java`, `CategoriaObjeto.java`, `EstadoReporte.java`, `ReporteService.java`. |
| **Integrante 3** | Búsqueda y devoluciones | `Coincidencia.java`, `Devolucion.java`, `BusquedaService.java`, `CoincidenciaService.java`, `DevolucionService.java`. |

### Sprint 2 – Interfaz gráfica

| Integrante | Módulo | Archivos y paneles |
|---|---|---|
| **Integrante 1** | Autenticación y usuarios | `LoginFrame.java`, `RegistroFrame.java`, `MiPerfilPanel.java`, `AdminUsuariosPanel.java`. |
| **Integrante 2** | Gestión de reportes | `DialogoRegistro.java`, `MisReportesPanel.java`, `AdminReportesPanel.java`, `TablaReportes.java`. |
| **Integrante 3** | Búsqueda y devoluciones | `BusquedaPanel.java`, `CoincidenciasPanel.java`, `DevolucionesPanel.java`, `AdminDevolucionesPanel.java`. |
| **Todos** | Integración y navegación | `Main.java`, `DashboardFrame.java`, `MenuLateral.java`, `HeaderUsuario.java`, `InicioPanel.java`, `Estilos.java`. |

## 6. Refactorizaciones y correcciones

Durante la integración del Sprint 1 con el Sprint 2 se realizaron modificaciones para adaptar la lógica de negocio a los nuevos requerimientos.

| Archivo | Responsable | Modificación |
|---|---|---|
| `EstadoDevolucion.java` | Integrante 3 | Creación de un enumerado para representar los cuatro estados de devolución. |
| `Devolucion.java` | Integrante 3 | Adaptación de la entidad para incluir el estado actual y las fechas de solicitud y entrega. |
| `DevolucionService.java` | Integrante 3 | Implementación de una máquina de estados mediante los métodos `solicitar()`, `verificar()`, `autorizar()` y `entregar()`. |
| `CoincidenciaService.java` | Integrante 3 | Corrección del contador `secuenciaId` para reiniciarlo al recalcular las coincidencias. |
| `AutenticacionService.java` | Integrante 1 | Incorporación del método `actualizarPerfil()` para permitir la edición de datos del usuario. |
| `InicioPanel.java` | Integrante 2 | Incorporación del método `refrescar()` para actualizar las estadísticas y los reportes al regresar a la vista de inicio. |

### Flujo de devolución

El proceso de devolución utiliza cuatro estados:

1. **SOLICITADA:** el usuario registra una solicitud de devolución.
2. **VERIFICADA:** el administrador comprueba la información del hallazgo.
3. **AUTORIZADA:** el administrador aprueba la entrega.
4. **ENTREGADA:** se concreta la devolución y se actualiza el estado de los reportes correspondientes a `DEVUELTO`.

## 7. Arquitectura del sistema

El proyecto utiliza una arquitectura en capas para separar las responsabilidades y facilitar el mantenimiento.

1. **Modelos:** representan las entidades del sistema, como `Usuario`, `Objeto`, `Reporte`, `Coincidencia` y `Devolucion`.
2. **Repositorios:** administran el almacenamiento temporal de los datos.
3. **Servicios:** contienen las reglas de negocio y las operaciones del sistema.
4. **Interfaz gráfica:** presenta las ventanas, formularios, tablas y paneles desarrollados con Java Swing.
5. **Main:** inicializa los servicios y ejecuta la aplicación.

### Navegación

La ventana principal `DashboardFrame` utiliza `BorderLayout` para organizar sus componentes y `CardLayout` para alternar entre los diferentes paneles.

La navegación se centraliza mediante `ActionListener`, `setActionCommand()` y `getActionCommand()`.

Las opciones disponibles dependen del rol del usuario autenticado.

## 8. Reglas de desarrollo

Para mantener la organización del proyecto se establecieron las siguientes reglas:

1. **No duplicar lógica de negocio:** los paneles de interfaz deben invocar los métodos de los servicios, sin implementar directamente las reglas del sistema.
2. **Navegación centralizada:** `DashboardFrame` administra el cambio entre paneles mediante `CardLayout`.
3. **Separación de responsabilidades:** cada integrante trabaja principalmente en el módulo que tiene asignado.
4. **Coordinación de cambios:** las modificaciones sobre clases desarrolladas por otro integrante deben realizarse previa coordinación y documentarse.
5. **Actualización de información:** las vistas deben reflejar los cambios realizados en los datos del sistema.

## 9. Requisitos de ejecución

- Tener instalado **Java JDK 21**.
- Disponer de una terminal o IDE compatible con Java.
- No se requieren dependencias externas para ejecutar la versión actual.

## 10. Compilación y ejecución

Abrir una terminal en la carpeta raíz del proyecto.

### Windows (CMD)

Compilar todas las clases:

```cmd
if not exist out mkdir out
(for /r src\main\java %f in (*.java) do @echo "%f") > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
```

Ejecutar la aplicación:

```cmd
java -cp out finditunsa.Main
```

### Linux / macOS

Compilar:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
```

Ejecutar:

```bash
java -cp out finditunsa.Main
```

## 11. Consideraciones

- **Almacenamiento:** los datos se mantienen en memoria RAM. Al cerrar la aplicación, los registros creados durante la ejecución se pierden.
- **Interfaz:** se utilizan administradores de diseño (*Layout Managers*) para organizar los componentes, evitando depender del posicionamiento absoluto.
- **Roles:** el menú y los paneles disponibles dependen del tipo de usuario autenticado.
- **Mantenimiento:** la separación por capas facilita realizar modificaciones sin afectar innecesariamente a los demás módulos.

---

**FindIt UNSA**  
Sistema de gestión de objetos perdidos y encontrados.  
Universidad Nacional de San Agustín de Arequipa.
