# FindIt UNSA

Sistema universitario de gestión de objetos perdidos y encontrados para la comunidad de la Universidad Nacional de San Agustín de Arequipa (UNSA).

El proyecto permite registrar objetos perdidos y encontrados, consultar reportes, identificar posibles coincidencias y gestionar el proceso de devolución mediante roles de usuario y administrador.

---

## 1. Estado del proyecto

El desarrollo se organizó en tres sprints principales:

- **Sprint 1 – Lógica de negocio:** implementación de modelos, servicios y reglas de negocio utilizando programación orientada a objetos y almacenamiento en memoria.
- **Sprint 2 – Interfaz gráfica:** desarrollo de la interfaz con Java Swing, integración de los servicios y navegación según los roles del sistema.
- **Sprint 3 – Persistencia e integración:** incorporación del almacenamiento permanente mediante archivos de objetos, texto y binarios. También se realizaron ajustes en los servicios, integración de los módulos y actualización de la identidad visual del sistema.

Durante los diferentes sprints se realizaron refactorizaciones y correcciones para mejorar el funcionamiento del sistema, especialmente en el proceso de devoluciones, la actualización de información y la conservación de los datos.

## 2. Tecnologías utilizadas

- **Lenguaje:** Java.
- **Versión:** JDK 21.
- **Interfaz gráfica:** Java Swing y AWT.
- **Arquitectura:** organización por capas.
- **Persistencia:** serialización de objetos y archivos de texto y binarios.
- **Entorno de desarrollo:** IntelliJ IDEA, Eclipse, NetBeans o VS Code.

No se requieren dependencias externas para ejecutar la versión actual.

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
├── data/                         # Se genera automáticamente al ejecutar
│   ├── objetos/
│   │   └── datos_sistema.dat
│   ├── texto/
│   │   ├── historial.txt
│   │   └── reportes.txt
│   └── binarios/
│       └── estadisticas.bin
├── docs/
│   ├── arquitectura.md
│   ├── arquitectura_sprint3.md
│   ├── clases_sprint1.md
│   ├── contratos_integracion.md
│   ├── distribucion_integrantes.md
│   ├── distribucion_integrantes_sprint3.md
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
                ├── archivos/
                │   ├── GestorArchivosBinarios.java
                │   ├── GestorArchivosObjetos.java
                │   └── GestorArchivosTexto.java
                ├── modelos/
                │   ├── CategoriaObjeto.java
                │   ├── Coincidencia.java
                │   ├── DatosSistema.java
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

### Descripción de los nuevos archivos

- **`DatosSistema.java`:** centraliza las colecciones de usuarios, reportes, coincidencias y devoluciones que utiliza el sistema.
- **`GestorArchivosObjetos.java`:** administra la serialización y recuperación de los datos principales del sistema.
- **`GestorArchivosTexto.java`:** registra las actividades realizadas y permite exportar los reportes mediante archivos de texto.
- **`GestorArchivosBinarios.java`:** almacena estadísticas y contadores del sistema en archivos binarios.
- **`arquitectura_sprint3.md`:** documenta la arquitectura y los mecanismos de persistencia incorporados durante el Sprint 3.
- **`distribucion_integrantes_sprint3.md`:** describe las responsabilidades asignadas a cada integrante durante el Sprint 3.

## 5. Distribución de responsabilidades

El proyecto fue desarrollado por tres integrantes, distribuyendo las actividades según los módulos de lógica de negocio, interfaz gráfica y persistencia de información.

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

### Sprint 3 – Persistencia e integración

| Integrante | Módulo | Archivos y responsabilidades |
|---|---|---|
| **Integrante 1** | Persistencia de usuarios | `UsuarioRepository.java`: adaptación del repositorio para almacenar y recuperar usuarios mediante serialización de objetos. |
| **Integrante 2** | Persistencia de reportes | `GestorArchivosTexto.java`: implementación del registro de actividades en `historial.txt` y exportación de reportes en `reportes.txt`. También realizó las adaptaciones correspondientes en `ReporteService.java`. |
| **Integrante 3** | Integración y archivos binarios | `DatosSistema.java`, `GestorArchivosObjetos.java`, `GestorArchivosBinarios.java` y `Main.java`: centralización de los datos, almacenamiento de estadísticas binarias e integración de la carga inicial de información. También realizó las adaptaciones correspondientes en `DevolucionService.java` y `CoincidenciaService.java`. |

