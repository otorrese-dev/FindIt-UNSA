# Integrante 1 - Usuarios y autenticación

## Objetivo
Gestionar usuarios, roles, registro, inicio y cierre de sesión.

## Clases asignadas
- `Usuario`
- `Rol`
- `UsuarioRepository`
- `AutenticacionService`
- `Validador`

## Funciones
- Registrar usuarios con validación.
- Evitar correos duplicados.
- Iniciar y cerrar sesión.
- Diferenciar usuario y administrador.
- Almacenar usuarios en memoria.

## Integración
`Main` usa `AutenticacionService` para registrar e iniciar sesión. Los reportes reciben un `Usuario` autenticado.
