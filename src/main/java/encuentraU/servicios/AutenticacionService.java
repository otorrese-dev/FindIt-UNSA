package encuentraU.servicios;

import encuentraU.modelos.Rol;
import encuentraU.modelos.Usuario;
import encuentraU.repositorios.UsuarioRepository;
import encuentraU.utilidades.Validador;

// RESPONSABLE: INTEGRANTE 1
// MODULO: Usuarios y autenticación
// DESCRIPCION: Gestiona registro, inicio y cierre de sesión.

public class AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private Usuario usuarioActual; // Guarda el usuario que inició sesión en este momento

    // Inyecta el repositorio para comunicarse con la memoria
    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // REGISTRAR UN NUEVO USUARIO
    public Usuario registrarUsuario(String nombre, String correo, String contrasena, Rol rol) {
        // Validar datos de entrada
        Validador.validarTextoNoVacio(nombre, "nombre");
        Validador.validarCorreo(correo);
        Validador.validarContrasena(contrasena);

        // Verificar que el rol no sea nulo
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio.");
        }

        // Verificar que el correo no esté registrado previa parte
        if (usuarioRepository.existeCorreo(correo)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
        }

        // Crear objeto temporal con ID 0 y mandarlo a guardar al repositorio
        Usuario usuario = new Usuario(0, nombre.trim(), correo.trim(), contrasena, rol);
        return usuarioRepository.guardar(usuario);
    }

    // INICIAR SESION
    public Usuario iniciarSesion(String correo, String contrasena) {
        // Validar datos ingresados
        Validador.validarCorreo(correo);
        Validador.validarTextoNoVacio(contrasena, "contraseña");

        // Buscar correo en repositorio; si no existe, lanza error
        Usuario usuario = usuarioRepository.buscarPorCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas."));

        // Comparar contraseña ingresada con la guardada
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

        // Establecer usuario actual y devolverlo
        usuarioActual = usuario;
        return usuario;
    }

    // CERRAR SESION
    public void cerrarSesion() {
        usuarioActual = null;
    }

    // OBTENER USUARIO LOGUEADO
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // VERIFICAR SI HAY SESION ACTIVA
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    // VERIFICAR SI EL USUARIO LOGUEADO ES ADMIN
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.esAdministrador();
    }
}