## 6. Refactorizaciones y correcciones

Durante la integración de los diferentes sprints se realizaron modificaciones para adaptar la lógica de negocio, mejorar la comunicación entre módulos e incorporar nuevas funcionalidades.

| Archivo | Responsable | Modificación |
|---|---|---|
| `EstadoDevolucion.java` | Integrante 3 | Creación de un enumerado para representar los cuatro estados de devolución. |
| `Devolucion.java` | Integrante 3 | Adaptación de la entidad para incluir el estado actual y las fechas de solicitud y entrega. |
| `DevolucionService.java` | Integrante 3 | Implementación de una máquina de estados mediante los métodos `solicitar()`, `verificar()`, `autorizar()` y `entregar()`. |
| `CoincidenciaService.java` | Integrante 3 | Corrección del contador `secuenciaId` para reiniciarlo al recalcular las coincidencias. |
| `AutenticacionService.java` | Integrante 1 | Incorporación del método `actualizarPerfil()` para permitir la edición de datos del usuario y guardar los cambios. |
| `InicioPanel.java` | Integrante 2 | Incorporación del método `refrescar()` para actualizar las estadísticas y los reportes al regresar a la vista de inicio. |
| `UsuarioRepository.java` | Integrante 1 | Adaptación del repositorio para trabajar con la estructura centralizada de datos y guardar los cambios de los usuarios. |
| `ReporteService.java` | Integrante 2 | Adaptación del servicio para registrar reportes y conservarlos mediante archivos persistentes. |
| `Main.java` | Integración | Incorporación de la carga inicial de información y la inicialización de los servicios con los datos recuperados. |

### Flujo de devolución

El proceso de devolución utiliza cuatro estados:

1. **SOLICITADA:** el usuario registra una solicitud de devolución.
2. **VERIFICADA:** el administrador comprueba la información del hallazgo.
3. **AUTORIZADA:** el administrador aprueba la entrega.
4. **ENTREGADA:** se concreta la devolución y se actualiza el estado de los reportes correspondientes a `DEVUELTO`.

## 7. Arquitectura del sistema

El proyecto utiliza una arquitectura en capas para separar las responsabilidades y facilitar el mantenimiento.

1. **Modelos:** representan las entidades del sistema, como `Usuario`, `Objeto`, `Reporte`, `Coincidencia` y `Devolucion`.
2. **Repositorios:** administran las colecciones de datos y permiten acceder a la información almacenada.
3. **Servicios:** contienen las reglas de negocio y las operaciones del sistema.
4. **Archivos:** gestionan la lectura y escritura de información persistente.
5. **Interfaz gráfica:** presenta las ventanas, formularios, tablas y paneles desarrollados con Java Swing.
6. **Main:** carga los datos, inicializa los servicios y ejecuta la aplicación.

### Navegación

La ventana principal `DashboardFrame` utiliza `BorderLayout` para organizar sus componentes y `CardLayout` para alternar entre los diferentes paneles.

La navegación se centraliza mediante `ActionListener`, `setActionCommand()` y `getActionCommand()`.

Las opciones disponibles dependen del rol del usuario autenticado.

### Persistencia de información

La información del sistema se centraliza mediante la clase `DatosSistema.java`, que contiene las colecciones principales de usuarios, reportes, coincidencias y devoluciones.

Los gestores de archivos se encargan de almacenar y recuperar la información sin necesidad de utilizar una base de datos externa.

## 8. Identidad visual

La interfaz gráfica utiliza un tema inspirado en los colores institucionales de la UNSA, basado en una paleta de tonos vino y borgoña.

Los colores y las fuentes se centralizan en la clase `Estilos.java`, ubicada en el paquete `finditunsa.interfaz.componentes`.

### Paleta de colores

