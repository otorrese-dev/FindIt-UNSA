package finditunsa.servicios;

import finditunsa.modelos.Rol;
import finditunsa.modelos.Usuario;
import finditunsa.repositorios.UsuarioRepository;
import finditunsa.utilidades.Validador;


// Servicio encargado del registro de usuarios, control de sesión y actualización de perfil.
public class AutenticacionService {
    private final UsuarioRepository usuarioRepository;
    private Usuario usuarioActual;

    public AutenticacionService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Valida los datos de entrada, verifica que el correo no esté duplicado y registra un nuevo usuario.
    public Usuario registrarUsuario(String nombre, String correo, String contrasena, Rol rol) {
        Validador.validarTextoNoVacio(nombre, "nombre");
        Validador.validarCorreo(correo);
        Validador.validarContrasena(contrasena);
        if (rol == null) throw new IllegalArgumentException("El rol es obligatorio.");
        if (usuarioRepository.existeCorreo(correo)) throw new IllegalArgumentException("Ya existe un usuario con ese correo.");

        Usuario usuario = new Usuario(0, nombre.trim(), correo.trim(), contrasena, rol);
        return usuarioRepository.guardar(usuario);
    }

    // Verifica las credenciales contra el repositorio y establece al usuario en la sesión activa.
    public Usuario iniciarSesion(String correo, String contrasena) {
        Validador.validarCorreo(correo);
        Validador.validarTextoNoVacio(contrasena, "contraseña");
        Usuario usuario = usuarioRepository.buscarPorCorreo(correo).orElse(null);
        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }
        usuarioActual = usuario;
        return usuario;
    }

    public void cerrarSesion() { usuarioActual = null; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public boolean estaAutenticado() { return usuarioActual != null; }
    public boolean esAdministrador() { return usuarioActual != null && usuarioActual.esAdministrador(); }

    // Actualiza los datos del usuario autenticado; solo modifica la contraseña si se ingresa una nueva.
    public void actualizarPerfil(String nuevoNombre, String nuevoCorreo, String nuevaContrasena) {
        if (!estaAutenticado()) throw new IllegalStateException("No hay sesión activa.");
        Usuario u = getUsuarioActual();
        Validador.validarTextoNoVacio(nuevoNombre, "nombre");
        Validador.validarCorreo(nuevoCorreo);
        if (!nuevaContrasena.isEmpty()) Validador.validarContrasena(nuevaContrasena);
        
        u.setNombre(nuevoNombre);
        u.setCorreo(nuevoCorreo);
        if (!nuevaContrasena.isEmpty()) u.setContrasena(nuevaContrasena);
    }
}
