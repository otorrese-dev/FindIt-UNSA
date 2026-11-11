package encuentraU.utilidades;

// RESPONSABLE: INTEGRANTE 1
// MODULO: Usuarios y autenticación
// DESCRIPCION: Validaciones comunes para texto, correo y contraseña.

// Declarada 'final' para que no tenga herencia
public final class Validador {

    // Constructor privado para no acceder a su constructor por defecto
    private Validador() {
    }

    // Comprueba que un texto no sea nulo ni esté lleno de espacios vacíos
    public static void validarTextoNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }

    // Valida que el correo no esté vacío y cumpla la estructura estándar
    public static void validarCorreo(String correo) {
        validarTextoNoVacio(correo, "correo");
        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido.");
        }
    }

    // Revisa que la contraseña no esté vacía y tenga mínimo 4 caracteres
    public static void validarContrasena(String contrasena) {
        validarTextoNoVacio(contrasena, "contraseña");
        if (contrasena.length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
        }
    }

    // Verifica que cualquier Objeto que le pases no sea nulo
    public static void validarNoNulo(Object valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo.");
        }
    }

    // Limpia espacios a los lados y convierte el texto a minusculas
    public static String normalizar(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
    }
}