| Constante | Código HEX | Uso |
|---|---|---|
| `AZUL_PRINCIPAL` | `#721C24` | Color vino principal para botones y elementos destacados. |
| `AZUL_OSCURO` | `#4A0E14` | Vino oscuro utilizado principalmente en el menú lateral. |
| `AZUL_CLARO` | `#F5E6E6` | Fondo claro para tablas y detalles. |
| `FONDO_CLARO` | `#FAFAFA` | Fondo general de la aplicación. |
| `BLANCO` | `#FFFFFF` | Superficies y elementos de contraste. |
| `TEXTO_OSCURO` | `#212529` | Texto principal. |
| `GRIS` | `#6C757D` | Elementos secundarios y textos complementarios. |
| `VERDE` | `#28A745` | Indicadores de éxito. |
| `ROJO` | `#DC3545` | Indicadores de error. |
| `NARANJA` | `#FD7E14` | Advertencias y estados de atención. |
| `MORADO` | `#6F42C1` | Elementos complementarios. |

### Tipografía

- **Georgia:** utilizada en los títulos principales, con tamaños de 26 y 20 puntos.
- **Segoe UI:** utilizada en subtítulos y textos generales, con tamaños de 15, 14 y 12 puntos.

Las constantes conservan algunos nombres históricos, como `AZUL_PRINCIPAL`, para evitar modificar las referencias existentes en los demás componentes. Sin embargo, sus valores corresponden actualmente a la nueva paleta vino.

## 9. Persistencia mediante archivos (Sprint 3)

A partir del Sprint 3, el sistema incorpora almacenamiento permanente para conservar la información después de cerrar la aplicación.

Los archivos se generan automáticamente dentro de la carpeta `data/`, ubicada en la raíz del proyecto.

### Archivos de objetos

**Ubicación:** `data/objetos/datos_sistema.dat`

Se utiliza serialización de objetos para almacenar y recuperar las principales colecciones del sistema:

- Usuarios.
- Reportes.
- Coincidencias.
- Devoluciones.

La clase `DatosSistema.java` centraliza estas colecciones y `GestorArchivosObjetos.java` administra su almacenamiento.

### Archivos de texto

**Ubicación:** `data/texto/`

Se utilizan para registrar información legible y facilitar la consulta de actividades.

- `historial.txt`: almacena el historial de actividades realizadas en el sistema.
- `reportes.txt`: contiene una exportación de los reportes registrados.

La administración de estos archivos corresponde a `GestorArchivosTexto.java`.

### Archivos binarios

**Ubicación:** `data/binarios/estadisticas.bin`

Se utiliza para almacenar contadores y estadísticas del sistema mediante datos binarios.

Su administración corresponde a `GestorArchivosBinarios.java`.

### Comprobación de persistencia

Para comprobar el funcionamiento:

1. Iniciar la aplicación.
2. Registrar un usuario, objeto o reporte.
3. Cerrar completamente el programa.
4. Volver a ejecutar la aplicación.
5. Comprobar que la información registrada continúa disponible.

## 10. Reglas de desarrollo

Para mantener la organización del proyecto se establecieron las siguientes reglas:

1. **No duplicar lógica de negocio:** los paneles de interfaz deben invocar los métodos de los servicios, sin implementar directamente las reglas del sistema.
2. **Navegación centralizada:** `DashboardFrame` administra el cambio entre paneles mediante `CardLayout`.
3. **Separación de responsabilidades:** cada integrante trabaja principalmente en el módulo que tiene asignado.
4. **Coordinación de cambios:** las modificaciones sobre clases desarrolladas por otro integrante deben realizarse previa coordinación y documentarse.
5. **Actualización de información:** las vistas deben reflejar los cambios realizados en los datos del sistema.
6. **Persistencia:** las modificaciones de información deben conservarse mediante los mecanismos de almacenamiento implementados.
7. **Organización de archivos:** los archivos generados durante la ejecución se almacenan dentro de la carpeta `data/`.

## 11. Requisitos de ejecución

- Tener instalado **Java JDK 21**.
- Disponer de una terminal o IDE compatible con Java.
- No se requieren dependencias externas para ejecutar la versión actual.
- Contar con permisos de lectura y escritura en la carpeta del proyecto para generar los archivos de persistencia.

## 12. Compilación y ejecución

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

### Windows (PowerShell)

Compilar:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
```

Ejecutar:

```powershell
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

**FindIt UNSA**  
Sistema de gestión de objetos perdidos y encontrados.  
Universidad Nacional de San Agustín de Arequipa.
