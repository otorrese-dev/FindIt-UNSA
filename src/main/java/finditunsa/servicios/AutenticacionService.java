package finditunsa.servicios;

import finditunsa.modelos.Rol;
import finditunsa.modelos.Usuario;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.utilidades.Validador;

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

    // METODO PARA ACTUALIZAR DATOS DEL PERFIL DEL USUARIO EN SESION
    public void actualizarPerfil(String nuevoNombre, String nuevoCorreo, String nuevaContrasena) {
        // Verificar que exista un usuario autenticado
        if (!estaAutenticado()) {
            throw new IllegalStateException("No hay sesión activa.");
        }

        Usuario u = getUsuarioActual();
        // Validar formato de nombre y correo
        Validador.validarTextoNoVacio(nuevoNombre, "nombre");
        Validador.validarCorreo(nuevoCorreo);

        String correoAnterior = u.getCorreo();
        String correoLimpio = nuevoCorreo.trim().toLowerCase();

        // Evitar duplicados si el usuario cambió su correo por uno ya existente
        if (!correoAnterior.equalsIgnoreCase(correoLimpio) && usuarioRepository.existeCorreo(correoLimpio)) {
            throw new IllegalArgumentException("Ese correo ya está registrado por otro usuario.");
        }

        // Validar nueva contraseña solo si el campo no llegó vacío
        boolean cambiarClave = nuevaContrasena != null && !nuevaContrasena.isEmpty();
        if (cambiarClave) {
            Validador.validarContrasena(nuevaContrasena);
        }

        // Actualizar atributos en el objeto y sincronizar clave en el repositorio
        u.setNombre(nuevoNombre.trim());
        u.setCorreo(correoLimpio);
        usuarioRepository.actualizarCorreo(correoAnterior, u);

        if (cambiarClave) {
            u.setContrasena(nuevaContrasena);
        }
    }
}
