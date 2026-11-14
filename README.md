# FINDIT-UNSA — SPRINT 1

## 1. Descripción del proyecto

**FindIt-UNSA** es una aplicación desarrollada para gestionar objetos perdidos y encontrados dentro de una comunidad universitaria.

Su propósito es facilitar el registro, búsqueda y recuperación de objetos, permitiendo que los usuarios reporten artículos que han perdido o encontrado. Además, el sistema permite identificar posibles coincidencias y gestionar las devoluciones.

El proyecto se desarrolla de manera incremental mediante tres sprints, aplicando los principios de programación orientada a objetos y metodologías ágiles.

## 2. Objetivos del Sprint 1

Durante este primer sprint se desarrollará la lógica principal del sistema mediante una aplicación de consola.

Los objetivos son:

- Implementar el registro de usuarios y la autenticación.
- Permitir el registro de objetos perdidos y encontrados.
- Gestionar los reportes realizados por los usuarios.
- Implementar la búsqueda y consulta de objetos.
- Identificar posibles coincidencias entre reportes.
- Gestionar las devoluciones de objetos.
- Aplicar los principios de programación orientada a objetos.
- Organizar el código para facilitar su integración en los siguientes sprints.

## 3. Tecnologías y herramientas utilizadas

| Tecnología / Herramienta | Uso |
|---|---|
| Java 21 | Lenguaje de programación utilizado para desarrollar el sistema. |
| Apache NetBeans | Entorno de desarrollo compatible. |
| IntelliJ IDEA | Entorno de desarrollo compatible. |
| Visual Studio Code | Entorno de desarrollo compatible mediante las extensiones de Java. |
| Git y GitHub | Control de versiones y colaboración. |
| ArrayList y HashMap | Gestión temporal de información en memoria. |

**Importante:** El proyecto no depende de un entorno de desarrollo específico. Puede abrirse y ejecutarse desde cualquier IDE compatible con Java 21 o directamente desde la terminal, respetando la estructura de paquetes y las instrucciones de ejecución.

## 4. Requisitos previos

Para ejecutar FindIt-UNSA se necesita:

- Tener instalado **JDK 21**.
- Disponer de una computadora con Windows, Linux o macOS.
- Descargar el código fuente desde el repositorio de GitHub.

No es obligatorio instalar Apache NetBeans, IntelliJ IDEA o Visual Studio Code. Estos programas son alternativas para facilitar la visualización y ejecución del código.

Para comprobar que Java está instalado, ejecutar en una terminal:

```bash
java -version
javac -version
```

Ambos comandos deben reconocer la versión 21 del JDK.

## 5. Estructura del proyecto

El proyecto debe organizarse de la siguiente manera:

```text
FindIt-UNSA/
│
├── README.md
│
├── docs/
│   ├── arquitectura.md
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

### 5.1. Descripción de las carpetas

| Carpeta | Contenido |
|---|---|
| `docs/` | Documentación general y distribución del trabajo. |
| `modelos/` | Clases que representan las entidades del sistema. |
| `servicios/` | Clases que contienen la lógica y operaciones del sistema. |
| `repositorios/` | Clases encargadas de gestionar los datos en memoria. |
| `utilidades/` | Funciones auxiliares y validaciones. |
| `Main.java` | Punto de entrada y menú principal de consola. |

## 6. Clases y distribución de responsabilidades

El desarrollo se divide entre tres integrantes para facilitar el trabajo y la integración de los módulos.

### 6.1. Integrante 1: Usuarios y autenticación

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Usuario` | Clase | Almacenar los datos del usuario. |
| `AutenticacionService` | Servicio | Registrar usuarios e iniciar/cerrar sesión. |
| `UsuarioRepository` | Repositorio | Gestionar usuarios temporalmente en memoria. |
| `Rol` | Enum | Diferenciar usuario y administrador. |

