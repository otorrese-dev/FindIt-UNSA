package finditunsa.archivos;

import finditunsa.modelos.DatosSistema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//MODULO: Persistencia de Usuarios y Archivos de Objetos
//DESCRIPCION: Guarda y recupera el estado principal del sistema mediante serialización.

public class GestorArchivosObjetos {

    // Ruta relativa donde se almacenará el archivo serializado
    private static final String RUTA = "data/objetos/datos_sistema.dat";

    /**
     * Serializa y guarda en disco el contenedor con todas las listas del sistema.
     */
    public static void guardar(DatosSistema datos) {
        File archivo = new File(RUTA);
        File carpeta = archivo.getParentFile();

        // Crea las carpetas padre (data/objetos/) si aún no existen
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        // Abre el flujo de salida y cierra automáticamente los recursos al terminar
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(datos);
        } catch (IOException e) {
            System.err.println("Error al guardar objetos: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo de objetos y reconstruye el estado guardado en memoria.
     * Si el archivo no existe o falla la lectura, devuelve una instancia vacía.
     */
    public static DatosSistema cargar() {
        File archivo = new File(RUTA);

        // Primera ejecución: si no hay archivo previo, inicia con listas vacías
        if (!archivo.exists()) {
            return new DatosSistema();
        }

        // Deserializa el contenido y verifica que corresponda a DatosSistema
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object contenido = ois.readObject();
            if (contenido instanceof DatosSistema) {
                return (DatosSistema) contenido;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar objetos: " + e.getMessage());
        }

        return new DatosSistema();
    }
}