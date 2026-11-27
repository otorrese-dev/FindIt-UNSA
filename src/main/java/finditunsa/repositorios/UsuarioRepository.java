package finditunsa.repositorios;

import finditunsa.archivos.GestorArchivosObjetos;
import finditunsa.modelos.DatosSistema;
import finditunsa.modelos.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//MODULO: Persistencia de Usuarios y Archivos de Objetos
//DESCRIPCION: Repositorio de usuarios conectado al contenedor DatosSistema y al archivo .dat.

public class UsuarioRepository {
    private final List<Usuario> usuarios;
    private final DatosSistema datosSistema;

    // CONSTRUCTOR PARA INICIALIZAR EL REPOSITORIO CON LOS DATOS DEL SISTEMA
    public UsuarioRepository(DatosSistema datos) {
        // Validar que el contenedor de datos no sea nulo
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del sistema no pueden ser nulos.");
        }
        // Enlazar la referencia de DatosSistema y su lista de usuarios
        this.datosSistema = datos;
        this.usuarios = datos.usuarios;
    }

    // METODO PARA GUARDAR O ACTUALIZAR USUARIO
    public Usuario guardar(Usuario usuario) {
        // Validar que el usuario no sea nulo
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }

        // Normalizar correo (minúsculas y sin espacios)
        String correo = usuario.getCorreo() == null
                ? ""
                : usuario.getCorreo().trim().toLowerCase();

        // Verificar que el correo no esté vacío
        if (correo.isEmpty()) {
            throw new IllegalArgumentException("El correo del usuario es obligatorio.");
        }

        // Verificar que no exista otro usuario distinto con el mismo correo
        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(correo) && u.getId() != usuario.getId()) {
                throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
            }
        }

        // Asignar ID autoincremental si el usuario es nuevo
        if (usuario.getId() == 0) {
            int maxId = 0;
            // Buscar el ID más alto registrado actualmente
            for (Usuario u : usuarios) {
                if (u.getId() > maxId) {
                    maxId = u.getId();
                }
            }
            usuario.setId(maxId + 1);
        }

        // Guardar correo normalizado en el objeto usuario
        usuario.setCorreo(correo);

        // Verificar si el usuario ya existe en la lista para actualizarlo
        boolean existe = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                existe = true;
                break;
            }
        }

        // Si no existía previamente, agregarlo a la lista
        if (!existe) {
            usuarios.add(usuario);
        }

        // Guardar automáticamente los cambios en el archivo datos_sistema.dat
        GestorArchivosObjetos.guardar(datosSistema);
        return usuario;
    }

    // METODO PARA BUSCAR POR CORREO
    public Optional<Usuario> buscarPorCorreo(String correo) {
        // Validar si el correo es nulo
        if (correo == null) {
            return Optional.empty();
        }
        // Normalizar correo y recorrer la lista buscando coincidencia
        String normalizado = correo.trim().toLowerCase();
        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getCorreo().equalsIgnoreCase(normalizado)) {
                return Optional.of(u);
            }
        }
        // Retornar vacío si no se encuentra
        return Optional.empty();
    }

    // METODO PARA BUSCAR POR ID
    public Optional<Usuario> buscarPorId(int id) {
        // Recorrer la lista buscando coincidencia de ID
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return Optional.of(u);
            }
        }
        // Retornar vacío si no se encuentra
        return Optional.empty();
    }

    // METODO PARA VERIFICAR SI EXISTE CORREO
    public boolean existeCorreo(String correo) {
        // Reutilizar la búsqueda por correo para confirmar existencia
        return buscarPorCorreo(correo).isPresent();
    }

    // METODO PARA LISTAR USUARIOS
    public List<Usuario> listar() {
        // Devolver una copia defensiva de la lista
        return new ArrayList<>(usuarios);
    }
}