### 6.2. Integrante 2: Registro y gestión de objetos

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Objeto` | Clase | Representar las características de un objeto. |
| `Reporte` | Clase abstracta | Contener los datos comunes de los reportes. |
| `ReportePerdido` | Clase | Representar un objeto que alguien perdió. |
| `ReporteEncontrado` | Clase | Representar un objeto que alguien encontró. |
| `ReporteService` | Servicio | Registrar, consultar y actualizar reportes. |
| `EstadoReporte` | Enum | Definir los estados de un reporte. |
| `CategoriaObjeto` | Enum | Clasificar los objetos. |

### 6.3. Integrante 3: Búsqueda, coincidencias y devoluciones

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `BusquedaService` | Servicio | Buscar y filtrar objetos. |
| `Coincidencia` | Clase | Representar una posible coincidencia. |
| `CoincidenciaService` | Servicio | Comparar reportes perdidos y encontrados. |
| `Devolucion` | Clase | Registrar la devolución de un objeto. |
| `DevolucionService` | Servicio | Gestionar las devoluciones. |

### 6.4. Clases generales de integración

Estas clases se crean para conectar los módulos:

| Clase | Responsabilidad |
|---|---|
| `Main` | Ejecutar el programa y mostrar el menú principal. |
| `Validador` | Validar datos ingresados por consola. |

### 6.5. Resumen general

**Total: 18 archivos entre clases y enumeraciones.**

| Responsable | Cantidad de archivos |
|---|---:|
| Integrante 1 | 4 |
| Integrante 2 | 7 |
| Integrante 3 | 5 |
| Integración general | 2 |
| **Total** | **18** |

## 7. Formas de obtener y ejecutar el proyecto

### Opción 1. Ejecutar desde la terminal (Windows - PowerShell)

Esta alternativa permite compilar y ejecutar el proyecto sin instalar un entorno de desarrollo.

1. Descargar el repositorio desde GitHub o clonarlo:

```bash
git clone URL_DEL_REPOSITORIO
```

2. Abrir PowerShell y ubicarse en la carpeta principal `FindIt-UNSA`.

3. Compilar los archivos Java:

**Windows PowerShell:**
```powershell
javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
```

**Linux/macOS:**

```bash
javac -d out $(find src/main/java -name "*.java")
```

4. Ejecutar el programa:

```powershell
java -cp out finditunsa.Main
```

Si la compilación termina correctamente, aparecerá el menú principal de FindIt-UNSA en la consola.

### Opción 2. Descargar desde GitHub y ejecutar desde un IDE

Esta opción permite obtener el proyecto sin utilizar comandos de Git.

1. Ingresar al repositorio de FindIt-UNSA en GitHub.
2. Presionar el botón **Code**.
3. Seleccionar **Download ZIP**.
4. Extraer el archivo descargado en una carpeta.
5. Abrir el proyecto desde un IDE compatible con Java 21, como Apache NetBeans, IntelliJ IDEA o Visual Studio Code.
6. Configurar JDK 21 y verificar que `src/main/java` esté reconocida como carpeta de fuentes.
7. Ubicar la clase `Main.java`, dentro del paquete `finditunsa`.
8. Ejecutar la clase principal.

**Requisito:** ambas opciones necesitan tener instalado JDK 21.

## 8. Funcionalidades del Sprint 1

| Módulo | Funcionalidades |
|---|---|
| Usuarios | Registro, inicio y cierre de sesión. |
| Objetos | Registro de objetos perdidos y encontrados. |
| Reportes | Consulta y actualización de reportes. |
| Búsqueda | Búsqueda y filtrado de objetos. |
| Coincidencias | Identificación de posibles coincidencias. |
| Devoluciones | Registro y gestión de devoluciones. |

**Nota:** Al cerrar el programa, los datos almacenados en memoria se pierden. La persistencia en una base de datos se incorporará durante el Sprint 3.

## 9. Documentación del proyecto

La carpeta `docs/` contiene los documentos necesarios para organizar y comprender el desarrollo:

| Archivo | Descripción |
|---|---|
| `arquitectura.md` | Lista de clases y estructura del Sprint 1. |
| `distribucion_integrantes.md` | Distribución general de responsabilidades. |
| `integrante_1.md` | Tareas correspondientes al integrante 1. |
| `integrante_2.md` | Tareas correspondientes al integrante 2. |
| `integrante_3.md` | Tareas correspondientes al integrante 3. |
| `arquitectura.md` | Organización y relación entre los módulos. |
| `contratos_integracion.md` | Acuerdos para conectar las clases y servicios. |
| `instrucciones_ejecucion.md` | Indicaciones adicionales para ejecutar el programa. |

## 10. Progresión de los siguientes sprints

El proyecto se desarrollará en tres etapas:

| Sprint | Desarrollo |
|---|---|
| **Sprint 1** | Lógica del sistema mediante consola y almacenamiento temporal en memoria. |
| **Sprint 2** | Interfaz gráfica con Java Swing e integración con la lógica existente. |
| **Sprint 3** | Base de datos MySQL y conexión mediante JDBC. |

La estructura presentada corresponde al Sprint 1. En los siguientes sprints se podrán agregar nuevas carpetas y clases sin necesidad de modificar toda la organización del proyecto.
