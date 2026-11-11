package finditunsa.repositorios;

import encuentraU.modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//RESPONSABLE: INTEGRANTE 1
//MODULO: Usuarios y autenticación
//DESCRIPCION: Repositorio temporal en memoria para usuarios.

public class UsuarioRepository {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final HashMap<String, Usuario> usuariosPorCorreo = new HashMap<>();
    private int secuenciaId = 1;

    // METODO PARA GUARDAR USUARIO
    public Usuario guardar(Usuario usuario) {
        // Validar que el usuario no sea nulo
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        // Normalizar correo (minúsculas y sin espacios)
        String correoNormalizado = usuario.getCorreo() == null
                ? ""
                : usuario.getCorreo().trim().toLowerCase();

        // Verificar que no esté vacío
        if (correoNormalizado.isEmpty()) {
            throw new IllegalArgumentException("El correo del usuario es obligatorio.");
        }

        // Verificar que no exista correo duplicado
        if (usuariosPorCorreo.containsKey(correoNormalizado)) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo: " + correoNormalizado);
        }

        // Asignar ID autoincremental
        if (usuario.getId() == 0) {
            usuario.setId(secuenciaId++);
        } else {
            secuenciaId = Math.max(secuenciaId, usuario.getId() + 1);
        }

        // Guardar en la lista y en el HashMap
        usuario.setCorreo(correoNormalizado);
        usuarios.add(usuario);
        usuariosPorCorreo.put(correoNormalizado, usuario);
        return usuario;
    }

    // METODO PARA BUSCAR POR CORREO
    public Optional<Usuario> buscarPorCorreo(String correo) {
        // Validar si el correo es nulo
        if (correo == null) {
            return Optional.empty();
        }
        // Buscar y retornar usando Optional
        return Optional.ofNullable(usuariosPorCorreo.get(correo.trim().toLowerCase()));
    }

    // METODO PARA BUSCAR POR ID
    public Optional<Usuario> buscarPorId(int id) {
        // Recorrer la lista buscando coincidencia de ID
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return Optional.of(usuario);
            }
        }
        // Retornar vacío si no se encuentra
        return Optional.empty();
    }

    // METODO PARA VERIFICAR SI EXISTE CORREO
    public boolean existeCorreo(String correo) {
        // Retornar falso si es nulo, o true/false si la clave existe
        if (correo == null) {
            return false;
        }
        return usuariosPorCorreo.containsKey(correo.trim().toLowerCase());
    }

    // METODO PARA LISTAR USUARIOS
    public List<Usuario> listar() {
        // Devolver una copia defensiva de la lista
        return new ArrayList<>(usuarios);
    }
}