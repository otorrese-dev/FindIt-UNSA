package finditunsa.archivos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
- MODULO: Persistencia Binaria e Integracion
- DESCRIPCION: Guarda y lee estadísticas numéricas mediante flujos binarios.
 */
public class GestorArchivosBinarios {

    // Ruta relativa del archivo binario donde se almacenan los contadores
    private static final String RUTA = "data/binarios/estadisticas.bin";

    // Escribe los contadores principales del sistema en formato binario.
    public static void guardarEstadisticas(int totalUsuarios, int totalReportes, int totalDevoluciones) {
        File archivo = new File(RUTA);
        File carpeta = archivo.getParentFile();

        // Asegura que los directorios padre existan antes de crear el archivo
        if (carpeta != null) carpeta.mkdirs();

        // Escribe los enteros en orden estricto (el mismo orden debe respetarse al leer)
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo))) {
            dos.writeInt(totalUsuarios);
            dos.writeInt(totalReportes);
            dos.writeInt(totalDevoluciones);
        } catch (IOException e) {
            System.err.println("Error al guardar binario: " + e.getMessage());
        }
    }

    // Lee los valores enteros desde el archivo binario y los muestra por consola.

    public static void leerEstadisticas() {
        File archivo = new File(RUTA);

        // Si el archivo aún no existe (primera ejecución), finaliza sin errores
        if (!archivo.exists()) return;

        // Lee los datos en el mismo orden secuencial en que fueron escritos
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            int usuarios = dis.readInt();
            int reportes = dis.readInt();
            int devoluciones = dis.readInt();

            System.out.println("--- ESTADÍSTICAS DEL SISTEMA (BINARIO) ---");
            System.out.println("Usuarios registrados: " + usuarios);
            System.out.println("Reportes totales: " + reportes);
            System.out.println("Devoluciones gestionadas: " + devoluciones);
        } catch (IOException e) {
            System.err.println("Error al leer binario: " + e.getMessage());
        }
    }
}
