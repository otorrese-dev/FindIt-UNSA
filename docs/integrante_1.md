# Integrante 1 - Autenticación y Gestión de Usuarios

## Objetivo
Gestionar el acceso seguro al sistema, el perfil de usuario y la administración de cuentas.

## Responsabilidades y Cambios
- **Sprint 1:** Creación del modelo `Usuario`, `Rol` y el servicio `AutenticacionService`.
- **Ajustes de Lógica:** Se modificó `AutenticacionService` para incluir el método `actualizarPerfil()`, permitiendo editar credenciales sin cerrar sesión.
- **Sprint 2 (UI):**
  - Desarrollo de `LoginFrame` y `RegistroFrame`.
  - Creación de `MiPerfilPanel` para la edición de datos personales.
  - Creación de `AdminUsuariosPanel`, exclusivo para visualizar la tabla de usuarios y registrar nuevos administradores.

## Pruebas
1. Ingresar como administrador.
2. Modificar los datos desde "Mi perfil" y validar la actualización.
3. Acceder a "Gestión Usuarios" y registrar un nuevo administrador.
